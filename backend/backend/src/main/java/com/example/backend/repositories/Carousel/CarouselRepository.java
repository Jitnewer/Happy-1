package com.example.backend.repositories.Carousel;

import com.example.backend.models.Carousel;
import com.example.backend.models.Event;

import java.util.List;

public interface CarouselRepository {

    List<Carousel> getCarousels();

    Carousel getCarousel(long id);

    void addCarousel(Carousel carousel);
    void updateCarousel(Carousel carousel);
    void deleteCarousel(long id);

}
