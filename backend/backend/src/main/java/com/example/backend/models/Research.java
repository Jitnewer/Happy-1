package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a research entity with information related to a specific theme.
 */
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

    /**
     * Enumeration representing different themes for the research.
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
     * Constructor for creating a Research with specified parameters.
     *
     * @param title          The title of the research.
     * @param firstParagraph The first paragraph of the research.
     * @param paragraphs     The set of paragraphs associated with the research.
     * @param theme          The theme of the research.
     */
    public Research(String title, String firstParagraph, Set<Paragraph> paragraphs, Theme theme) {
        this.title = title;
        this.dateTime = LocalDateTime.now();
        this.image = "img.png";
        this.firstParagraph = firstParagraph;
        this.paragraphs = paragraphs;
        this.theme = theme;
    }

    /**
     * Default constructor initializes the creation date and sets a default image.
     */
    public Research() {
        this.dateTime = LocalDateTime.now();
        this.image = "img.png";
    }

    /**
     * Gets the unique identifier of the research.
     *
     * @return The research's identifier.
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the research.
     *
     * @param id The research's identifier.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the title of the research.
     *
     * @return The research's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the research.
     *
     * @param title The research's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the theme of the research.
     *
     * @return The research's theme.
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Sets the theme of the research.
     *
     * @param theme The research's theme.
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /**
     * Gets the date and time of the research creation.
     *
     * @return The creation date and time.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time of the research creation.
     *
     * @param dateTime The creation date and time.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the image associated with the research.
     *
     * @return The image filename.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image associated with the research.
     *
     * @param image The image filename.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the first paragraph of the research description.
     *
     * @return The first paragraph.
     */
    public String getFirstParagraph() {
        return firstParagraph;
    }

    /**
     * Sets the first paragraph of the research description.
     *
     * @param firstParagraph The first paragraph.
     */
    public void setFirstParagraph(String firstParagraph) {
        this.firstParagraph = firstParagraph;
    }

    /**
     * Gets the set of paragraphs associated with the research.
     *
     * @return The set of paragraphs.
     */
    public Set<Paragraph> getParagraphs() {
        return paragraphs;
    }

    /**
     * Sets the set of paragraphs associated with the research.
     *
     * @param paragraphs The set of paragraphs.
     */
    public void setParagraphs(Set<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    /**
     * Provides a string representation of the Research object.
     *
     * @return A string representation of the Research.
     */
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
