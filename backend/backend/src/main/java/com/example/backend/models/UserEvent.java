package com.example.backend.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

/**
 * Represents the association between a User and an Event in the system.
 */
@Entity
@Table(name = "userEvents")
public class UserEvent implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    /**
     * Default constructor for UserEvent.
     */
    public UserEvent() {

    }

    /**
     * Constructor for creating a UserEvent with specified User and Event.
     *
     * @param user  The user associated with the event.
     * @param event The event associated with the user.
     */
    public UserEvent(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    /**
     * Gets the user associated with the UserEvent.
     *
     * @return The user associated with the event.
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets the event associated with the UserEvent.
     *
     * @return The event associated with the user.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Gets the unique identifier of the UserEvent.
     *
     * @return The UserEvent's identifier.
     */
    @Override
    public long getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier of the UserEvent.
     *
     * @param id The UserEvent's identifier.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }
}
