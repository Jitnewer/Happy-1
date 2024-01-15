package com.example.backend.repositories.user;

import com.example.backend.models.User;
import com.example.backend.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class UsersDataloader implements CommandLineRunner {

    @Autowired
    private EntityRepository<User> userEntityRepository;

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("Running Carousel Data Loader");
        this.createInitialUser();
    }

    private void createInitialUser() {
        // Check whether the repo is empty
        List<User> users = this.userEntityRepository.findAll();
        if (!users.isEmpty()) {
            return; // Geen dummy-gegevens toevoegen als er al gegevens zijn
        }

        System.out.println("Configuring some initial carousels in the repository");

        for (int i = 0; i < 2; i++) {
            User user = User.createSampleUser(i);
            userEntityRepository.save(user);
        }
    }
}
