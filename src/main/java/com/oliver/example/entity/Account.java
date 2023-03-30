package com.oliver.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(name = "password_hash", nullable = false)
  @JsonIgnore
  private String password;

  @Column
  private boolean locked;

  @Column(name = "registered_at", nullable = false, updatable = false, insertable = false)
  private Instant registeredAt;

  @ElementCollection(fetch = FetchType.EAGER)
  @Fetch(FetchMode.JOIN)
  @CollectionTable(name = "account_role", joinColumns = @JoinColumn(name = "account_id"))
  @Column(name = "role")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Set<String> roles;

  @OneToOne(fetch = FetchType.LAZY, optional = true, orphanRemoval = false, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
  @JoinColumn(name = "id", referencedColumnName = "account_id")
  @JsonIgnore
  private User user;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public boolean isLocked() {
    return locked;
  }
  public void setLocked(boolean locked) {
    this.locked = locked;
  }
  @JsonIgnore
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @JsonProperty(required = false)
  public boolean hasUser() {
    return this.user != null;
  }
}
