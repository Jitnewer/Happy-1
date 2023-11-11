package com.example.backend.repositories.event;

import com.example.backend.models.Event;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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
                return event;
            }
        }
        return null;
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
        events.removeIf(event -> event.getId() == id);
    }

}
