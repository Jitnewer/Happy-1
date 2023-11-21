package com.example.backend.repositories.challenge;

import com.example.backend.models.Challenge;

import java.util.List;

public interface ChallengeRepository {
    List<Challenge> getChallenges();
    Challenge getChallenge(long id);
    void addChallenge(Challenge challenge);
    void updateChallenge(Challenge challenge);
    void deleteChallenge(long id);
}
