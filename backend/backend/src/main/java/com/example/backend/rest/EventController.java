package com.example.backend.rest;

import com.example.backend.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.models.Event;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.sound.sampled.FloatControl;
import java.net.URI;
import java.util.List;


@RestController @RequestMapping("events")
@CrossOrigin(origins = {"http://localhost:8080"})
public class EventController  {
    @Autowired
    EventRepository eventRepository;

    @GetMapping()
    public List<Event> getEvents() {
        return eventRepository.getEvents();
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable long id) {
        return eventRepository.getEvent(id);
    }

    @PostMapping("")
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        Event savedEvent = Event.copyConstructor(event);
        eventRepository.addEvent(savedEvent);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedEvent.getId()).toUri();
        return ResponseEntity.created(location).body(savedEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        Event updatedEvent = Event.copyConstructor(event);
        eventRepository.updateEvent(updatedEvent);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(updatedEvent.getId()).toUri();
        return ResponseEntity.created(location).body(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventRepository.deleteEvent(id);
    }
}
