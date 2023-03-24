package com.oliver.example.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
  @Id
  private String login;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(name = "public_name", unique = true, nullable = false)
  private String publicName;

  @Column(name = "registered_at", nullable = false, updatable = false, insertable = false)
  private Instant registeredAt;

  @ElementCollection
  @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_login"))
  @Column(name = "role")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<String> roles;

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

  @JsonProperty(value = "registeredAt", required = false)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public Long getRegisteredTimestamp() {
    if (this.registeredAt == null) return null;
    return getRegisteredAt().toEpochMilli();
  }
  public Set<String> getRoles() {
    return roles;
  }
  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }
}
