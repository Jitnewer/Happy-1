package com.example.backend.rest;

import com.example.backend.APIConfig;
import com.example.backend.auth.JWToken;
import com.example.backend.models.User;
import com.example.backend.repositories.user.UserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.NotAcceptableStatusException;


import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("authentication")

public class AuthenticationController {

    private JWToken jwToken;
    private APIConfig apiConfig;
    private UserRepository userRepository;

    @Autowired
    public AuthenticationController(JWToken jwToken, APIConfig apiConfig, UserRepository userRepository) {
        this.jwToken = jwToken;
        this.apiConfig = apiConfig;
        this.userRepository = userRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody ObjectNode requestedBody) {
        String mail = requestedBody.get("mail").asText();
        String password = requestedBody.get("password").asText();

        User user = userRepository.login(mail, password);

        if (user != null) {
            // Use the existing jwToken instance and update its properties
            jwToken.setCallName(user.getMail());
            jwToken.setUserId(user.getId());
            jwToken.setRole(user.getUserType().name());


            String tokenString = jwToken.encode(apiConfig.getJwtIssuer(), apiConfig.getJwtPassphrase(), apiConfig.getJwtDurationOfValidity());

            return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString).body(user);

        } else {
            throw new NotAcceptableStatusException("login failed, cannot authenticate user by email=" + mail);
        }
    }
    @GetMapping("/{mail}")
    public ResponseEntity<Object> getUserByMail(@PathVariable String mail) {
        try {
            User user = userRepository.getUserByMail(mail);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found with email: " + mail));
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error getting user by mail", "error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        try {
            if (userRepository.getUserByMail(user.getMail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "User with mail: " + user.getMail() + " already exists"));
            }
            userRepository.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error registering user", "error", e.getMessage()));
        }
    }
}