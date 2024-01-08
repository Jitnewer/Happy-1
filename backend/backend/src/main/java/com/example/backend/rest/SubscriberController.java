package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.Subscriber;
import com.example.backend.repositories.EntityRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    @Autowired
    private EntityRepository<Subscriber> subscriberRepository;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;


    @GetMapping("/superuser")
    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    @GetMapping("/superuser/{id}")
    public ResponseEntity<Object> getSubscriberById(@PathVariable long id) {
        try {
            Subscriber subscriber = subscriberRepository.findById(id);
            if (subscriber != null) {
                return ResponseEntity.ok(subscriber);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Subscriber not found with id: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error retrieving subscriber",
                    "error", e.getMessage()));
        }
    }


    @PostMapping("/superuser")
    public ResponseEntity<Object> createSubscriber(@RequestBody Subscriber subscriber) {

        if (subscriber.getEmail() == null || subscriber.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email is required"));
        }

        try {
            subscriberRepository.save(subscriber);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(subscriber.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Subscriber added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString(),
                    "entity", subscriber));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding the subscriber");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/authentication")
    public ResponseEntity<Object> subscribe(@RequestBody Subscriber subscriber) {

        if (subscriber.getEmail() == null || subscriber.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email is required"));
        }

        try {
            subscriberRepository.save(subscriber);

            sendSubscribeEmail(subscriber.getEmail());


            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(subscriber.getId())
                    .toUri();


            return ResponseEntity.created(location).body(Map.of(
                    "message", "You have subscribed for the newsletter!",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString(),
                    "entity", subscriber));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding the subscriber");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/superuser/newsletter")
    public ResponseEntity<Object> sendNewsletter(@RequestBody Subscriber subscriber) {
        try {
            sendNewsletterEmail(subscriber.getEmail());


            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(subscriber.getId())
                    .toUri();


            return ResponseEntity.created(location).body(Map.of(
                    "message", "You have send and newsletter",
                    "status", HttpStatus.OK.value(),
                    "location", location.toString(),
                    "entity", subscriber));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error sending newsletter");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/superuser/{id}")
    public ResponseEntity<Object> updateSubscriber(@RequestBody Subscriber subscriber, @PathVariable Long id) {
        try {
            if (!id.equals(subscriber.getId())) {
                throw new PreConditionFailedException("Subscriber ID in the path does not match the ID in the request body.");
            }
            subscriberRepository.save(subscriber);


            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Subscriber updated successfully", "subscriber", subscriber));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error updating the subscriber");
            errorResponse.put("error", e.getMessage());
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }




    @DeleteMapping("/superuser/{id}")
    public ResponseEntity<Object> deleteSubscriber(@PathVariable long id) {
        subscriberRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Subscriber deleted successfully", "status", HttpStatus.OK));
    }
    private void sendSubscribeEmail(String toEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            String htmlContent = buildHtmlContent("newsletter-template.html");
            helper.setFrom("rickveerman4@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Newsletter Subscription");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle exception (log or rethrow)
            e.printStackTrace();
        }
    }

    private void sendNewsletterEmail(String toEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            String htmlContent = buildHtmlContent("newsletter-template1.html");
            helper.setFrom("rickveerman4@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Newsletter Happy Hospitality");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle exception (log or rethrow)
            e.printStackTrace();
        }
    }
    private String buildHtmlContent(String template) {
        Context context = new Context();
        // You can add variables to the context if you want dynamic content in your email

        // Process Thymeleaf template to HTML
        return templateEngine.process(template, context);
    }
}
