package com.example.backend.repositories.paragraph;

import com.example.backend.models.Challenge;
import com.example.backend.models.Event;
import com.example.backend.models.Paragraph;
import com.example.backend.repositories.challenge.ChallengeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Primary
public class ParagraphRepositoryJpa implements ParagraphRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Paragraph> getParagraphs() {
        String jpql = "SELECT p FROM Paragraph p";
        TypedQuery<Paragraph> query = entityManager.createQuery(jpql, Paragraph.class);
        return query.getResultList();
    }

    @Override
    public Paragraph getParagraph(long id) {
        return entityManager.find(Paragraph.class, id);
    }

    @Override
    @Transactional
    public void addParagraph(Paragraph paragraph) {
        entityManager.persist(paragraph);
    }

    @Override
    @Transactional
    public void updateParagraph(Paragraph paragraph) {
        entityManager.merge(paragraph);

    }

    @Override
    @Transactional
    public void deleteParagraph(long id) {
        Paragraph paragraph = entityManager.find(Paragraph.class, id);
        if (paragraph != null) {
            entityManager.remove(paragraph);
        }
    }
}
