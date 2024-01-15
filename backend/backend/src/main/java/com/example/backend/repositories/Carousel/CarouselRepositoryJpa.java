package com.example.backend.repositories.Carousel;

import com.example.backend.models.Carousel;
import com.example.backend.models.Event;
import com.example.backend.models.Research;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CAROUSEL.JPA")
@Primary
public class CarouselRepositoryJpa extends AbstractEntityRepositoryJpa<Carousel> {


    public CarouselRepositoryJpa() {
        super(Carousel.class);
    }

    @Override
    public List<Carousel> findAll() {
        return super.findAll();
    }

    @Override
    public List<Carousel> findByQuery(String jpqlName, Object... params) {
        return super.findByQuery(jpqlName, params);
    }

    @Override
    public Carousel findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Carousel save(Carousel entity) {
        return super.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

}
