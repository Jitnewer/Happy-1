package com.example.backend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "carousels")
public class Carousel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @OneToMany(mappedBy = "carousel", cascade = CascadeType.ALL, orphanRemoval = true)

    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private String image;
    @Column (nullable = false)
    private Date date;

  public Carousel (String image, String text, Date date){
      this.image = image;
      this.text = text;
      this.date = date;
  }

  public Carousel() {

  }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }
}
