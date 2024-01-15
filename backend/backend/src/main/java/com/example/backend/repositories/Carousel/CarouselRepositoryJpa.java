package com.example.backend.repositories.Carousel;

import com.example.backend.models.Carousel;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Carousel save(Carousel entity) {
        return super.save(entity);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

}
