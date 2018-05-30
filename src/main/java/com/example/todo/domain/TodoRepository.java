package com.example.todo.domain;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

  Optional<Todo> findById(int id);

  List<Todo> list();

  void register(Todo todo);

  void finish(int id);

  void delete(int id);
}
