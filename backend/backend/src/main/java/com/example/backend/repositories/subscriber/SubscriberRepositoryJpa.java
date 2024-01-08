package com.example.backend.repositories.subscriber;

import com.example.backend.models.Subscriber;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("SUBSCRIBER.JPA")
@Primary
public class SubscriberRepositoryJpa extends AbstractEntityRepositoryJpa<Subscriber> {


    public SubscriberRepositoryJpa() {
        super(Subscriber.class);
    }

    @Override
    public List<Subscriber> findAll() {
        return super.findAll();
    }

    @Override
    public List<Subscriber> findByQuery(String jpqlName, Object... params) {
        return super.findByQuery(jpqlName, params);
    }

    @Override
    public Subscriber findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Subscriber save(Subscriber entity) {
        return super.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }



    @Override
    public boolean entityWithEntityExist(String propertyName, Object value) {
        return super.entityWithEntityExist(propertyName, value);
    }

}
