package com.example.todo.domain.task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

  Optional<Task> findById(int id);

  List<Task> list();

  void register(Task task);

  void finish(int id);

  void delete(int id);
}
