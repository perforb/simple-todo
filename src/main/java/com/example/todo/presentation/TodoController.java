package com.example.todo.presentation;

import com.example.todo.application.TodoService;
import com.example.todo.domain.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController {

  private final TodoService service;

  public TodoController(TodoService service) {
    this.service = service;
  }

  @GetMapping(value = "/todos")
  @ResponseStatus(HttpStatus.OK)
  public List<Todo> list() {
    return service.list();
  }

  @GetMapping(value = "/todos/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Todo find(@PathVariable("id") String identifier) {
    int id = Integer.parseInt(identifier);
    return service.findById(id);
  }

  @PostMapping(value = "/todos")
  @ResponseStatus(HttpStatus.CREATED)
  public Todo register(Todo todo) {
    return service.register(todo);
  }

  @PutMapping(value = "/todos/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void finish(@PathVariable("id") String identifier) {
    int id = Integer.parseInt(identifier);
    service.finish(id);
  }

  @DeleteMapping(value = "/todos/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") String identifier) {
    int id = Integer.parseInt(identifier);
    service.delete(id);
  }
}
