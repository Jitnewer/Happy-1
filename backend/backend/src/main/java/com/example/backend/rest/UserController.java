package com.example.backend.rest;

import com.example.backend.models.User;
import com.example.backend.repositories.user.UserRepository;
import com.example.backend.repositories.user.UserSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        try {
            if (userRepository.existsByMail(user.getMail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "User with mail: " + user.getMail() + " already exists"));
            }

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error registering user", "error", e.getMessage()));
        }
    }

    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<Object> login(@PathVariable String email, @PathVariable String password) {
        try {
            Optional<User> foundUser = userRepository.findByMailAndPassword(email, password);

            if (foundUser.isPresent()) {
                return ResponseEntity.ok(foundUser.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error during login", "error", e.getMessage()));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() {
        try {
            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Users not found"));
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error getting users", "error", e.getMessage()));
        }
    }

    @GetMapping("/users/mail/{mail}")
    public ResponseEntity<Object> getUserByMail(@PathVariable String mail) {
        try {
            Optional<User> user = userRepository.findByMail(mail);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found with email: " + mail));
            }
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error getting user by mail", "error", e.getMessage()));
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found with id: " + id));
            }
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error getting user by ID", "error", e.getMessage()));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            if (userRepository.findByMail(user.getMail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "User with mail:" + user.getMail() + " is already in use"));
            }

            User savedUser = userRepository.save(user);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedUser.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "User added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error adding user", "error", e.getMessage()));
        }
    }

    @PostMapping("/create-users")
    public ResponseEntity<Object> createUsers() {
        try {
            for (int i = 0; i < 6; i++) {
                User user = User.createSampleUser();
                if (userRepository.findByMail(user.getMail()).isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "User with mail:" + user.getMail() + " is already in use"));
                }
                userRepository.save(user);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Users added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error creating users", "error", e.getMessage()));
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error updating user", "error", e.getMessage()));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                userRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User with id " + id + " deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error deleting user", "error", e.getMessage()));
        }
    }
}
