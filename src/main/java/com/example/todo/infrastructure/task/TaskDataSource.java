package com.example.todo.infrastructure.task;

import com.example.todo.domain.NotFoundException;
import com.example.todo.domain.task.Task;
import com.example.todo.domain.task.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskDataSource implements TaskRepository {

  private final TaskMapper mapper;

  public TaskDataSource(TaskMapper mapper) {
    this.mapper = mapper;
  }

  public Task findById(Integer id) {
    return Optional.ofNullable(mapper.findById(id)).orElseThrow(() ->
      new NotFoundException(String.format(
        "The task with the given id [%s] was not found.",
        id
      ))
    );
  }

  public List<Task> list() {
    return mapper.findAll();
  }

  public void register(Task task) {
    mapper.insert(task);
  }

  public void finish(Integer id) {
    mapper.finish(id);
  }

  public void delete(Integer id) {
    mapper.deleteById(id);
  }
}
