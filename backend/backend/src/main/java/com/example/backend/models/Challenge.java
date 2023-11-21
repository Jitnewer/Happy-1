package com.example.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TEXT")
    private String firstParagraph;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Paragraph> paragraphs = new HashSet<>();

    // Constructors



    // Constructor with parameters
    public Challenge(String title, String firstParagraph, Set<Paragraph> paragraphs) {
        this.title = title;
        this.dateTime = LocalDateTime.now();
        this.firstParagraph = firstParagraph;
        this.paragraphs = paragraphs;

    }

    public Challenge() {
        this.dateTime = LocalDateTime.now();

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setFirstParagraph(String firstParagraph) {
        this.firstParagraph = firstParagraph;
    }

    public void setParagraphs(Set<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    // Getter methods
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getFirstParagraph() {
        return firstParagraph;
    }

    public Set<Paragraph> getParagraphs() {
        return paragraphs;
    }


    // Other methods

}
