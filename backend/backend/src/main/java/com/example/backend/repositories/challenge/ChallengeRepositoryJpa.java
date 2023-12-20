package com.example.backend.repositories.challenge;

import com.example.backend.models.Challenge;
import com.example.backend.models.User;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CHALLANGE.JPA")
@Primary
public class ChallengeRepositoryJpa extends AbstractEntityRepositoryJpa<Challenge> {


    public ChallengeRepositoryJpa() {
        super(Challenge.class);
    }

    @Override
    public List<Challenge> findAll() {
        return super.findAll();
    }

    @Override
    public List<Challenge> findByQuery(String jpqlName, Object... params) {
        return super.findByQuery(jpqlName, params);
    }

    @Override
    public Challenge findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Challenge save(Challenge entity) {
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
