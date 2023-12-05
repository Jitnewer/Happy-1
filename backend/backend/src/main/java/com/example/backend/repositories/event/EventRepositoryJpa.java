package com.example.backend.repositories.event;

import com.example.backend.models.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Primary
public class EventRepositoryJpa implements EventRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> getEvents() {
        String jpql = "SELECT s FROM Event s";
        TypedQuery<Event> query = entityManager.createQuery(jpql, Event.class);
        return query.getResultList();
    }

    @Override
    public Event getEvent(long id) {
        return entityManager.find(Event.class, id);
    }

    @Override
    @Transactional
    public void addEvent(Event event) {
        entityManager.persist(event);

    }

    @Override
    @Transactional
    public void updateEvent(Event event) {
        entityManager.merge(event);
    }

    @Override
    @Transactional
    public void deleteEvent(long id) {
        Event event = entityManager.find(Event.class, id);
        if (event != null) {
            entityManager.remove(event);
        }
    }
}
