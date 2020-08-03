package com.example.todo.domain.task;

import java.util.List;

public interface TaskRepository {

  Task findById(Long id);

  List<Task> list();

  void register(Task task);

  void finish(Long id);

  void delete(Long id);
}
