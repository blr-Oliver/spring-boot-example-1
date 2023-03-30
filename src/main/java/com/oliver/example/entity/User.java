package com.oliver.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
  @Id
  @Column(name = "account_id", nullable = false)
  private Integer accountId;

  @OneToOne(fetch = FetchType.LAZY, optional = true, orphanRemoval = false, mappedBy = "user")
  @PrimaryKeyJoinColumn
  private Account account;

  @Column(name = "public_name")
  private String publicName;

  @ManyToMany(cascade = {
      CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
  })
  @JoinTable(name = "user_favorite_track",
      joinColumns = @JoinColumn(name = "account_id"),
      inverseJoinColumns = @JoinColumn(name = "track_id")
  )
  private Set<Track> favoriteTracks;

  @ElementCollection
  @CollectionTable(name = "user_favorite_album", joinColumns = @JoinColumn(name = "account_id"))
  @Column(name = "album_id")
  private Set<Integer> favoriteAlbums;

  public Integer getAccountId() {
    return accountId;
  }
  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }
  @JsonIgnore
  public Account getAccount() {
    return account;
  }
  public void setAccount(Account account) {
    this.account = account;
  }
  public String getPublicName() {
    return publicName;
  }
  public void setPublicName(String publicName) {
    this.publicName = publicName;
  }
  public Set<Track> getFavoriteTracks() {
    return favoriteTracks;
  }
  public void setFavoriteTracks(Set<Track> favoriteTracks) {
    this.favoriteTracks = favoriteTracks;
  }
  public Set<Integer> getFavoriteAlbums() {
    return favoriteAlbums;
  }
  public void setFavoriteAlbums(Set<Integer> favoriteAlbums) {
    this.favoriteAlbums = favoriteAlbums;
  }
}
