package com.example.backend.rest;

import com.example.backend.models.User;
import com.example.backend.repositories.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.models.Event;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("events")
@CrossOrigin(origins = {"http://localhost:8080"})
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @GetMapping()
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> events = eventRepository.getEvents();
        return ResponseEntity.ok(events); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable long id) {
        try {
            Event event = eventRepository.getEvent(id);
            return ResponseEntity.ok(event); // 200 OK
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with id: " + id); // 404 Not Found
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        try {
            Event savedEvent = Event.copyConstructor(event);

            eventRepository.updateEvent(savedEvent);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(savedEvent.getId()).toUri();
            return ResponseEntity.created(location)
                    .header("Response-message", "Event saved successfully.")
                    .body(savedEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@RequestBody Event event) {
        try {
            Event updatedEvent = Event.copyConstructor(event);

            eventRepository.updateEvent(updatedEvent);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(updatedEvent.getId()).toUri();
            return ResponseEntity.created(location)
                    .header("Response-message", "Event updated successfully.")
                    .body(updatedEvent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable long id) {
        try {
            eventRepository.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully"); // 200 OK
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        }
    }
}
