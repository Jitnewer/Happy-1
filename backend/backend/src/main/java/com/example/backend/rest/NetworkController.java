package com.example.backend.rest;

import com.example.backend.models.Network;
import com.example.backend.models.Paragraph;
import com.example.backend.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/networks")
public class NetworkController {

    @Autowired
    private EntityRepository<Network> networkRepository;
    @Autowired
    private EntityRepository<Paragraph> paragraphRepository;

    @GetMapping
    List<Network> getAllNetworks() {
        return networkRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getNetworkById(@PathVariable long id) {
        try {
            Network network = networkRepository.findById(id);
            if (network != null) {
                return ResponseEntity.ok(network);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getByTheme/{theme}")
    ResponseEntity<Object> getNetworksByTheme(@PathVariable String theme) {
        try {
            Network.Theme themeEnum = Network.Theme.valueOf(theme.toUpperCase());

            List<Network> networks = networkRepository.findMultipleByProperty("theme", themeEnum);
            if (networks != null) {
                return ResponseEntity.ok(networks);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/admin")
    ResponseEntity<Object> createNetwork(@RequestBody Network network) {
        if (network.getTitle() == null || network.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Title is required"));
        }

        try {
            for (Paragraph paragraph : network.getParagraphs()) {
                paragraph.setNetwork(network);
            }
            networkRepository.save(network);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(network.getId())
                    .toUri();

            return ResponseEntity.created(location).body(Map.of(
                    "message", "Challenge added successfully",
                    "status", HttpStatus.CREATED.value(),
                    "location", location.toString()));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error adding the network");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteNetwork(@PathVariable long id) {
        networkRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
