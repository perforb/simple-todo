package com.example.todo.domain.task;

import java.util.List;

public interface TaskRepository {

  Task findById(Integer id);

  List<Task> list();

  void register(Task task);

  void finish(Integer id);

  void delete(Integer id);
}
