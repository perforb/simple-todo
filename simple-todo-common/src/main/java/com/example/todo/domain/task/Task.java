package com.example.todo.domain.task;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

public class Task {

  private Long id;
  @NotBlank(message = "must not be blank")
  @Size(max = 30, message = "must be lower or equal to 30")
  private String title;
  private boolean done;
  private Instant createdAt;

  public Task() {
  }

  public Task(
    Long id,
    String title,
    boolean done,
    Instant createdAt
  ) {
    this.id = id;
    this.title = title;
    this.done = done;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
