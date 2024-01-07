package com.example.backend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @Column (nullable = false)
    private LocalDateTime dateTime;


  public Carousel (String title) {
      this.title = title;
      this.dateTime = LocalDateTime.now();
      this.image = "img.png";


  }



  public Carousel() {
        this.dateTime = LocalDateTime.now();;
        this.image = "assets/carouselPic/imagePlaceholder.jpg";
  }

    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {

    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public void setText(String text) {
        this.title = text;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }




}
