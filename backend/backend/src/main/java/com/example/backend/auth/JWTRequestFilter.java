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

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private APIConfig apiConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            // Handle preflight requests
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8080");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization, " + HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Continue with the filter chain for all requests
        String servletPath = request.getServletPath();
        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (encryptedToken == null) {
            // If not logged in, only allow paths with authentication
            if (!servletPath.contains("authentication")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access forbidden. You need to log in first.");
                return;
            }
        } else {
            try {
                JWToken jwToken = JWToken.decode(encryptedToken.replace("Bearer", "").trim(), this.apiConfig.getJwtPassphrase());
                request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);

                // Access control based on UserType
                User.UserType userType = User.UserType.valueOf(jwToken.getRole());
                if (!hasAccess(userType, servletPath)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access forbidden. Test");
                    return;
                }
            } catch (RuntimeException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage() + " You need to log in first.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    private boolean hasAccess(User.UserType userType, String servletPath) {
        return switch (userType) {
            case ADMIN ->
                // Admin can access all paths
                    true;
            case SUPERUSER ->
                // Superuser can access paths with superuser and authentication
                    !servletPath.contains("admin");
            case PARTNER, ENTREPRENEUR ->
                // Partners and Entrepreneurs can access paths for normal users and authentication
                    !servletPath.contains("admin") && !servletPath.contains("superuser");
            default -> false;
        };
    }

}
