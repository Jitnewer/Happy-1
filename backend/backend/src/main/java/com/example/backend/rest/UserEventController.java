package com.example.backend.rest;

import com.example.backend.models.Event;
import com.example.backend.models.User;
import com.example.backend.models.UserEvent;
import com.example.backend.repositories.UserEvent.UserEventRepository;
import com.example.backend.repositories.event.EventRepository;
import com.example.backend.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/userevents")
@CrossOrigin(origins = {"http://localhost:8080"})
public class UserEventController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserEventRepository userEventRepository;

    @PostMapping("/addUserToEvent/{userId}/{eventId}")
    public ResponseEntity<?> addUserToEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {

        User user = userRepository.getUser(userId);
        Event event = eventRepository.getEvent(eventId);

        if (user != null && event != null) {

            UserEvent userEvent = new UserEvent(user, event);
            userEventRepository.addUserEvent(userEvent);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "User added to event successfully",
                    "status", HttpStatus.OK.value(),
                    "location", location.toString()));
        } else {
            return new ResponseEntity<>(Map.of(
                    "message", "User or event not found",
                    "status", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/removeUserFromEvent/{userId}/{eventId}")
    public ResponseEntity<?> removeUserFromEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {

        User user = userRepository.getUser(userId);
        Event event = eventRepository.getEvent(eventId);

        if (user != null && event != null) {

            UserEvent userEvent = userEventRepository.getUserEventByUserAndEvent(user, event);
            if (userEvent != null) {
                userEventRepository.deleteUserEvent(userEvent.getId());
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .build()
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "User removed from event successfully",
                    "status", HttpStatus.OK.value(),
                    "location", location.toString()));
        } else {
            return new ResponseEntity<>(Map.of(
                    "message", "User or event not found",
                    "status", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/userCountForEvent/{eventId}")
    public ResponseEntity<?> getUserCountForEvent(@PathVariable Long eventId) {
        Event event = eventRepository.getEvent(eventId);

        if (event != null) {
            int userCount = userEventRepository.countUserEventsByEventId(event.getId());

            return new ResponseEntity<>(Map.of(
                    "userCounter", userCount,
                    "status", HttpStatus.OK.value()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of(
                    "userCounter", 0,
                    "status", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to check if a user has a specific event
    @GetMapping("/hasUserEvent/{userId}/{eventId}")
    public ResponseEntity<Object> hasUserEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {

        User user = userRepository.getUser(userId);
        Event event = eventRepository.getEvent(eventId);

        if (user != null && event != null) {

            UserEvent userEvent = userEventRepository.getUserEventByUserAndEvent(user, event);

            if (userEvent != null) {
                return new ResponseEntity<>(Map.of(
                        "message", "User is associated with the event",
                        "status", HttpStatus.OK.value()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of(
                        "message", "User is not associated with the event",
                        "status", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(Map.of(
                    "message", "User or event not found",
                    "status", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
}

