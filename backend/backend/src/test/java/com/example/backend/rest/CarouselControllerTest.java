package com.example.backend.rest;

import com.example.backend.auth.JWToken;
import com.example.backend.models.Carousel;
import com.example.backend.repositories.EntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.net.URI;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CarouselControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EntityRepository<Carousel> carouselRepository;

    private String generateAuthToken(String username, String role) {
        JWToken jwtToken = new JWToken(username, 1L, role);
        return jwtToken.encode("my-app", "ThisIsMyVeryLongAndSuperSecurePassphraseWithNumbersAndSymbolsAndSpecialCharacters!!!", 3600);
    }
    @Test
    void getAllCarousels() {
        ResponseEntity<List> response = restTemplate.getForEntity("/carousels/authentication", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody(), notNullValue());
    }

    @Test
    void getCarouselById() {
        Carousel carousel = new Carousel("Test Carousel");
        carouselRepository.save(carousel);

        String authToken = generateAuthToken("nickybosveld@gmail.com", "0");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Carousel> response = restTemplate
                .exchange("/carousels/superuser/{id}", HttpMethod.GET, requestEntity, Carousel.class, carousel.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getId(), is(carousel.getId()));
    }


    @Test
    void createCarousel() {
        Carousel carousel = new Carousel("Test Carousel");
        ResponseEntity<Map> response = restTemplate.postForEntity("/carousels/superuser", carousel, Map.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().get("message"), is("carousel added successfully"));
    }

    @Test
    void updateCarousel() {
        Carousel carousel = new Carousel("Test Carousel");
        carouselRepository.save(carousel);

        String authToken = generateAuthToken("nickybosveld@gmail.com", "0");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);

        HttpEntity<Carousel> requestEntity = new HttpEntity<>(carousel, headers);

        restTemplate.exchange("/carousels/superuser/{id}", HttpMethod.PUT, requestEntity, Carousel.class, carousel.getId());

        Carousel updatedCarousel = carouselRepository.findById(carousel.getId());
        assertThat(updatedCarousel.getTitle(), is("Updated Carousel"));
    }


    @Test
    void deleteCarousel() {
        // Save the carousel in the database
        Carousel carousel = new Carousel("Test Carousel");
        carouselRepository.save(carousel);

        String authToken = generateAuthToken("nickybosveld@gmail.com", "0");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        // Use restTemplate's exchange method to handle DELETE request
        ResponseEntity<Void> response = restTemplate.exchange(
                "/carousels/superuser/{id}", HttpMethod.DELETE, requestEntity, Void.class, carousel.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that the carousel is deleted from the database
        Carousel deletedCarousel = carouselRepository.findById(carousel.getId());
        assertThat(deletedCarousel, nullValue());
    }


}
