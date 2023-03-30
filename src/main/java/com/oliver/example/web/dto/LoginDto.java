package com.oliver.example.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class LoginDto {
  @JsonProperty(value = "username", required = true)
  private String userName;
  @JsonProperty(required = true)
  @Schema(format = "password")
  private String password;

  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
}
