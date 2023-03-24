package com.oliver.example.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "album")
public class Album {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  private LocalDate published;

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
  public LocalDate getPublished() {
    return published;
  }
  public void setPublished(LocalDate published) {
    this.published = published;
  }
}
