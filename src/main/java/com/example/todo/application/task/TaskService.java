package com.example.todo.application.task;

import com.example.todo.domain.NotFoundException;
import com.example.todo.domain.task.Task;
import com.example.todo.domain.task.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    return repository.findById(id).orElseThrow(() ->
      new NotFoundException(String.format(
        "The task with the given id [%s] was not found.",
        id
      ))
    );
  }

  @Transactional
  public Task register(Task task) {
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
    task.setCreatedAt(now.toLocalDateTime());
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
