package com.example.backend.repositories;

import com.example.backend.BackendApplication;
import com.example.backend.models.Challenge;
import com.example.backend.models.Paragraph;
import com.example.backend.repositories.DataLoader;
import com.example.backend.repositories.EntityRepository;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
@Import(DataLoader.class)
public class ChallengeJpaRepositoryTests {
    @Autowired
    private EntityRepository<Challenge> challengeEntityRepository;
    @BeforeAll
    public static void classLevelSetup() {
        System.out.println("initialise static values and expensive data structures");
    }



    @Autowired
    CommandLineRunner dataLoader;

    private List<Challenge> someChallenges;

    @BeforeEach
    public void setup() throws Exception {
        this.dataLoader.run(null);
        this.someChallenges = this.challengeEntityRepository.findAll();
    }


    @Test
    public void repoFindAllReturnsAll() {
        assertTrue(this.someChallenges.size() > 0);
    }

    @Test
    public void repoFindByIdReturnsProperChallenge() {
        // check all books can be found by id
        for (int i = 0; i < this.someChallenges.size(); i++) {
            Challenge challenge = challengeEntityRepository.findById(this.someChallenges.get(i).getId());
            assertEquals(this.someChallenges.get(i).getTitle(), challenge.getTitle());
            assertEquals(this.someChallenges.get(i).getDateTime(), challenge.getDateTime());
        }

        Challenge challenge = challengeEntityRepository.findById(9999999L);
        assertNull(challenge);
    }

    @Test
    public void repoSaveAddsOrUpdatesAChallenge() {
        // Create a new challenge
        Challenge challenge = new Challenge("title5", "paragraph5", null, Challenge.Theme.DISTRIBUTION);

        // Create a set of new paragraphs
        Set<Paragraph> newParagraphs = new HashSet<>();
        Paragraph paragraph1 = new Paragraph("title1", "content1", challenge, null);
        Paragraph paragraph2 = new Paragraph("title2", "content2", challenge, null);
        newParagraphs.add(paragraph1);
        newParagraphs.add(paragraph2);

        // Set the new paragraphs in the challenge
        challenge.setParagraphs(newParagraphs);

        // Save the challenge with new paragraphs
        Challenge savedChallenge = challengeEntityRepository.save(challenge);

        // Assertions for the saved challenge
        assertTrue(savedChallenge.getId() > 0);
        assertEquals("title5", savedChallenge.getTitle());
        assertEquals(2, savedChallenge.getParagraphs().size());

        // Modify the saved challenge
        savedChallenge.setTitle("title6");

        // Save the modified challenge
        Challenge updatedChallenge = challengeEntityRepository.save(savedChallenge);

        // Assertions for the updated challenge
        assertEquals(savedChallenge.getId(), updatedChallenge.getId());
        assertEquals("title6", updatedChallenge.getTitle());
        assertEquals(2, updatedChallenge.getParagraphs().size());

        // Load the challenge again
        Challenge reloadedChallenge = challengeEntityRepository.findById(updatedChallenge.getId());

        // Assertions for the reloaded challenge
        assertNotNull(reloadedChallenge);
        assertEquals(updatedChallenge.getId(), reloadedChallenge.getId());


    }
}
