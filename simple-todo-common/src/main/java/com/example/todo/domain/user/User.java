package com.example.todo.domain.user;

import java.time.Instant;

public class User {

  private Long id;
  private String userName;
  private String userId;
  private String hashedPassword;
  private Instant createdAt;

  public User() {
  }

  public User(
    Long id,
    String userName,
    String userId,
    String hashedPassword,
    Instant createdAt
  ) {
    this.id = id;
    this.userName = userName;
    this.userId = userId;
    this.hashedPassword = hashedPassword;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
