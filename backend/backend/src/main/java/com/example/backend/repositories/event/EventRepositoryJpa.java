package com.example.backend.repositories.event;

import com.example.backend.models.Event;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("EVENT.JPA")
@Primary
public class EventRepositoryJpa extends AbstractEntityRepositoryJpa<Event> {

    public EventRepositoryJpa() {
        super(Event.class);
    }

    @Override
    public List<Event> findAll() {
        return super.findAll();
    }

    @Override
    public List<Event> findByQuery(String jpqlName, Object... params) {
        return super.findByQuery(jpqlName, params);
    }

    @Override
    public Event findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Event save(Event entity) {
        return super.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }


}
