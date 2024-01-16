package com.example.backend;

import com.example.backend.auth.JWToken;
import com.example.backend.models.User;
import com.example.backend.repositories.EntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${server.servlet.context-path:authentication}")
    private String servletContextPath;

    User user1, userWithMailInUse;

    @BeforeEach
    void setup() {
        if (servletContextPath == null) {
            servletContextPath = "admin";
        }
        user1 = User.createSampleUser(3);
    }

    @Test
    public void getUserById() {
        ResponseEntity<Object> response = this.getById(2);

        // check status code, location header and response body of post request
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserByIdNotFound() {
        // Do Get request for user with id not in use
        ResponseEntity<Object> response = this.getById(100);

        // Check response status
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Check response message
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertEquals("User not found with id: 100", responseBody.get("message"));
    }

    private ResponseEntity<Object> getById(long id) {
        return this.restTemplate.getForEntity("/users/" + id, Object.class);
    }

    @Test
    public void testAddUser() {
        ResponseEntity<Object> response = this.addUser(user1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();

        assertEquals("User added successfully", responseBody.get("message"));
        assertNotNull(responseBody.get("entity"));
    }

    @Test
    public void testAddUserWithEmailInUse() {
        // Initialize sample user with mail already in use
        userWithMailInUse = User.createSampleUser(1);

        ResponseEntity<Object> response = this.addUser(userWithMailInUse);

        // Check response status
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        // Check response message
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertEquals("User with mail:" + userWithMailInUse.getMail() + " is already in use", responseBody.get("message"));
    }

    private ResponseEntity<Object> addUser(User user) {
        return this.restTemplate.postForEntity("/users/admin", user, Object.class);
    }

    @Test
    public void testDeleteUser() {
        // Setup DELETE request
        String url = "/users/admin/1";
        ResponseEntity<Object> response = this.restTemplate.exchange(url,
                org.springframework.http.HttpMethod.DELETE,
                null,
                Object.class);

        // Check response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check response message
        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertEquals("User with id 1 deleted successfully", responseBody.get("message"));

        response = this.getById(1);

        // Check whether deleted user can still be found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        responseBody = (Map<?, ?>) response.getBody();
        assertEquals("User not found with id: " + 1, responseBody.get("message"));
    }

    @Autowired
    private EntityRepository<User> userEntityRepository;

    @Test
    public void updateUser() {
        // Update User
        User initialUser = userEntityRepository.findById((long) 1);
        User updatedUser = initialUser;
        updatedUser.setMail("timKlein@mail.com");

        // Setup PUT request
        String url = "/users/admin/1";
        HttpEntity<?> request = new HttpEntity<>(updatedUser);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT, request, Object.class);

        // Check whether user has been updated
        updatedUser = userEntityRepository.findById((long) 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(initialUser, updatedUser);
    }
}

