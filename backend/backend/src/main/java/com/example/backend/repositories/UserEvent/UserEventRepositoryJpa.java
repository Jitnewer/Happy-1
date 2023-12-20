package com.example.backend.repositories.UserEvent;

import com.example.backend.models.Challenge;
import com.example.backend.models.UserEvent;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("USEREVENT.JPA")
@Primary
public class UserEventRepositoryJpa extends AbstractEntityRepositoryJpa<UserEvent> {
    public UserEventRepositoryJpa() {
        super(UserEvent.class);
    }

    @Override
    public List<UserEvent> findAll() {
        return super.findAll();
    }

    @Override
    public List<UserEvent> findByQuery(String jpqlName, Object... params) {
        return super.findByQuery(jpqlName, params);
    }

    @Override
    public UserEvent findById(Long id) {
        return super.findById(id);
    }

    @Override
    public UserEvent save(UserEvent entity) {
        return super.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }



}
