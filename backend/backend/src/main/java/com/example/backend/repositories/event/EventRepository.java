package com.example.backend.repositories.event;

import com.example.backend.models.Event;

import java.util.List;

public interface EventRepository {
    List<Event> getEvents();
    Event getEvent(long id);
    void addEvent(Event event);
    void updateEvent(Event event);
    void deleteEvent(long id);
}
