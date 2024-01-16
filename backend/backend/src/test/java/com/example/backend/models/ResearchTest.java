package com.example.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResearchTest {

    private Research research;

    @BeforeEach
    void setup() {
        this.research = new Research("title5", "paragraph5", null, Research.Theme.DISTRIBUTION);

        // Create a set of new paragraphs

    }

    @Test
    void testResearchInitialization() {
        assertNotNull(research);
        assertEquals("title5", research.getTitle());
        assertNotNull(research.getDateTime());
        assertEquals("img.png", research.getImage());
    }

    @Test
    void testSetTitle() {
        research.setTitle("testTitle");
        assertEquals("testTitle", research.getTitle());
    }

    @Test
    void testSetImage() {
        research.setImage("newImage.png");
        assertEquals("newImage.png", research.getImage());
    }

    @Test
    void testSetDateTime() {
        // Assuming your DateTime is not null by default
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(1);
        research.setDateTime(newDateTime);
        assertEquals(newDateTime, research.getDateTime());
    }

    @Test
    void testSetTheme() {
        // Assuming your DateTime is not null by default
        research.setTheme(Research.Theme.DISTRIBUTION);
        assertEquals(Research.Theme.DISTRIBUTION, research.getTheme());
    }

    @Test
    void testSetParagraphs() {
        // Assuming your DateTime is not null by default
        Set<Paragraph> newParagraphs = new HashSet<>();
        Paragraph paragraph1 = new Paragraph("title1", "content1", null, research);
        Paragraph paragraph2 = new Paragraph("title2", "content2", null, research);
        newParagraphs.add(paragraph1);
        newParagraphs.add(paragraph2);
        this.research.setParagraphs(newParagraphs);
        assertEquals(newParagraphs, research.getParagraphs());
    }


}