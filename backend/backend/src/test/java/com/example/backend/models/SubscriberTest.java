package com.example.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubscriberTest {

    private Subscriber subscriber;

    @BeforeEach
    void setUp() {
        this.subscriber = new Subscriber("test@example.com");
    }

    @Test
    void testSubscriberInitialization() {
        assertNotNull(subscriber);
        assertEquals("test@example.com", subscriber.getEmail());
    }

    @Test
    void testSetEmail() {
        subscriber.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", subscriber.getEmail());
    }

    @Test
    void testGetId() {
        assertEquals(0, subscriber.getId()); // Assuming the default id is 0
    }

    @Test
    void testSetId() {
        subscriber.setId(1);
        assertEquals(1, subscriber.getId());
    }
}
