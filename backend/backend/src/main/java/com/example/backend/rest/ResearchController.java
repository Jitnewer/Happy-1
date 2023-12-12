package com.example.backend.rest;

import com.example.backend.models.Challenge;
import com.example.backend.models.Paragraph;
import com.example.backend.models.Research;
import com.example.backend.repositories.paragraph.ParagraphRepository;
import com.example.backend.repositories.research.ResearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/researches")
public class ResearchController {

    @Autowired
    private ResearchRepository researchRepository;
    @Autowired
    private ParagraphRepository paragraphRepository;

    @GetMapping
    public List<Research> getAllResearches() {
        return researchRepository.getResearches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getResearchById(@PathVariable Long id) {
        try {
            Research research = researchRepository.getResearch(id);
            if (research != null) {
                return ResponseEntity.ok(research);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Research not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving research",
                    "error", e.getMessage()));
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createResearch(@RequestBody Research research) {
        if (research.getTitle() == null || research.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Title is required"));
        }

        try {
            // Save the research entity first to generate a valid ID
            researchRepository.addResearch(research);

            // Set the research property in each paragraph and persist them
            for (Paragraph paragraph : research.getParagraphs()) {
                paragraph.setResearch(research);
                paragraphRepository.addParagraph(paragraph);
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(research.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Research added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString()));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding the research");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteResearch(@PathVariable Long id) {
        researchRepository.deleteResearch(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
