package com.example.backend.rest;

import com.example.backend.auth.JWToken;
import com.example.backend.models.Carousel;
import com.example.backend.models.Challenge;
import com.example.backend.repositories.EntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ChallengeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EntityRepository<Challenge> challengeEntityRepository;

    private String generateAuthToken(String username, String role) {
        JWToken jwtToken = new JWToken(username, 1L, role);
        return jwtToken.encode("my-app", "ThisIsMyVeryLongAndSuperSecurePassphraseWithNumbersAndSymbolsAndSpecialCharacters!!!", 3600);
    }
    @Test
    void getAllChallenges() {
        ResponseEntity<List> response = restTemplate.getForEntity("/challenges/authentication", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody(), notNullValue());
    }

    @Test
    void getChallengeById() {
        Challenge challenge = new Challenge("title1", "paragraph1", null, Challenge.Theme.WATER);
        challengeEntityRepository.save(challenge);



        ResponseEntity<Challenge> response = restTemplate.getForEntity("/challenges/authentication/{id}", Challenge.class, challenge.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getId(), is(challenge.getId()));
    }


    @Test
    void createChallenge() {
        Challenge challenge = new Challenge("title1", "paragraph1", null, Challenge.Theme.WATER);
        ResponseEntity<Map> response = restTemplate.postForEntity("/challenges/authentication", challenge, Map.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().get("message"), is("Challenge added successfully"));
    }

    @Test
    void updateChallenge() {
        Challenge challenge = new Challenge("title1", "paragraph1", null, Challenge.Theme.WATER);
        challengeEntityRepository.save(challenge);

        restTemplate.put("/challenges/superuser/{id}", challenge, challenge.getId());
        Challenge updatedChallenge = challengeEntityRepository.findById(challenge.getId());
        assertThat(updatedChallenge.getTitle(), is("title1"));
    }


    @Test
    void deleteChallenge() {
        // Save the carousel in the database
        Challenge challenge = new Challenge("title1", "paragraph1", null, Challenge.Theme.WATER);
        challengeEntityRepository.save(challenge);


        ResponseEntity<Void> response = restTemplate.exchange(
                "/challenges/authentication/{id}", HttpMethod.DELETE, null, Void.class, challenge.getId());


        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the carousel is deleted from the database
        Challenge deletedChallenge = challengeEntityRepository.findById(challenge.getId());
        assertThat(deletedChallenge, nullValue());
    }


}
