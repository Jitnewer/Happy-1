package com.example.backend.repositories;

import com.example.backend.models.Carousel;
import com.example.backend.repositories.Carousel.CarouselRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class CarouselDataLoader implements CommandLineRunner {

    @Autowired
    private CarouselRepositoryJpa carouselRepository;

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("Running Carousel Data Loader");
        this.createInitialCarousels();
    }

    private void createInitialCarousels() {
        // Check whether the repo is empty
        List<Carousel> carousels = this.carouselRepository.findAll();
        if (!carousels.isEmpty()) {
            return; // Geen dummy-gegevens toevoegen als er al gegevens zijn
        }

        System.out.println("Configuring some initial carousels in the repository");

        Carousel carousel1 = new Carousel("Carousel 1");
        carouselRepository.save(carousel1);

        Carousel carousel2 = new Carousel("Carousel 2");
        carouselRepository.save(carousel2);

        Carousel carousel3 = new Carousel("Carousel 3");
        carouselRepository.save(carousel3);


        System.out.println("Initial carousels added to the repository");
    }
}
