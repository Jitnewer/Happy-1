package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.Challenge;
import com.example.backend.models.Event;
import com.example.backend.models.Paragraph;
import com.example.backend.repositories.EntityRepository;
import com.example.backend.repositories.challenge.ChallengeRepository;
import com.example.backend.repositories.paragraph.ParagraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private EntityRepository<Challenge> challengeRepository;
    @Autowired
    private EntityRepository<Paragraph> paragraphRepository;

    @GetMapping
    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getChallengeById(@PathVariable long id) {
        try {
            Challenge challenge = challengeRepository.findById(id);
            if (challenge != null) {
                return ResponseEntity.ok(challenge);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Event not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving event",
                    "error", e.getMessage()));
        }
    }




    @GetMapping("/getByTheme/{theme}")
    public ResponseEntity<Object> getChallengesByTheme(@PathVariable String theme) {
        try {
            Challenge.Theme themeEnum = Challenge.Theme.valueOf(theme.toUpperCase());

            List<Challenge> challenges = challengeRepository.findMultipleByProperty("theme", themeEnum);
            if (challenges != null) {
                return ResponseEntity.ok(challenges);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Challenges not found with theme: " + theme));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving challenge",
                    "error", e.getMessage()));
        }
    }

    @PostMapping("/superuser")
    public ResponseEntity<Object> createChallenge(@RequestBody Challenge challenge) {

        if (challenge.getTitle() == null || challenge.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Title is required"));
        }

        try {
            challengeRepository.save(challenge);

            // Now you can save the paragraphs with the associated challenge
            for (Paragraph paragraph : challenge.getParagraphs()) {
                paragraph.setChallenge(challenge);
                paragraphRepository.save(paragraph);
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(challenge.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Challenge added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString(),
                    "entity", challenge));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding the challenge");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/superuser/{id}")
    public ResponseEntity<Object> updateChallenge(@RequestBody Challenge challenge, @PathVariable Long id) {
        try {
            if (!id.equals(challenge.getId())) {
                throw new PreConditionFailedException("Event ID in the path does not match the ID in the request body.");
            }
            challengeRepository.save(challenge);

            for (Paragraph paragraph : challenge.getParagraphs()) {
                paragraph.setChallenge(challenge);
                paragraphRepository.save(paragraph);

            }


            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Challenge updated successfully", "challenge", challenge));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error updating the challenge");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }




    @DeleteMapping("/superuser/{id}")
    public ResponseEntity<Object> deleteChallenge(@PathVariable long id) {
        challengeRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Challenge deleted successfully", "status", HttpStatus.OK));
    }

}
