package com.example.backend.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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
      this.image = "assets/carouselPic/carousel_63.jpg";


  }
  public Carousel() {
        this.dateTime = LocalDateTime.now();;
        this.image = "assets/carouselPic/imagePlaceholder.jpg";
  }
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) { this.id = id;}

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



