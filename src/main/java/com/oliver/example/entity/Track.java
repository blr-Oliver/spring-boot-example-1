package com.oliver.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "track")
public class Track {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
  private LocalTime duration;

  @Column(nullable = true)
  private String genre;

  @ManyToMany(cascade = {
      CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
  })
  @JoinTable(name = "track_author",
      joinColumns = @JoinColumn(name = "track_id"),
      inverseJoinColumns = @JoinColumn(name = "author_id")
  )
  private Set<Artist> authors;

  @ManyToMany(mappedBy = "tracks")
  @JsonIgnore
  private Collection<Album> albums;

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
  public Set<Artist> getAuthors() {
    return authors;
  }
  public void setAuthors(Set<Artist> authors) {
    this.authors = authors;
  }
  public Collection<Album> getAlbums() {
    return albums;
  }
}
