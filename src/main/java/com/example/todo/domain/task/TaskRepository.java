package com.example.todo.domain.task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

  Optional<Task> findById(Integer id);

  List<Task> list();

  void register(Task task);

  void finish(Integer id);

  void delete(Integer id);
}
