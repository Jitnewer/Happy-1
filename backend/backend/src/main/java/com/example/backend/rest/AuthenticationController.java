package com.example.backend.rest;

import com.example.backend.APIConfig;
import com.example.backend.auth.JWToken;
import com.example.backend.models.User;
import com.example.backend.repositories.EntityRepository;
import com.example.backend.repositories.user.UserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.Map;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    private JWToken jwToken;
    private APIConfig apiConfig;
    private EntityRepository<User> userRepository;

    @Autowired
    public AuthenticationController(JWToken jwToken, APIConfig apiConfig, EntityRepository<User> userRepository) {
        this.jwToken = jwToken;
        this.apiConfig = apiConfig;
        this.userRepository = userRepository;
    }

    // Handles the login endpoint
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody ObjectNode requestedBody) {
        String mail = requestedBody.get("mail").asText();
        String password = requestedBody.get("password").asText();

        // Attempt to login using the provided credentials
        User user = userRepository.login(mail, password);

        if (user != null) {
            // Update the JWT token properties with user information
            jwToken.setCallName(user.getMail());
            jwToken.setUserId(user.getId());
            jwToken.setRole(user.getUserType().name());

            // Generate a JWT token and include it in the response header
            String tokenString = jwToken.encode(apiConfig.getJwtIssuer(), apiConfig.getJwtPassphrase(), apiConfig.getJwtDurationOfValidity());

            // Respond with the user information and the JWT token in the header
            return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString).body(user);
        } else {
            // If login fails, throw an exception with a 406 status code
            throw new NotAcceptableStatusException("Login failed. Cannot authenticate user by email=" + mail);
        }
    }

    // Handles the endpoint to get user information by mail
    @GetMapping("/{mail}")
    public ResponseEntity<Object> getUserByMail(@PathVariable String mail) {
        try {
            // Retrieve user information by mail
            User user = userRepository.findByProperty("mail", mail);
            if (user == null) {
                // If user not found, respond with a 404 status code and a message
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found with email: " + mail));
            }
            // Respond with the user information
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // If an error occurs, respond with a 500 status code and an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error getting user by mail", "error", e.getMessage()));
        }
    }

    // Handles the user registration endpoint
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        try {
            // Check if a user with the same email already exists
            if (userRepository.findByProperty("mail", user.getMail()) != null) {
                // If user already exists, respond with a 409 status code and a message
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "User with mail: " + user.getMail() + " already exists"));
            }
            // Register the new user
            userRepository.register(user);
            // Respond with a 201 status code and a success message
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            // If an error occurs during registration, respond with a 500 status code and an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error registering user", "error", e.getMessage()));
        }
    }
}
