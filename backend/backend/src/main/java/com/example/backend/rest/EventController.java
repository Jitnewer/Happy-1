package com.example.backend.rest;

import com.example.backend.models.UserEvent;
import com.example.backend.repositories.event.EventSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.models.Event;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("events")
@CrossOrigin(origins = {"http://localhost:8080"})
public class EventController {

    @Autowired
    private EventSpringDataJpaRepository eventRepository;

    @GetMapping()
    public ResponseEntity<?> getEvents() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Events not found");
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with id: " + id);
        return ResponseEntity.ok(event);
    }

    @PostMapping("")
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body("Event added successfully");
    }

    @PostMapping("/multiple")
    public ResponseEntity<String> createUsers() {
        for (int i = 0; i < 12; i++) {
            Event event = Event.createSampleEvent();
            eventRepository.save(event);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Events added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@RequestBody Event event, @PathVariable Long id) {
        if (!eventRepository.existsById(id)) return ResponseEntity.notFound().build();
        eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.OK).body("Event updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            eventRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Event with id " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with id: " + id);
        }
    }
}
