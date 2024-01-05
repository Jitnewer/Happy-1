package com.example.backend.repositories.challenge;

import com.example.backend.models.Challenge;
import com.example.backend.models.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Primary
public class ChallengeRepositoryJpa implements ChallengeRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Challenge> getChallenges() {
        String jpql = "SELECT c FROM Challenge c";
        TypedQuery<Challenge> query = entityManager.createQuery(jpql, Challenge.class);
        return query.getResultList();
    }

    @Override
    public Challenge getChallenge(long id) {
        return entityManager.find(Challenge.class, id);
    }

    @Override
    @Transactional
    public void addChallenge(Challenge challenge) {
        entityManager.persist(challenge);

    }

    @Override
    @Transactional
    public void updateChallenge(Challenge challenge) {
        entityManager.merge(challenge);

    }

    @Override
    @Transactional
    public void deleteChallenge(long id) {
        Challenge challenge = entityManager.find(Challenge.class, id);
        if (challenge != null) {
            entityManager.remove(challenge);
        }
    }
}
