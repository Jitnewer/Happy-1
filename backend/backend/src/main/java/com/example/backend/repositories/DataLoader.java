package com.example.backend.repositories;


import com.example.backend.models.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Random;

import static org.springframework.util.ClassUtils.getUserClass;

@Component
public class DataLoader implements CommandLineRunner {

    @Override
    @Transactional
    public void run(String... args)  {
        System.out.println("Running CommandLine Startup");
        this.createInitialChallenges();

        System.out.println("Injected challenges from " +
                (this.challengeEntityRepository != null ? getUserClass(this.challengeEntityRepository.getClass()).getName() : "none"));

    }

    @Autowired
    private EntityRepository<Challenge> challengeEntityRepository;

    private void createInitialChallenges() {
        // check whether the repo is empty
        List<Challenge> challenges = this.challengeEntityRepository.findAll();
        if (challenges.size() > 0) return;
        System.out.println("Configuring some initial accounts in the repository");
        challenges.add(this.challengeEntityRepository.save(new Challenge("title1", "paragraph1", null, Challenge.Theme.WATER)));
        challenges.add(this.challengeEntityRepository.save(new Challenge("title2", "paragraph2", null, Challenge.Theme.DISTRIBUTION)));
        challenges.add(this.challengeEntityRepository.save(new Challenge("title3", "paragraph3", null, Challenge.Theme.FOOD_WASTE)));
        challenges.add(this.challengeEntityRepository.save(new Challenge("title4", "paragraph4", null, Challenge.Theme.ENERGY_TRANSITION)));
        System.out.println("challenges added to the repository");
    }

}
