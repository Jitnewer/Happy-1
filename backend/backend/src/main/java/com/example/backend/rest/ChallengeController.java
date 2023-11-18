package com.example.backend.rest;

import com.example.backend.models.Challenge;
import com.example.backend.models.Paragraph;
import com.example.backend.repositories.challenge.ChallengeSpringDataJpaRepository;
import com.example.backend.repositories.paragraph.ParagraphSpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeSpringDataJpaRepository challengeRepository;
    @Autowired
    private ParagraphSpringDataJpaRepository paragraphRepository;

    @GetMapping
    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getChallengeById(@PathVariable Long id) {
        Optional<Challenge> challenge = challengeRepository.findById(id);
        return challenge.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Challenge not found with id: " + id)));
    }

    @PostMapping
    public ResponseEntity<Object> createChallenge(@RequestBody Challenge challenge) {
        if (challenge.getTitle() == null || challenge.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Title is required"));
        }

        try {
            for (Paragraph paragraph : challenge.getParagraphs()) {
                paragraph.setChallenge(challenge);
            }
            Challenge savedChallenge = challengeRepository.save(challenge);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedChallenge.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Challenge added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString(),
                    "challenge", savedChallenge));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding the challenge");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id) {
        challengeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
