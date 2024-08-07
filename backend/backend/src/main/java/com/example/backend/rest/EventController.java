package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.Event;
import com.example.backend.repositories.EntityRepository;
import com.example.backend.repositories.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.models.Event;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EntityRepository<Event> eventRepository; // Change this line

    @GetMapping()
    public ResponseEntity<Object> getEvents() {
        try {
            List<Event> events = eventRepository.findAll();
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
            Event event = eventRepository.findById(id);
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

    @PostMapping("/superuser")
    public ResponseEntity<Object> addEvent(@RequestBody Event event) {
        try {
            Event savedEvent = eventRepository.save(event);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(event.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Event added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString(),
                    "entity", savedEvent));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error adding the event",
                    "error", e.getMessage()));
        }
    }


    @PutMapping("/superuser/{id}")
    public ResponseEntity<Object> updateEvent(@RequestBody Event event, @PathVariable Long id) {
        try {
            // Ensure the ID in the path matches the ID in the request body
            if (!id.equals(event.getId())) {
                throw new PreConditionFailedException("Event ID in the path does not match the ID in the request body.");
            }

            eventRepository.save(event);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Event updated successfully", "event", event));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error updating the event",
                    "error", e.getMessage()));
        }
    }

    @DeleteMapping("/superuser/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable long id) {
        try {
            eventRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Event with id " + id + " deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error deleting the event",
                    "error", e.getMessage()));
        }
    }
}
