package com.example.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTest {

    private Network network;

    @BeforeEach
    void setup() {
        this.network = new Network();
        this.network.setTitle("Test Network");
        this.network.setDateTime(LocalDateTime.now());
        this.network.setTheme(Network.Theme.FOOD_WASTE);

        Paragraph paragraph = new Paragraph();
        paragraph.setContent("Test Paragraph");

        Set<Paragraph> paragraphs = new HashSet<>();
        paragraphs.add(paragraph);

        this.network.setParagraphs(paragraphs);
    }

    @Test
    void testNetworkInitialization() {
        assertNotNull(network);
        assertEquals("Test Network", network.getTitle());
        assertNotNull(network.getDateTime());
        assertEquals(Network.Theme.FOOD_WASTE, network.getTheme());
        assertFalse(network.getParagraphs().isEmpty());
    }

    @Test
    void testSetTitle() {
        network.setTitle("Updated Title");
        assertEquals("Updated Title", network.getTitle());
    }

    @Test
    void testSetDateTime() {
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(1);
        network.setDateTime(newDateTime);
        assertEquals(newDateTime, network.getDateTime());
    }

    @Test
    void testSetTheme() {
        network.setTheme(Network.Theme.DISTRIBUTION);
        assertEquals(Network.Theme.DISTRIBUTION, network.getTheme());
    }

    @Test
    void testSetParagraphs() {
        // Assuming you have a method to add paragraphs
        Paragraph newParagraph = new Paragraph();
        newParagraph.setContent("New Paragraph");
        Set<Paragraph> newParagraphs = new HashSet<>();
        newParagraphs.add(newParagraph);

        network.setParagraphs(newParagraphs);

        assertEquals(newParagraphs, network.getParagraphs());
    }

}
