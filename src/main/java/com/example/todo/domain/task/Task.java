package com.example.todo.domain.task;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class Task {

  private Integer id;
  @NotBlank(message = "must not be blank")
  @Size(max = 30, message = "must be lower or equal to 30")
  private String title;
  private boolean done;
  private LocalDateTime createdAt;

  public Task() {
  }

  public Task(
    Integer id,
    String title,
    boolean done,
    LocalDateTime createdAt
  ) {
    this.id = id;
    this.title = title;
    this.done = done;
    this.createdAt = createdAt;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}