package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.Carousel;
import com.example.backend.models.Event;
import com.example.backend.repositories.Carousel.CarouselRepository;
import com.example.backend.repositories.Carousel.CarouselRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authentication/carousels")
public class CarouselController {

    @Autowired
    private CarouselRepository carouselRepository;

    @GetMapping
    public ResponseEntity<Object> getCarousel() {
        try {
            List<Carousel> carousels = carouselRepository.getCarousels();
            if (carousels.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Carousels not found"));
            }
            return ResponseEntity.ok(carousels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving events",
                    "error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCarousel(@PathVariable long id) {
        try {
            Carousel carousel = carouselRepository.getCarousel(id);
            if (carousel != null) {
                return ResponseEntity.ok(carousel);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Carousel not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving Carousel",
                    "error", e.getMessage()));
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<Object> addCarousel(@RequestBody Carousel carousel) {
        try {
            carouselRepository.addCarousel(carousel);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(carousel.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Carousel added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error adding the carousel",
                    "error", e.getMessage()));
        }
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<Object> updateCarousel(@RequestBody Carousel carousel, @PathVariable Long id) {
        try {
            // Ensure the ID in the path matches the ID in the request body
            if (!id.equals(carousel.getId())) {
                throw new PreConditionFailedException("Carousel ID in the path does not match the ID in the request body.");
            }

            carouselRepository.updateCarousel(carousel);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Carousel updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error updating the carousel",
                    "error", e.getMessage()));
        }
    }
        @DeleteMapping("/admin/{id}")
        public ResponseEntity<Object> deleteCarousel(@PathVariable long id) {
            try {
                carouselRepository.deleteCarousel(id);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Carousel with id " + id + " deleted successfully"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "message", "Error deleting the carousel",
                        "error", e.getMessage()));
            }
        }
    }



