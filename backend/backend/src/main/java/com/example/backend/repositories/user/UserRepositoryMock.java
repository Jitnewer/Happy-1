package com.example.backend.repositories.user;

import com.example.backend.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserRepositoryMock implements UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepositoryMock() {
        users.add(new User("Rick", "Veerman", "rickveerman4@gmail.com", "0655076733", "RickVeerman2002" , true));
        users.add(new User("Nicky", "Bosveld", "nickybosveld@gmail.com", "061234567", "NickyBosveld",  false));
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
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
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
    public boolean isAdmin(String email) {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                return user.isAdmin();
            }
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                return user;
            }
        }
        return null;
    }


}
