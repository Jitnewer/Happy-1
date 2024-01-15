package com.example.backend.repositories;

import com.example.backend.models.Carousel;
import com.example.backend.repositories.Carousel.CarouselRepositoryJpa;
import jakarta.persistence.Entity;
import org.aspectj.apache.bcel.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Import(CarouselDataLoader.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = { Repository.class, Entity.class }))

public class CarouselRepositoryJpaTests {

    @Qualifier("CAROUSEL.JPA")

    @Autowired
    private  EntityRepository<Carousel> carouselsRepo;

    @Autowired
    private CarouselRepositoryJpa carouselRepository;

    private List<Carousel> carousels;

    @Autowired
     CarouselDataLoader carouselDataLoader;

    @BeforeEach
    public void setup() {
        // Voer de dataloader uit om de repository te initialiseren
        carouselDataLoader.run();
        this.carousels = this.carouselRepository.findAll();
    }

    @Test
    void findAllCarouselsReturnsAll() {
        assertFalse(this.carousels.isEmpty());
    }
}
