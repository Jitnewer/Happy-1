package com.example.backend;

import com.example.backend.models.User;
import com.example.backend.repositories.EntityRepository;
import com.example.backend.repositories.user.UsersDataloader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(UsersDataloader.class)
@SpringBootTest(classes = BackendApplication.class)
public class UserRepositoryJpaTest {

    @Autowired
    private EntityRepository<User> usersRepo;

    private List<User> users;

    @Autowired
    UsersDataloader usersDataloader;

    @BeforeEach
    public void setup() {
        // Voer de dataloader uit om de repository te initialiseren
        usersDataloader.run();
        this.users = this.usersRepo.findAll();
    }

    @Test
    void findAllUsersReturnsAll() {
        System.out.println(users);
        assertTrue(this.users.size() > 0);
    }

    @Test
    void saveUser() {
        User user = User.createSampleUser(3);
        usersRepo.save(user);

        User savedUser = usersRepo.findById((long) 3);
        assertEquals(user.getFirstname(), savedUser.getFirstname());
        assertEquals(user.getMail(), savedUser.getMail());
    }

    @Test
    void deleteUser() {
        int initialSize = this.users.size();

        usersRepo.deleteById((long) 1);

        this.users = usersRepo.findAll();
        assertEquals(initialSize - 1, users.size());
    }
}
