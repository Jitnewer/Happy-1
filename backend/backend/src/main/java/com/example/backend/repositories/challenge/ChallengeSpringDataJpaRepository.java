package com.example.backend.repositories.challenge;

import com.example.backend.models.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeSpringDataJpaRepository extends JpaRepository<Challenge, Long> {
}
