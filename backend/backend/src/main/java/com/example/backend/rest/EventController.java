package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.Event;
import com.example.backend.repositories.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("events")
@CrossOrigin(origins = {"http://localhost:8080"})
public class EventController {

    @Autowired
    private EventRepository eventRepository; // Change this line

    @GetMapping()
    public ResponseEntity<Object> getEvents() {
        try {
            List<Event> events = eventRepository.getEvents();
            if (events.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Events not found"));
            }
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving events",
                    "error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvent(@PathVariable long id) {
        try {
            Event event = eventRepository.getEvent(id);
            if (event != null) {
                return ResponseEntity.ok(event);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Event not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving event",
                    "error", e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> addEvent(@RequestBody Event event) {
        try {
            eventRepository.addEvent(event);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(event.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Event added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error adding the event",
                    "error", e.getMessage()));
        }
    }

    @PostMapping("/multiple")
    public ResponseEntity<Object> createEvents() {
        try {
            for (int i = 0; i < 12; i++) {
                Event event = Event.createSampleEvent();
                eventRepository.addEvent(event);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Events added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error adding events",
                    "error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@RequestBody Event event, @PathVariable Long id) {
        try {
            // Ensure the ID in the path matches the ID in the request body
            if (!id.equals(event.getId())) {
                throw new PreConditionFailedException("Event ID in the path does not match the ID in the request body.");
            }

            eventRepository.updateEvent(event);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Event updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error updating the event",
                    "error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable long id) {
        try {
            eventRepository.deleteEvent(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Event with id " + id + " deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error deleting the event",
                    "error", e.getMessage()));
        }
    }
}
