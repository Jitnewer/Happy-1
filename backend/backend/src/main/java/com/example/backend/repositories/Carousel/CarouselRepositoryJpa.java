package com.example.backend.repositories.Carousel;

import com.example.backend.models.Carousel;
import com.example.backend.models.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class CarouselRepositoryJpa implements CarouselRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Carousel> getCarousels() {
        String jpql = "SELECT c FROM Carousel c";
        TypedQuery<Carousel> query = entityManager.createQuery(jpql, Carousel.class);
        return query.getResultList();
    }

    @Override
    public Carousel getCarousel(long id) {return entityManager.find(Carousel.class, id);
    }

    @Override
    @Transactional
    public void addCarousel(Carousel carousel) {
        entityManager.persist(carousel);

    }

    @Override
    @Transactional
    public void updateCarousel(Carousel carousel) {
        entityManager.merge(carousel);
    }

    @Override
    @Transactional
    public void deleteCarousel(long id) {
        Carousel carousel = entityManager.find(Carousel.class, id);
        if (carousel != null)
            entityManager.remove(carousel);
    }
}
