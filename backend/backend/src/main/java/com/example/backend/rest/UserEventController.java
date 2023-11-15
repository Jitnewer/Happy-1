package com.example.backend.rest;

import com.example.backend.models.Event;
import com.example.backend.models.User;
import com.example.backend.models.UserEvent;
import com.example.backend.repositories.UserEvent.UserEventSpringDataJpaRepository;
import com.example.backend.repositories.event.EventSpringDataJpaRepository;
import com.example.backend.repositories.user.UserSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/userevents")
@CrossOrigin(origins = {"http://localhost:8080"})
public class UserEventController {

    @Autowired
    private UserSpringDataJpaRepository userRepository;

    @Autowired
    private EventSpringDataJpaRepository eventRepository;

    @Autowired
    private UserEventSpringDataJpaRepository userEventRepository;

    // Other methods...

    // Endpoint to add a user to an event
    @PostMapping("/addUserToEvent/{userId}/{eventId}")
    public ResponseEntity<String> addUserToEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalUser.isPresent() && optionalEvent.isPresent()) {
            User user = optionalUser.get();
            Event event = optionalEvent.get();

            UserEvent userEvent = new UserEvent(user, event);
            userEventRepository.save(userEvent);

            return new ResponseEntity<>("User added to event successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User or event not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/userCountForEvent/{eventId}")
    public ResponseEntity<Integer> getUserCountForEvent(@PathVariable Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            int userCount = userEventRepository.countByEvent(event);

            return new ResponseEntity<>(userCount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to check if a user has a specific event
    @GetMapping("/hasUserEvent/{userId}/{eventId}")
    public ResponseEntity<Object> hasUserEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalUser.isPresent() && optionalEvent.isPresent()) {
            User user = optionalUser.get();
            Event event = optionalEvent.get();

            boolean userHasEvent = userEventRepository.existsByUserAndEvent(user, event);

            if (userHasEvent) {
                return new ResponseEntity<>(Map.of("message", "User is associated with the event", "status", HttpStatus.OK.value()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("message", "User is not associated with the event", "status", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(Map.of("message", "User or event not found", "status", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
}
