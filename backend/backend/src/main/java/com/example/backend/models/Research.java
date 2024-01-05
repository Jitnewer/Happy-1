package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "researches")
public class Research implements Identifiable {

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

    @OneToMany(mappedBy = "research", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "research-paragraphs")
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

    // Constructors
    public Research(String title, String firstParagraph, Set<Paragraph> paragraphs, Theme theme) {
        this.title = title;
        this.dateTime = LocalDateTime.now();
        this.image = "img.png";
        this.firstParagraph = firstParagraph;
        this.paragraphs = paragraphs;
        this.theme = theme;
    }

    public Research() {
        this.dateTime = LocalDateTime.now();
        this.image = "img.png";

    }

//    // Getters and setters

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

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
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

    @Override
    public String toString() {
        return "Research{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dateTime=" + dateTime +
                ", image='" + image + '\'' +
                ", firstParagraph='" + firstParagraph + '\'' +
                ", paragraphs=" + paragraphs +
                '}';
    }
}
