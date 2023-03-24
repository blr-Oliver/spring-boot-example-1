package com.oliver.example.entity;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "track")
public class Track {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private LocalTime duration;

  @Column(nullable = true)
  private String genre;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public LocalTime getDuration() {
    return duration;
  }
  public void setDuration(LocalTime duration) {
    this.duration = duration;
  }
  public String getGenre() {
    return genre;
  }
  public void setGenre(String genre) {
    this.genre = genre;
  }
}
