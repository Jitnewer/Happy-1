package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime dateTime;



    @Column(nullable = false)
    private String image;

    @Column(columnDefinition = "TEXT")
    private String firstParagraph;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Paragraph> paragraphs = new HashSet<>();

    // Constructors



    // Constructor with parameters
    public Challenge(String title, String firstParagraph, Set<Paragraph> paragraphs) {
        this.title = title;
        this.dateTime = LocalDateTime.now();
        this.image = "img.png";
        this.firstParagraph = firstParagraph;
        this.paragraphs = paragraphs;

    }

    public Challenge() {
        this.dateTime = LocalDateTime.now();
        this.image = "img.png";

    }

    public String getImage() {
        return image;
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

    public void setImage(String image) {
        this.image = image;
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
