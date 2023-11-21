package com.example.backend.rest;

import com.example.backend.models.User;
import com.example.backend.repositories.user.UserRepository;
import com.example.backend.repositories.user.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
            if (userRepository.getUserByMail(user.getMail()) == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "User with mail: " + user.getMail() + " already exists"));
            }

            userRepository.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error registering user", "error", e.getMessage()));
        }
    }

    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<Object> login(@PathVariable String email, @PathVariable String password) {
        try {
            User user = userRepository.login(email, password);

            if (user != null) {
                return ResponseEntity.ok(user);
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
            List<User> users = userRepository.getUsers();
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
            User user = userRepository.getUserByMail(mail);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found with email: " + mail));
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error getting user by mail", "error", e.getMessage()));
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable long id) {
        try {
            User user = userRepository.getUser(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found with id: " + id));
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error getting user by ID", "error", e.getMessage()));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            if (userRepository.getUserByMail(user.getMail()) == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "User with mail:" + user.getMail() + " is already in use"));
            }

            userRepository.addUser(user);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "User added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error adding user", "error", e.getMessage()));
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable Long id) {
        try {
            if (userRepository.getUser(id) == null) {
                return ResponseEntity.notFound().build();
            }
            userRepository.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error updating user", "error", e.getMessage()));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        try {
            User user = userRepository.getUser(id);
            if (user != null) {
                userRepository.deleteUser(id);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User with id " + id + " deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error deleting user", "error", e.getMessage()));
        }
    }
}
