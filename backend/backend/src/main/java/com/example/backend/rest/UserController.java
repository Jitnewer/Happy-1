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
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/users")
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



    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable long id) {
        try {
            User user = userRepository.getUserById(id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found with id: " + id));
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error getting user by ID", "error", e.getMessage()));
        }
    }

    @PostMapping("/admin/users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            if (userRepository.userWithMailExists(user.getMail())) {
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
            if (userRepository.getUserById(id) == null) {
                return ResponseEntity.notFound().build();
            }
            userRepository.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error updating user", "error", e.getMessage()));
        }
    }

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        try {
            User user = userRepository.getUserById(id);
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
