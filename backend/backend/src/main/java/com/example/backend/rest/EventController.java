package com.example.backend.rest;

import com.example.backend.repositories.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.backend.models.Event;

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

    @PostMapping()
    public void addEvent(@RequestBody Event event) {
        eventRepository.addEvent(event);
    }

    @PutMapping()
    public void updateEvent(@RequestBody Event event) {
        eventRepository.updateEvent( event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        eventRepository.deleteEvent(id);
    }
}
