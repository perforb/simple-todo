package com.example.todo.application;

import com.example.todo.domain.NotFoundException;
import com.example.todo.domain.Todo;
import com.example.todo.domain.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TodoService {

  private final TodoRepository repository;

  public TodoService(TodoRepository repository) {
    this.repository = repository;
  }

  public List<Todo> list() {
    return repository.list();
  }

  public Todo findById(int id) {
    return repository.findById(id).orElseThrow(() ->
      new NotFoundException(String.format(
        "The record with the given id [%s] is not found.",
        id
      ))
    );
  }

  @Transactional
  public Todo register(Todo todo) {
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
    todo.setCreatedAt(now.toLocalDateTime());
    repository.register(todo);
    return todo;
  }

  @Transactional
  public void finish(int id) {
    repository.finish(id);
  }

  @Transactional
  public void delete(int id) {
    repository.delete(id);
  }
}
