package com.example.backend.exceptions;

/**
 * Exception indicating that a precondition for an operation has failed.
 * This typically occurs when a certain condition that must be true before
 * performing an operation is not met.
 */
public class PreConditionFailedException extends RuntimeException {

    /**
     * Constructs a new PreConditionFailedException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public PreConditionFailedException(String message) {
        super(message);
    }
}
