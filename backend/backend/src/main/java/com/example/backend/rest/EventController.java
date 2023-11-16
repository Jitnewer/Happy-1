package com.example.backend.rest;

import com.example.backend.models.Event;
import com.example.backend.repositories.event.EventSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("events")
@CrossOrigin(origins = {"http://localhost:8080"})
public class EventController {

    @Autowired
    private EventSpringDataJpaRepository eventRepository;

    @GetMapping()
    public ResponseEntity<Object> getEvents() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Events not found"));
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvent(@PathVariable long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Event not found with id: " + id)));
    }

    @PostMapping("")
    public ResponseEntity<Object> addEvent(@RequestBody Event event) {
        try {
            Event savedEvent = eventRepository.save(event);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedEvent.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Event added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString()));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding the event");
            errorResponse.put("error", e.getMessage()); // Include more details about the error if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PostMapping("/multiple")
    public ResponseEntity<Object> createEvents() {
        try {
            for (int i = 0; i < 12; i++) {
                Event event = Event.createSampleEvent();
                eventRepository.save(event);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Events added successfully"));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding events");
            errorResponse.put("error", e.getMessage()); // Include more details about the error if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@RequestBody Event event, @PathVariable Long id) {
        if (!eventRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Event not found with id: " + id));
        }
        eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Event updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            eventRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Event with id " + id + " deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Event not found with id: " + id));
        }
    }
}
