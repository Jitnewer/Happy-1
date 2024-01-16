package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The Challenge class represents a challenge entity in the application.
 * Challenges are topics or themes that users can engage with.
 */
@Entity
@Table(name = "challenges")
public class Challenge implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime dateTime;

    /**
     * Enum representing different themes for challenges.
     */
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

    @Column(nullable = true)
    private String image;

    @Column(columnDefinition = "TEXT")
    private String firstParagraph;

    @OneToMany(mappedBy = "challenge", orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "challenge-paragraphs")
    private Set<Paragraph> paragraphs = new HashSet<>();

    /**
     * Constructs a Challenge with the specified parameters.
     *
     * @param title          The title of the challenge.
     * @param firstParagraph The introductory paragraph for the challenge.
     * @param paragraphs     Set of paragraphs associated with the challenge.
     * @param theme          The theme of the challenge.
     */
    public Challenge(String title, String firstParagraph, Set<Paragraph> paragraphs, Theme theme) {
        this.title = title;
        this.dateTime = LocalDateTime.now();
        this.image = "img.png";
        this.firstParagraph = firstParagraph;
        this.paragraphs = paragraphs;
        this.theme = theme;
    }

    /**
     * Default constructor for Challenge.
     * Initializes date and image with default values.
     */
    public Challenge() {
        this.dateTime = LocalDateTime.now();
        this.image = "assets/challengePic/imagePlaceholder.jpg";
    }

    /**
     * Gets the theme of the challenge.
     *
     * @return The theme.
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Sets the theme of the challenge.
     *
     * @param theme The theme to set.
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /**
     * Gets the image URL of the challenge.
     *
     * @return The image URL.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the title of the challenge.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the date and time of the challenge.
     *
     * @param dateTime The date and time to set.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Sets the introductory paragraph of the challenge.
     *
     * @param firstParagraph The introductory paragraph to set.
     */
    public void setFirstParagraph(String firstParagraph) {
        this.firstParagraph = firstParagraph;
    }

    /**
     * Sets the set of paragraphs associated with the challenge.
     *
     * @param paragraphs The set of paragraphs to set.
     */
    public void setParagraphs(Set<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    /**
     * Sets the image URL of the challenge.
     *
     * @param image The image URL to set.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the unique identifier of the challenge.
     *
     * @return The unique identifier.
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the challenge.
     *
     * @param id The unique identifier to set.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the title of the challenge.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the date and time when the challenge was created.
     *
     * @return The date and time.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Gets the introductory paragraph of the challenge.
     *
     * @return The introductory paragraph.
     */
    public String getFirstParagraph() {
        return firstParagraph;
    }

    /**
     * Gets the set of paragraphs associated with the challenge.
     *
     * @return The set of paragraphs.
     */
    public Set<Paragraph> getParagraphs() {
        return paragraphs;
    }
}
