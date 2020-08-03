package com.example.todo.application.task;

import com.example.todo.domain.task.Task;
import com.example.todo.domain.task.TaskRepository;
import com.example.todo.lib.time.ApplicationClock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class TaskService {

  private final ApplicationClock clock;
  private final TaskRepository repository;

  public TaskService(ApplicationClock clock, TaskRepository repository) {
    this.clock = clock;
    this.repository = repository;
  }

  public List<Task> list() {
    return repository.list();
  }

  public Task findById(Long id) {
    return repository.findById(id);
  }

  @Transactional
  public Task register(Task task) {
    Instant instant = clock.instant();
    task.setCreatedAt(instant);
    repository.register(task);
    return task;
  }

  @Transactional
  public void finish(Long id) {
    repository.finish(id);
  }

  @Transactional
  public void delete(Long id) {
    repository.delete(id);
  }
}
