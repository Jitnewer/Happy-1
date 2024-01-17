package com.example.backend.rest;

import com.example.backend.auth.JWToken;
import com.example.backend.models.Research;
import com.example.backend.repositories.EntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ResearchControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EntityRepository<Research> researchEntityRepository;

    private String generateAuthToken(String username, String role) {
        JWToken jwtToken = new JWToken(username, 1L, role);
        return jwtToken.encode("my-app", "ThisIsMyVeryLongAndSuperSecurePassphraseWithNumbersAndSymbolsAndSpecialCharacters!!!", 3600);
    }

    @Test
    void getAllResearches() {
        ResponseEntity<List> response = restTemplate.getForEntity("/researches/authentication", List.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), notNullValue());
    }

    @Test
    void getResearchById() {
        Research research = new Research("Test Research", "Test Research Description", null, Research.Theme.PROTEIN_TRANSITION);
        researchEntityRepository.save(research);

        ResponseEntity<Research> response = restTemplate.getForEntity("/researches/authentication/" + research.getId(), Research.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody(), notNullValue());
        assertEquals(research.getId(), response.getBody().getId());
    }

    @Test
    void getResearchesByTheme() {
        Research research = new Research("Test Research", "Test Research Description", null, Research.Theme.PROTEIN_TRANSITION);
        researchEntityRepository.save(research);

        ResponseEntity<List> response = restTemplate.getForEntity("/researches/authentication/getByTheme/" + research.getTheme(), List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody(), notNullValue());
    }

    // Maybe more tests here
}
