package com.example.backend.repositories;

import com.example.backend.BackendApplication;
import com.example.backend.models.Challenge;
import com.example.backend.models.Paragraph;
import com.example.backend.models.Research;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
@Import(DataLoader.class)
public class ResearchRepositoryTests {
    @Autowired
    private EntityRepository<Research> researchEntityRepository;
    @BeforeAll
    public static void classLevelSetup() {
        System.out.println("initialise static values and expensive data structures");
    }



    @Autowired
    CommandLineRunner dataLoader;

    private List<Research> someResearches;

    @BeforeEach
    public void setup() throws Exception {
        this.dataLoader.run(null);
        this.someResearches = this.researchEntityRepository.findAll();
    }


    @Test
    public void repoFindAllReturnsAll() {
        assertTrue(this.someResearches.size() > 0);
    }

    @Test
    public void repoFindByIdReturnsProperResearch() {
        // check all books can be found by id
        for (int i = 0; i < this.someResearches.size(); i++) {
            Research research = researchEntityRepository.findById(this.someResearches.get(i).getId());
            assertEquals(this.someResearches.get(i).getTitle(), research.getTitle());
            assertEquals(this.someResearches.get(i).getDateTime(), research.getDateTime());
        }

        Research research = researchEntityRepository.findById(9999999L);
        assertNull(research);
    }

    @Test
    public void repoSaveAddsOrUpdatesAChallenge() {
        // Create a new challenge
        Research research = new Research("title5", "paragraph5", null, Research.Theme.WATER);

        // Create a set of new paragraphs
        Set<Paragraph> newParagraphs = new HashSet<>();
        Paragraph paragraph1 = new Paragraph("title1", "content1", null, research);
        Paragraph paragraph2 = new Paragraph("title2", "content2", null, research);
        newParagraphs.add(paragraph1);
        newParagraphs.add(paragraph2);

        // Set the new paragraphs in the challenge
        research.setParagraphs(newParagraphs);

        // Save the challenge with new paragraphs
        Research savedResearch = researchEntityRepository.save(research);

        // Assertions for the saved challenge
        assertTrue(savedResearch.getId() > 0);
        assertEquals("title5", savedResearch.getTitle());
        assertEquals(2, savedResearch.getParagraphs().size());

        // Modify the saved challenge
        savedResearch.setTitle("title6");

        // Save the modified challenge
        Research updatedResearch = researchEntityRepository.save(savedResearch);

        // Assertions for the updated challenge
        assertEquals(savedResearch.getId(), updatedResearch.getId());
        assertEquals("title6", updatedResearch.getTitle());
        assertEquals(2, updatedResearch.getParagraphs().size());

        // Load the challenge again
        Research reloadedResearch = researchEntityRepository.findById(updatedResearch.getId());

        // Assertions for the reloaded challenge
        assertNotNull(reloadedResearch);
        assertEquals(updatedResearch.getId(), reloadedResearch.getId());


    }
}
