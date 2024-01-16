package com.example.backend.auth;

import com.example.backend.APIConfig;
import com.example.backend.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWTRequestFilter is a filter responsible for handling JWT authentication and authorization for incoming requests.
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private APIConfig apiConfig;

    /**
     * This method is called for each incoming request and handles JWT authentication and authorization.
     *
     * @param request     The HTTP servlet request.
     * @param response    The HTTP servlet response.
     * @param filterChain The filter chain to continue processing the request.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Handle preflight requests
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            handlePreflightRequest(response);
            return;
        }

        // Continue with the filter chain for all other requests
        String servletPath = request.getServletPath();
        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (encryptedToken == null) {
            // If not logged in, only allow paths with authentication
            if (!servletPath.contains("authentication")) {
                sendUnauthorizedResponse(response, "Access forbidden. You need to log in first.");
                return;
            }
        } else {
            try {
                JWToken jwToken = JWToken.decode(encryptedToken.replace("Bearer", "").trim(), this.apiConfig.getJwtPassphrase());
                request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);

                // Access control based on UserType
                User.UserType userType = User.UserType.valueOf(jwToken.getRole());
                if (!hasAccess(userType, servletPath)) {
                    sendForbiddenResponse(response, "Access forbidden.");
                    return;
                }
            } catch (RuntimeException e) {
                sendUnauthorizedResponse(response, e.getMessage() + " You need to log in first.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Handles preflight requests by setting appropriate headers.
     *
     * @param response The HTTP servlet response.
     */
    private void handlePreflightRequest(HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8080");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization, " + HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Sends an unauthorized response with the specified message.
     *
     * @param response The HTTP servlet response.
     * @param message  The unauthorized message.
     * @throws IOException If an I/O error occurs.
     */
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }

    /**
     * Sends a forbidden response with the specified message.
     *
     * @param response The HTTP servlet response.
     * @param message  The forbidden message.
     * @throws IOException If an I/O error occurs.
     */
    private void sendForbiddenResponse(HttpServletResponse response, String message) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, message);
    }

    /**
     * Checks if the user has access based on their UserType and the requested servlet path.
     *
     * @param userType    The user's UserType.
     * @param servletPath The requested servlet path.
     * @return True if the user has access, false otherwise.
     */
    private boolean hasAccess(User.UserType userType, String servletPath) {
        return switch (userType) {
            case ADMIN -> true; // Admin can access all paths
            case SUPERUSER -> !servletPath.contains("admin"); // Superuser can access paths with superuser and authentication
            case PARTNER, ENTREPRENEUR -> !servletPath.contains("admin") && !servletPath.contains("superuser");
            default -> false;
        };
    }
}
