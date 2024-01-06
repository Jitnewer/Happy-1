package com.example.backend.repositories.paragraph;

import com.example.backend.models.Challenge;
import com.example.backend.models.Event;
import com.example.backend.models.Paragraph;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import com.example.backend.repositories.challenge.ChallengeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("PARAGRAPH.JPA")
@Primary
public class ParagraphRepositoryJpa extends AbstractEntityRepositoryJpa<Paragraph> {

    public ParagraphRepositoryJpa() {
        super(Paragraph.class);
    }

    @Override
    public List<Paragraph> findAll() {
        return super.findAll();
    }

    @Override
    public List<Paragraph> findByQuery(String jpqlName, Object... params) {
        return super.findByQuery(jpqlName, params);
    }

    @Override
    public Paragraph findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Paragraph save(Paragraph entity) {
        return super.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

}
