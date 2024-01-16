package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Exception indicating that a requested resource was not found.
 * This exception is annotated with @ResponseStatus to map it to the HTTP 404 Not Found status.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message and path.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param path    The path of the requested resource.
     */
    public ResourceNotFoundException(String message, String path) {
        super(message);
        this.timestamp = LocalDateTime.now();
        this.status = 404;
        this.error = "Not Found";
        this.message = message;
        this.path = path;
    }

    /**
     * Converts the exception details to a Map for easy serialization.
     *
     * @return A Map containing the exception details.
     */
    public Map<String, String> toMap() {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("timestamp", timestamp.toString());
        errorMap.put("status", String.valueOf(status));
        errorMap.put("error", error);
        errorMap.put("message", message);
        errorMap.put("path", path);
        return errorMap;
    }

    /**
     * Gets the timestamp when the exception occurred.
     *
     * @return The timestamp.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the HTTP status code.
     *
     * @return The HTTP status code.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets the error type.
     *
     * @return The error type.
     */
    public String getError() {
        return error;
    }

    /**
     * Gets the detail message of the exception.
     *
     * @return The detail message.
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Gets the path of the requested resource.
     *
     * @return The path.
     */
    public String getPath() {
        return path;
    }
}
