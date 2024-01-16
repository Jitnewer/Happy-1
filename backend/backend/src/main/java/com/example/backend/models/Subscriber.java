package com.example.backend.models;

import jakarta.persistence.*;

/**
 * Represents a subscriber entity with an email address.
 */
@Entity
@Table(name = "subscribers")
public class Subscriber implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Constructor for creating a subscriber with a specified email address.
     *
     * @param email The email address of the subscriber.
     */
    public Subscriber(String email) {
        this.email = email;
    }

    /**
     * Default constructor for Subscriber.
     */
    public Subscriber() {

    }

    /**
     * Gets the email address of the subscriber.
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the subscriber.
     *
     * @param email The email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the unique identifier of the subscriber.
     *
     * @return The subscriber's identifier.
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the subscriber.
     *
     * @param id The subscriber's identifier.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }
}
