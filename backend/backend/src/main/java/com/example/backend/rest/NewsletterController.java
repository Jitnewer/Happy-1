package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.Challenge;
import com.example.backend.models.Newsletter;
import com.example.backend.models.Paragraph;
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
@RequestMapping("/newsletters")
public class NewsletterController {

    @Autowired
    private EntityRepository<Newsletter> newsletterRepository;


    @GetMapping
    public List<Newsletter> getAllNewsletters() {
        return newsletterRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNetworkById(@PathVariable long id) {
        try {
            Newsletter newsletter = newsletterRepository.findById(id);
            if (newsletter != null) {
                return ResponseEntity.ok(newsletter);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Newsletter not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving newsletter",
                    "error", e.getMessage()));
        }
    }


    @PostMapping("/authentication")
    public ResponseEntity<Object> createNewsletter(@RequestBody Newsletter newsletter) {

        if (newsletter.getEmail() == null || newsletter.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email is required"));
        }

        try {
            newsletterRepository.save(newsletter);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newsletter.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Newsletter added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString(),
                    "newsletter", newsletter));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding the newsletter");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/superuser/{id}")
    public ResponseEntity<Object> updateNewsletter(@RequestBody Newsletter newsletter, @PathVariable Long id) {
        try {
            if (!id.equals(newsletter.getId())) {
                throw new PreConditionFailedException("Newsletter ID in the path does not match the ID in the request body.");
            }
            newsletterRepository.save(newsletter);


            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Newsletter updated successfully", "newsletter", newsletter));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error updating the newsletter");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }




    @DeleteMapping("/superuser/{id}")
    public ResponseEntity<Object> deleteNewsletter(@PathVariable long id) {
        newsletterRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Newsletter deleted successfully", "status", HttpStatus.OK));
    }

}
