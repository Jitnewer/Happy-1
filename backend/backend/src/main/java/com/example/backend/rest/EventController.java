package com.example.backend.rest;

import com.example.backend.models.Event;
import com.example.backend.repositories.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity<String> addEvent(@RequestBody Event event) {
        eventRepository.addEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body("Event added successfully"); // 201 Created
    }

    @PutMapping()
    public ResponseEntity<String> updateEvent(@RequestBody Event event) {
        eventRepository.updateEvent(event);
        return ResponseEntity.ok("Event updated successfully"); // 200 OK
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
