package com.example.backend.repositories.event;

import com.example.backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EventSpringDataJpaRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByName(String name);

}
