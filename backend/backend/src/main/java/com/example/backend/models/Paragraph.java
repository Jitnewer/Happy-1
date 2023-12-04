package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "paragraphs")
public class Paragraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    @JsonIgnore
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "research_id")
    @JsonIgnore
    private Research research;


    // Constructors, getters, and setters

    // Constructors
    public Paragraph() {
    }

    public Paragraph(String title, String content, Challenge challenge) {
        this.title = title;
        this.content = content;
        this.challenge = challenge;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    // Getter and setter for challenge
    public Challenge getChallenge() {
        return challenge;
    }

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }
}
