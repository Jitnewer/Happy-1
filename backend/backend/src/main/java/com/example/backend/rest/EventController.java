package com.example.backend.rest;

import com.example.backend.models.Event;
import com.example.backend.repositories.event.EventSpringDataJpaRepository;
import com.example.backend.models.User;
import com.example.backend.repositories.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.models.Event;
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
    EventRepository eventRepository;

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
