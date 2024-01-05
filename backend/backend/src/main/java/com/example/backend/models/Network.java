package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "networks")
public class Network implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = true)
    private String image;

    @Column(columnDefinition = "TEXT")
    private String firstParagraph;

    @OneToMany(mappedBy = "network", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "network-paragraphs")
    private Set<Paragraph> paragraphs = new HashSet<>();

    public enum Theme {
        FOOD_WASTE,
        DISTRIBUTION,
        ENERGY_TRANSITION,
        SINGLE_USED_PLASTIC,
        PROTEIN_TRANSITION,
        WATER
    }

    @Column(nullable = false)
    private Theme theme;

    public Network() {
        this.dateTime = LocalDateTime.now();
        this.image = "img.png";
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstParagraph() {
        return firstParagraph;
    }

    public void setFirstParagraph(String firstParagraph) {
        this.firstParagraph = firstParagraph;
    }

    public Set<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(Set<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
