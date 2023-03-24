package com.oliver.example.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "user")
public class User {
  @Id
  private String login;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(name = "public_name", unique = true, nullable = false)
  private String publicName;

  @Column(name = "registered_at", nullable = false)
  private Instant registeredAt;

  public String getLogin() {
    return login;
  }
  public void setLogin(String login) {
    this.login = login;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPublicName() {
    return publicName;
  }
  public void setPublicName(String publicName) {
    this.publicName = publicName;
  }
  public Instant getRegisteredAt() {
    return registeredAt;
  }
  public void setRegisteredAt(Instant registeredAt) {
    this.registeredAt = registeredAt;
  }
}
