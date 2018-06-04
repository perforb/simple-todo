package com.example.todo.infrastructure;

import com.example.todo.domain.Task;
import com.example.todo.domain.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskDataSource implements TaskRepository {

  private final TaskMapper mapper;

  public TaskDataSource(TaskMapper mapper) {
    this.mapper = mapper;
  }

  public Optional<Task> findById(int id) {
    return Optional.ofNullable(mapper.findById(id));
  }

  public List<Task> list() {
    return mapper.findAll();
  }

  public void register(Task task) {
    mapper.insert(task);
  }

  public void finish(int id) {
    mapper.finish(id);
  }

  public void delete(int id) {
    mapper.deleteById(id);
  }
}
