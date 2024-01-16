package com.example.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeTest {

    private Challenge challenge;

    @BeforeEach
    void setup() {
        this.challenge = new Challenge("title5", "paragraph5", null, Challenge.Theme.DISTRIBUTION);

        // Create a set of new paragraphs

    }

    @Test
    void testChallengeInitialization() {
        assertNotNull(challenge);
        assertEquals("title5", challenge.getTitle());
        assertNotNull(challenge.getDateTime());
        assertEquals("img.png", challenge.getImage());
    }

    @Test
    void testSetTitle() {
        challenge.setTitle("testTitle");
        assertEquals("testTitle", challenge.getTitle());
    }

    @Test
    void testSetImage() {
        challenge.setImage("newImage.png");
        assertEquals("newImage.png", challenge.getImage());
    }

    @Test
    void testSetDateTime() {
        // Assuming your DateTime is not null by default
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(1);
        challenge.setDateTime(newDateTime);
        assertEquals(newDateTime, challenge.getDateTime());
    }

    @Test
    void testSetTheme() {
        // Assuming your DateTime is not null by default
        challenge.setTheme(Challenge.Theme.DISTRIBUTION);
        assertEquals(Challenge.Theme.DISTRIBUTION, challenge.getTheme());
    }

    @Test
    void testSetParagraphs() {
        // Assuming your DateTime is not null by default
        Set<Paragraph> newParagraphs = new HashSet<>();
        Paragraph paragraph1 = new Paragraph("title1", "content1", challenge, null);
        Paragraph paragraph2 = new Paragraph("title2", "content2", challenge, null);
        newParagraphs.add(paragraph1);
        newParagraphs.add(paragraph2);
        this.challenge.setParagraphs(newParagraphs);
        assertEquals(newParagraphs, challenge.getParagraphs());
    }


}