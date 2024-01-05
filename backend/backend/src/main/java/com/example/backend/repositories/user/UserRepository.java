package com.example.backend.repositories.user;


import com.example.backend.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();
    User getUserById(long id);

    User addUser(User user);
    User login(String email, String password);
    void updateUser(User user);
    void deleteUser(long id);

    User getUserByMail(String email);
    boolean userWithMailExists(String email);
}
