package com.example.backend.repositories.research;

import com.example.backend.models.Challenge;
import com.example.backend.models.Research;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("RESEARCH.JPA")
@Primary
public class ResearchRepositoryRPA extends AbstractEntityRepositoryJpa<Research> {

    public ResearchRepositoryRPA() {
        super(Research.class);
    }

    @Override
    public List<Research> findAll() {
        return super.findAll();
    }

    @Override
    public List<Research> findByQuery(String jpqlName, Object... params) {
        return super.findByQuery(jpqlName, params);
    }

    @Override
    public Research findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Research save(Research entity) {
        return super.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

}
