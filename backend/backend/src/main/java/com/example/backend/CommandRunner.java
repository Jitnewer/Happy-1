package com.example.backend;

import com.example.backend.models.Challenge;
import com.example.backend.models.Event;
import com.example.backend.models.Paragraph;
import com.example.backend.models.User;
import com.example.backend.repositories.challenge.ChallengeRepository;
import com.example.backend.repositories.event.EventRepository;
import com.example.backend.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CommandRunner implements CommandLineRunner {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ChallengeRepository challengeRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running CommandLine Startup");
        createInitialEvent();
        createInitialUser();
        createInitialChallenge();
    }

    private void createInitialEvent() {
        List<Event> events = eventRepository.getEvents();
        if (!events.isEmpty()) return;
        System.out.println("Configuring some initial event data");
        for (int i = 0; i < 11; i++) {
            Event event = Event.createSampleEvent();
            eventRepository.addEvent(event);
        }
    }

    private void createInitialUser() {
        List<User> Users = userRepository.getUsers();
        if (!Users.isEmpty()) return;
        System.out.println("Configuring some initial user data");
        for (int i = 0; i < 2; i++) {
            User user = com.example.backend.models.User.createSampleUser();
            userRepository.addUser(user);
        }
    }
    private void createInitialChallenge() {
        List<Challenge> challenges = challengeRepository.getChallenges();
        if (!challenges.isEmpty()) return;

        System.out.println("Configuring some initial challenge data");

        Challenge initialChallenge = new Challenge();
        initialChallenge.setTitle("Java Spring Boot Challenge");
        initialChallenge.setFirstParagraph("This is the first paragraph of the challenge.");
        initialChallenge.setImage("img.png");

        Set<Paragraph> initialParagraphs = new HashSet<>();
        initialParagraphs.add(new Paragraph("Introduction", "This is the introduction paragraph.", initialChallenge));
        initialParagraphs.add(new Paragraph("Requirements", "Participants should have knowledge of Java and Spring Boot.", initialChallenge));
        initialParagraphs.add(new Paragraph("Submission", "Submit your solution by the deadline mentioned in the challenge.", initialChallenge));

        initialChallenge.setParagraphs(initialParagraphs);

        challengeRepository.addChallenge(initialChallenge);
    }

}
