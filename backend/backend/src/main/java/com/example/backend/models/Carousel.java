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
    private String text;
    @Column(nullable = false)
    private String image;
    @Temporal(TemporalType.TIMESTAMP)
    @Column (nullable = false)
    private LocalDateTime dateTime;


  public Carousel (String text) {
      this.text = text;
      this.dateTime = LocalDateTime.now();
      this.image = "image.png";


  }



  public Carousel() {
        this.dateTime = LocalDateTime.now();;
        this.image = "image.png";
  }

    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {

    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public void setText(String text) {
        this.text = text;
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




    @Override
    public String toString() {
        return "Carousel{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", dateTime=" + dateTime +
                ", image='" + image + '\'' +
                '}';
  }
}
