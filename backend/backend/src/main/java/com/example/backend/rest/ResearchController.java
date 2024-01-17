package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.Challenge;
import com.example.backend.models.Paragraph;
import com.example.backend.models.Research;
import com.example.backend.repositories.EntityRepository;
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
    private EntityRepository<Research> researchRepository;
    @Autowired
    private EntityRepository<Paragraph> paragraphRepository;

    @GetMapping("/authentication")
    public List<Research> getAllResearches() {
        return researchRepository.findAll();
    }

    @GetMapping("/authentication/{id}")
    public ResponseEntity<Object> getResearchById(@PathVariable long id) {
        try {
            Research research = researchRepository.findById(id);
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

    @GetMapping("/authentication/getByTheme/{theme}")
    public ResponseEntity<Object> getResearchesByTheme(@PathVariable String theme) {
        try {
            Research.Theme themeEnum = Research.Theme.valueOf(theme.toUpperCase());

            List<Research> researches = researchRepository.findMultipleByProperty("theme", themeEnum);
            if (researches != null) {
                return ResponseEntity.ok(researches);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Researches not found with theme: " + theme));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving challenge",
                    "error", e.getMessage()));
        }
    }
    @PostMapping("/authentication")
    public ResponseEntity<Object> createResearch(@RequestBody Research research) {
        if (research.getTitle() == null || research.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Title is required"));
        }

        try {
            // Save the research entity first to generate a valid ID

            researchRepository.save(research);

            // Set the research property in each paragraph and persist them
            for (Paragraph paragraph : research.getParagraphs()) {
                paragraph.setResearch(research);
                paragraphRepository.save(paragraph);
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(research.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Research added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString(),
                    "entity", research));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding the research");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/authentication/{id}")
    public ResponseEntity<Object> updateResearch(@RequestBody Research research, @PathVariable Long id) {
        try {
            if (!id.equals(research.getId())) {
                throw new PreConditionFailedException("Network ID in the path does not match the ID in the request body.");
            }
            researchRepository.save(research);

            for (Paragraph paragraph : research.getParagraphs()) {
                paragraph.setResearch(research);
                paragraphRepository.save(paragraph);

            }


            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Research updated successfully", "research", research));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error updating the research");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/authentication/{id}")
    public ResponseEntity<Object> deleteResearch(@PathVariable long id) {
        researchRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Research deleted successfully", "status", HttpStatus.OK));
    }


}
