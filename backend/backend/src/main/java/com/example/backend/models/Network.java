package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a network entity with information related to a specific theme.
 */
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

    /**
     * Enumeration representing different themes for the network.
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

    /**
     * Default constructor initializes the creation date and sets a default image.
     */
    public Network() {
        this.dateTime = LocalDateTime.now();
        this.image = "img.png";
    }

    /**
     * Gets the unique identifier of the network.
     *
     * @return The network's identifier.
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the network.
     *
     * @param id The network's identifier.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the theme of the network.
     *
     * @return The network's theme.
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Sets the theme of the network.
     *
     * @param theme The network's theme.
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /**
     * Gets the title of the network.
     *
     * @return The network's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the network.
     *
     * @param title The network's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the date and time of the network creation.
     *
     * @return The creation date and time.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time of the network creation.
     *
     * @param dateTime The creation date and time.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the image associated with the network.
     *
     * @return The image filename.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image associated with the network.
     *
     * @param image The image filename.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the first paragraph of the network description.
     *
     * @return The first paragraph.
     */
    public String getFirstParagraph() {
        return firstParagraph;
    }

    /**
     * Sets the first paragraph of the network description.
     *
     * @param firstParagraph The first paragraph.
     */
    public void setFirstParagraph(String firstParagraph) {
        this.firstParagraph = firstParagraph;
    }

    /**
     * Gets the set of paragraphs associated with the network.
     *
     * @return The set of paragraphs.
     */
    public Set<Paragraph> getParagraphs() {
        return paragraphs;
    }

    /**
     * Sets the set of paragraphs associated with the network.
     *
     * @param paragraphs The set of paragraphs.
     */
    public void setParagraphs(Set<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
