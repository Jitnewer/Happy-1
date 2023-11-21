package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ResourceNotFoundException(String message, String path) {
        super(message);
        this.timestamp = LocalDateTime.now();
        this.status = 404;
        this.error = "Not Found";
        this.message = message;
        this.path = path;
    }
    public Map<String, String> toMap() {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("timestamp", timestamp.toString());
        errorMap.put("status", String.valueOf(status));
        errorMap.put("error", error);
        errorMap.put("message", message);
        errorMap.put("path", path);
        return errorMap;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}