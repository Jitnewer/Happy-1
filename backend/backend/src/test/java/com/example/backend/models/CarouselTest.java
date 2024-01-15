package com.example.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CarouselTest {

    private Carousel carousel;

    @BeforeEach
    void setup() {
        this.carousel = new Carousel("Test Carousel");
    }

    @Test
    void testCarouselInitialization() {
        assertNotNull(carousel);
        assertEquals("Test Carousel", carousel.getTitle());
        assertNotNull(carousel.getDateTime());
        assertEquals("assets/carouselPic/carousel_63.jpg", carousel.getImage());
    }

    @Test
    void testSetTitle() {
        carousel.setText("Updated Title");
        assertEquals("Updated Title", carousel.getTitle());
    }

    @Test
    void testSetImage() {
        carousel.setImage("newImage.png");
        assertEquals("newImage.png", carousel.getImage());
    }

    @Test
    void testSetDateTime() {
        // Assuming your DateTime is not null by default
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(1);
        carousel.setDateTime(newDateTime);
        assertEquals(newDateTime, carousel.getDateTime());
    }

    @Test
    void testToString() {
        String expectedString = "Carousel{id=0, title='Test Carousel', dateTime=" + carousel.getDateTime() +  ", image='assets/carouselPic/carousel_63.jpg}";
        assertEquals(expectedString, carousel.toString());
    }
}
