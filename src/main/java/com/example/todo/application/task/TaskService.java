package com.example.todo.application.task;

import com.example.todo.domain.task.Task;
import com.example.todo.domain.task.TaskRepository;
import com.example.todo.library.datetime.DateTimeProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class TaskService {

  private final TaskRepository repository;

  public TaskService(TaskRepository repository) {
    this.repository = repository;
  }

  public List<Task> list() {
    return repository.list();
  }

  public Task findById(Integer id) {
    return repository.findById(id);
  }

  @Transactional
  public Task register(Task task) {
    Instant instant = DateTimeProvider.instant();
    task.setCreatedAt(instant);
    repository.register(task);
    return task;
  }

  @Transactional
  public void finish(Integer id) {
    repository.finish(id);
  }

  @Transactional
  public void delete(Integer id) {
    repository.delete(id);
  }
}
