package com.example.backend.repositories.user;

import com.example.backend.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Qualifier("mock")
public class UserRepositoryMock implements UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepositoryMock() {
        for (int i = 0; i < 3; i++) {
            users.add(User.createSampleUser(i));
        }
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User getUserById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new IllegalArgumentException("User not found");
    }


    @Override
    public User addUser(User user) {
        users.add(user);
        return user;
    }


    @Override
    public User login(String email, String password) {
        for (User user : users) {
            if (user.getMail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new IllegalArgumentException("Invalid credentials");
    }

    @Override
    public void register(User user) {

    }

    @Override
    public void updateUser(User updatedUser) {
        Optional<User> existingUser = Optional.empty();
        for (User user : users) {
            if (user.getId() == updatedUser.getId()) {
                existingUser = Optional.of(user);
                break;
            }
        }

        if (existingUser.isPresent()) {
            int index = users.indexOf(existingUser.get());
            users.set(index, updatedUser);
        } else {
            throw new IllegalArgumentException("User not found with id: " + updatedUser.getId());
        }
    }

    @Override
    public void deleteUser(long id) {
        boolean userRemoved = false;
        for (User user : users) {
            if (user.getId() == id) {
                users.remove(user);
                userRemoved = true;
                break;
            }
        }

        if (!userRemoved) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }

    @Override
    public User getUserByMail(String mail) {
        for (User user : users) {
            if (Objects.equals(user.getMail(), mail)) {
                return user;
            }
        }
        throw new IllegalArgumentException("User not found with email: " + mail);
    }

    @Override
    public boolean userWithMailExists(String email) {
        return false;
    }

}

