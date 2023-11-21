package com.example.backend.repositories.event;

import com.example.backend.models.Event;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("mock")
public class EventRepositoryMock implements EventRepository {
    private List<Event> events = new ArrayList<>();

    public EventRepositoryMock() {
        for (int i = 0; i < 12; i++) {
            events.add(Event.createSampleEvent());
        }
    }

    @Override
    public List<Event> getEvents() {
        return events;
    }

    @Override
    public Event getEvent(long id) {
        for (Event event : events) {
            if (event.getId() == id) {
                return event; // Success: Event found
            }
        }
        throw new IllegalArgumentException("Event not found");
    }

    @Override
    public void addEvent(Event event) {
        events.add(event);
    }

    @Override
    public void updateEvent(Event updatedEvent) {
        for (Event event : events) {
            if (event.getId() == updatedEvent.getId()) {
                int index = events.indexOf(event);
                events.set(index, updatedEvent);
            }
        }
    }

    @Override
    public void deleteEvent(long id) {
        boolean eventRemoved = events.removeIf(event -> event.getId() == id);
        if (!eventRemoved) {
            throw new IllegalArgumentException("Event not found with id: " + id);
        }

    }
}
