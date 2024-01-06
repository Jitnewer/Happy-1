package com.example.backend.repositories.newsletter;

import com.example.backend.models.Challenge;
import com.example.backend.models.Newsletter;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("NEWSLETTER.JPA")
@Primary
public class NetworkRepositoryJpa extends AbstractEntityRepositoryJpa<Newsletter> {


    public NetworkRepositoryJpa() {
        super(Newsletter.class);
    }

    @Override
    public List<Newsletter> findAll() {
        return super.findAll();
    }

    @Override
    public List<Newsletter> findByQuery(String jpqlName, Object... params) {
        return super.findByQuery(jpqlName, params);
    }

    @Override
    public Newsletter findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Newsletter save(Newsletter entity) {
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
