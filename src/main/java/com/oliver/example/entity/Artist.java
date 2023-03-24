package com.oliver.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "author")
public class Artist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "short_name", nullable = true)
  private String shortName;

  @ManyToMany(mappedBy = "authors")
  @JsonIgnore
  private Collection<Track> tracks;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getFullName() {
    return fullName;
  }
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  public String getShortName() {
    return shortName;
  }
  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public Collection<Track> getTracks() {
    return tracks;
  }
}
