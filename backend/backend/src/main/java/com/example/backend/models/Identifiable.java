package com.example.backend.models;

/**
 * The Identifiable interface represents an entity with a unique identifier.
 */
public interface Identifiable {

    /**
     * Gets the unique identifier of the entity.
     *
     * @return The unique identifier.
     */
    long getId();

    /**
     * Sets the unique identifier of the entity.
     *
     * @param id The unique identifier to set.
     */
    void setId(long id);
}
