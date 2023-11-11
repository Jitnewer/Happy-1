package com.example.backend.rest;

import com.example.backend.models.Event;
import com.example.backend.models.User;
import com.example.backend.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
public class UserController {
    @Autowired
    UserRepository userRepository;


    @PostMapping("/register")
    public void register(@RequestBody User user) {
        userRepository.register(user);
    }
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.getUsers();
    }
    @GetMapping("/users/fullname/{mail}")
    public User getUserByMail(@PathVariable String mail) {
        return userRepository.getUserByMail(mail);
    }
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable long id) {
        return userRepository.getUser(id);
    }
    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        userRepository.addUser(user);
    }
    @PostMapping("/login/{email}/{password}")
    public User login(@PathVariable String email, @PathVariable String password) {
        return userRepository.login(email, password);
    }
    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        userRepository.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteUser(id);
    }
}
