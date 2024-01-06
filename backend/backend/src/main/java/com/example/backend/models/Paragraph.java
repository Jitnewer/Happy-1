package com.example.backend.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@Entity
@Table(name = "paragraphs")
public class Paragraph implements Identifiable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    @JsonBackReference(value = "challenge-paragraphs")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "research_id")
    @JsonBackReference(value = "research-paragraphs")
    private Research research;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "network_id")
    @JsonBackReference(value = "network-paragraphs")
    private Network network;


    // Constructors, getters, and setters

    // Constructors
    public Paragraph() {
    }

    public Paragraph(String title, String content, Challenge challenge, Research research) {
        this.title = title;
        this.content = content;
        this.challenge = challenge;
        this.research = research;

    }

    // Getters and Setters
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    @Override
    public String toString() {
        return "Paragraph{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", challenge=" + challenge +
                ", research=" + research +
                '}';
    }
}
