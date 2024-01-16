package com.example.backend.repositories;

import com.example.backend.BackendApplication;
import com.example.backend.models.Carousel;
import com.example.backend.repositories.Carousel.CarouselRepositoryJpa;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.Repository;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BackendApplication.class)
@Import(CarouselDataLoader.class)
public class CarouselRepositoryJpaTests {

    @Autowired
    private EntityRepository<Carousel> carouselsRepo;

    @Autowired
    private CarouselRepositoryJpa carouselRepository;

    private List<Carousel> carousels;

    @Autowired
    CarouselDataLoader carouselDataLoader;

    @BeforeEach
    public void setup() {
        // Execute the data loader to initialize the repository
        carouselDataLoader.run();
        this.carousels = this.carouselRepository.findAll();
    }

    @Test
    void findAllCarouselsReturnsAll() {
        assertFalse(this.carousels.isEmpty());
        Carousel firstCarousel = this.carousels.get(0);
        assertNotNull(firstCarousel.getId());
        assertNotNull(firstCarousel.getTitle());
        assertNotNull(firstCarousel.getTitle());
    }

    @Test
    void findCarouselByIdReturnsCorrectCarousel() {
        // Choose an existing carousel ID from the initialized data
        Long existingCarouselId = 59L;

        // Attempt to find the carousel by ID
        Carousel foundCarousel = this.carouselRepository.findById(existingCarouselId);

        // Assert that the carousel is found and has the expected ID
        assertNotNull(foundCarousel);
        assertEquals(existingCarouselId, foundCarousel.getId());
    }

    @Test
    void saveCarousel() {
        Carousel newCarousel = new Carousel("New Carousel Title");
        Carousel savedCarousel = this.carouselRepository.save(newCarousel);
        Carousel retrievedCarousel = this.carouselRepository.findById(savedCarousel.getId());

        // Assertions
        assertNotNull(savedCarousel.getId());
        assertEquals(newCarousel.getTitle(), retrievedCarousel.getTitle());
        assertEquals(newCarousel.getTitle(), retrievedCarousel.getTitle());
    }

    @Test
    void deleteCarousel() {
        // Choose an existing carousel ID from the initialized data
        Long existingCarouselId = 173L;

        this.carouselRepository.deleteById(existingCarouselId);

        // Attempt to find the deleted carousel and check if its null
        Carousel deletedCarousel = this.carouselRepository.findById(existingCarouselId);
        assertNull(deletedCarousel);
    }


}




