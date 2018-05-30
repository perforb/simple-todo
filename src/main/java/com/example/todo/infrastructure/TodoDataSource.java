package com.example.todo.infrastructure;

import com.example.todo.domain.Todo;
import com.example.todo.domain.TodoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TodoDataSource implements TodoRepository {

  private final TodoMapper mapper;

  public TodoDataSource(TodoMapper mapper) {
    this.mapper = mapper;
  }

  public Optional<Todo> findById(int id) {
    return Optional.ofNullable(mapper.findById(id));
  }

  public List<Todo> list() {
    return mapper.findAll();
  }

  public void register(Todo todo) {
    mapper.insert(todo);
  }

  public void finish(int id) {
    mapper.finish(id);
  }

  public void delete(int id) {
    mapper.deleteById(id);
  }
}
