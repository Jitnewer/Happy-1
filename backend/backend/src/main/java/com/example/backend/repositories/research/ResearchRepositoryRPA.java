package com.example.backend.repositories.research;

import com.example.backend.models.Challenge;
import com.example.backend.models.Research;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Primary
public class ResearchRepositoryRPA implements ResearchRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Research> getResearches() {
        String jpql = "SELECT r FROM Research r";
        TypedQuery<Research> query = entityManager.createQuery(jpql, Research.class);
        return query.getResultList();
    }

    @Override
    public Research getResearch(long id) {
        return entityManager.find(Research.class, id);
    }

    @Override
    @Transactional
    public void addResearch(Research research) {
        entityManager.persist(research);
    }

    @Override
    @Transactional
    public void updateResearch(Research research) {
        entityManager.merge(research);
    }

    @Override
    @Transactional
    public void deleteResearch(long id) {
        Research research = entityManager.find(Research.class, id);
        if (research != null) {
            entityManager.remove(research);
        }
    }
}
