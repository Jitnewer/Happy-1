package com.example.backend.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

/**
 * Represents a paragraph with title and content, associated with a Challenge, Research, or Network.
 */
@Entity
@Table(name = "paragraphs")
public class Paragraph implements Identifiable {

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

    /**
     * Default constructor for Paragraph.
     */
    public Paragraph() {
    }

    /**
     * Constructor with parameters for creating a Paragraph associated with a Challenge or Research.
     *
     * @param title     The title of the paragraph.
     * @param content   The content of the paragraph.
     * @param challenge The associated Challenge (can be null).
     * @param research  The associated Research (can be null).
     */
    public Paragraph(String title, String content, Challenge challenge, Research research) {
        this.title = title;
        this.content = content;
        this.challenge = challenge;
        this.research = research;
    }

    /**
     * Gets the unique identifier of the paragraph.
     *
     * @return The paragraph's identifier.
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the paragraph.
     *
     * @param id The paragraph's identifier.
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the title of the paragraph.
     *
     * @return The paragraph's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the paragraph.
     *
     * @param title The paragraph's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the content of the paragraph.
     *
     * @return The paragraph's content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the paragraph.
     *
     * @param content The paragraph's content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the associated Challenge.
     *
     * @return The associated Challenge (can be null).
     */
    public Challenge getChallenge() {
        return challenge;
    }

    /**
     * Sets the associated Challenge.
     *
     * @param challenge The associated Challenge (can be null).
     */
    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    /**
     * Gets the associated Research.
     *
     * @return The associated Research (can be null).
     */
    public Research getResearch() {
        return research;
    }

    /**
     * Sets the associated Research.
     *
     * @param research The associated Research (can be null).
     */
    public void setResearch(Research research) {
        this.research = research;
    }

    /**
     * Gets the associated Network.
     *
     * @return The associated Network (can be null).
     */
    public Network getNetwork() {
        return network;
    }

    /**
     * Sets the associated Network.
     *
     * @param network The associated Network (can be null).
     */
    public void setNetwork(Network network) {
        this.network = network;
    }

    /**
     * Provides a string representation of the Paragraph object.
     *
     * @return A string representation of the Paragraph.
     */
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
