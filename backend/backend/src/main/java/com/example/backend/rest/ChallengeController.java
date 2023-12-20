package com.example.backend.rest;

import com.example.backend.models.Challenge;
import com.example.backend.models.Event;
import com.example.backend.models.Paragraph;
import com.example.backend.repositories.challenge.ChallengeRepository;
import com.example.backend.repositories.paragraph.ParagraphRepository;
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

@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private ParagraphRepository paragraphRepository;

    @GetMapping
    public List<Challenge> getAllChallenges() {
        return challengeRepository.getChallenges();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getChallengeById(@PathVariable Long id) {
        try {
            Challenge challenge = challengeRepository.getChallenge(id);
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

    @PostMapping()
    public ResponseEntity<Object> createChallenge(@RequestBody Challenge challenge) {
//        if (challenge.getTitle() == null || challenge.getTitle().isEmpty()) {
//            return ResponseEntity.badRequest().body(Map.of("message", "Title is required"));
//        }

        return ResponseEntity.ok().body(challenge);
//        try {
////            for (Paragraph paragraph : challenge.getParagraphs()) {
////                paragraph.setChallenge(challenge);
////                paragraphRepository.addParagraph(paragraph);
////            }
////            challengeRepository.addChallenge(challenge);
////
////            URI location = ServletUriComponentsBuilder
////                    .fromCurrentRequest()
////                    .path("/{id}")
////                    .buildAndExpand(challenge.getId())
////                    .toUri();
//
//            return ResponseEntity.created(location).body(Map.of(
//                    "message", "Challenge added successfully",
//                    "status", HttpStatus.CREATED.value(),
//                    "location", location.toString()));
//        } catch (Exception e) {
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("message", "Error adding the challenge");
//            errorResponse.put("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//        }
    }

//    @PutMapping("/{id}")
//


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable Long id) {
        challengeRepository.deleteChallenge(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
