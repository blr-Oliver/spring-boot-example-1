package com.oliver.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "album")
public class Album {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate published;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {
      CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
  })
  @JoinTable(name = "album_track",
      joinColumns = @JoinColumn(name = "album_id"),
      inverseJoinColumns = @JoinColumn(name = "track_id")
  )
  @OrderColumn(name = "idx")
  private List<Track> tracks;

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

  public List<Track> getTracks() {
    return tracks;
  }
  public void setTracks(List<Track> tracks) {
    this.tracks = tracks;
  }
}
