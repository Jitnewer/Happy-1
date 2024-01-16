package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The Carousel class represents a carousel entity in the application.
 * Carousels are used to display rotating content, such as images and titles.
 */
@Entity
@Table(name = "carousels")
public class Carousel implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime dateTime;

    /**
     * Constructs a Carousel with the specified title.
     *
     * @param title The title of the carousel.
     */
    public Carousel(String title) {
        this.title = title;
        this.dateTime = LocalDateTime.now();
        this.image = "img.png";
    }

    /**
     * Default constructor for Carousel.
     * Initializes date and image with default values.
     */
    public Carousel() {
        this.dateTime = LocalDateTime.now();
        this.image = "assets/carouselPic/imagePlaceholder.jpg";
    }

    /**
     * Gets the unique identifier of the carousel.
     *
     * @return The unique identifier.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the carousel.
     *
     * @param id The unique identifier to set.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the title of the carousel.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the image URL of the carousel.
     *
     * @return The image URL.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the title of the carousel.
     *
     * @param text The title to set.
     */
    public void setText(String text) {
        this.title = text;
    }

    /**
     * Sets the image URL of the carousel.
     *
     * @param image The image URL to set.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the date and time when the carousel was created.
     *
     * @return The date and time.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time of the carousel.
     *
     * @param dateTime The date and time to set.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Returns a string representation of the Carousel object.
     *
     * @return A string representation containing carousel details.
     */
    @Override
    public String toString() {
        return "Carousel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dateTime=" + dateTime +
                ", image='" + image +
                '}';
    }
}
