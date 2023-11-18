package com.example.backend.repositories.paragraph;

import com.example.backend.models.Challenge;
import com.example.backend.models.Event;
import com.example.backend.models.Paragraph;
import com.example.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Phaser;

public interface ParagraphSpringDataJpaRepository extends JpaRepository<Paragraph, Long> {
    Optional<Paragraph> findByChallenge(Challenge challenge);

}
