package com.example.backend.rest;

import com.example.backend.models.Event;
import com.example.backend.models.User;
import com.example.backend.repositories.user.UserRepository;
import com.example.backend.repositories.user.UserSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
public class UserController {

    @Autowired
    private UserSpringDataJpaRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.existsByMail(user.getMail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with mail: " + user.getMail() + " already exists");
        }

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        Optional<User> foundUser = userRepository.findByMailAndPassword(email, password);

        if (foundUser.isPresent()) {
            // User is found, you can return user details or a token for authentication
            return ResponseEntity.ok(foundUser.get());
        } else {
            // User not found or invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found");
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/mail/{mail}")
    public ResponseEntity<?> getUserByMail(@PathVariable String mail) {
        Optional<User> user = userRepository.findByMail(mail);
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with email: " + mail);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id: " + id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        if (userRepository.findByMail(user.getMail()).isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with mail:" + user.getMail() + "is already in use");
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }

    @PostMapping("/create-users")
    public ResponseEntity<String> createUsers() {
        for (int i = 0; i < 6; i++) {
            User user = User.createSampleUser();
            if (userRepository.findByMail(user.getMail()).isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with mail:" + user.getMail() + "is already in use");
            userRepository.save(user);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Users added successfully");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable Long id) {
        if (!userRepository.existsById(id)) return ResponseEntity.notFound().build();
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User with id " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id: " + id);
        }
    }


}
