package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.Carousel;
import com.example.backend.models.Challenge;
import com.example.backend.models.Event;
import com.example.backend.models.Paragraph;
import com.example.backend.repositories.Carousel.CarouselRepository;
import com.example.backend.repositories.Carousel.CarouselRepositoryJpa;
import com.example.backend.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authentication/carousels")
public class CarouselController {

    @Autowired
    private EntityRepository<Carousel> carouselRepository;

    @GetMapping
    public List<Carousel> getAllCarousels() {
        return carouselRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCarouselById(@PathVariable long id) {
        try {
            Carousel carousel = carouselRepository.findById(id);
            if (carousel != null) {
                return ResponseEntity.ok(carousel);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "carousel not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving event",
                    "error", e.getMessage()));
        }
    }
    }



