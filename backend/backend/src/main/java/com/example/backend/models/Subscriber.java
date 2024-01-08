package com.example.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "subscribers")
public class Subscriber implements Identifiable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    public Subscriber(String email) {
        this.email = email;
    }
    public Subscriber() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter methods
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

}
