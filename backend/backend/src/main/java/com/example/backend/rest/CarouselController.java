package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.Carousel;
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
@RequestMapping("/carousels")
public class CarouselController {

    @Autowired
    private EntityRepository<Carousel> carouselRepository;

    @GetMapping("authentication")
    public List<Carousel> getAllCarousels() {
        return carouselRepository.findAll();
    }

    @GetMapping("/superuser/{id}")
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
    @PostMapping("/superuser")
    public ResponseEntity<Object> createCarousel(@RequestBody Carousel carousel) {
        try {
            carouselRepository.save(carousel);


            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(carousel.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "carousel added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString(),
                    "carousel", carousel));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding the carousel");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @PutMapping("/superuser/{id}")
    public ResponseEntity<Object> updateCarousel(@RequestBody Carousel carousel, @PathVariable Long id) {
        try {
            if (!id.equals(carousel.getId())) {
                throw new PreConditionFailedException("Event ID in the path does not match the ID in the request body.");
            }
            carouselRepository.save(carousel);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Event updated successfully", "carousel", carousel));
            }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error updating the carousel",
                    "error", e.getMessage()));
    }
    }
    @DeleteMapping("/superuser/{id}")
    public ResponseEntity<Object> deleteCarousel(@PathVariable long id) {
        try {
            carouselRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Carousel with id " + id + " deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error deleting the event",
                    "error", e.getMessage()));
        }
    }

}



