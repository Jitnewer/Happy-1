package com.example.backend.repositories.user;

import com.example.backend.models.Event;
import com.example.backend.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserRepositoryMock implements UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepositoryMock() {
        for (int i = 0; i < 6; i++) {
            users.add(User.createSampleUser());
        }
    }
    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User getUser(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void register(User user) {
        users.add(user);

    }

    @Override
    public User login(String email, String password) {
        for (User user : users) {
            if (user.getMail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Return null if no matching user is found
    }


    @Override
    public void updateUser(User updatedUser) {
        for (User user : users) {
            if (user.getId() == updatedUser.getId()) {
                int index = users.indexOf(user);
                users.set(index, updatedUser);
            }
        }
    }

    @Override
    public void deleteUser(long id) {
        users.removeIf(event -> event.getId() == id);
    }

    @Override
    public User getUserByMail(String mail) {
        for (User user : users) {
            if (Objects.equals(user.getMail(), mail)) {
                return user;
            }
        }
        return null;
    }


}
