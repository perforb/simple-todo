package com.example.todo.presentation.controller.task;

import com.example.todo.application.task.TaskService;
import com.example.todo.domain.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

  private final TaskService service;

  public TaskController(TaskService service) {
    this.service = service;
  }

  @GetMapping(value = "/tasks")
  @ResponseStatus(HttpStatus.OK)
  public List<Task> list() {
    return service.list();
  }

  @GetMapping(value = "/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Task find(@PathVariable("id") String identifier) {
    Long id = Long.valueOf(identifier);
    return service.findById(id);
  }

  @PostMapping(value = "/tasks")
  @ResponseStatus(HttpStatus.CREATED)
  public Task register(@RequestBody @Validated Task todo) {
    return service.register(todo);
  }

  @PutMapping(value = "/tasks/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void finish(@PathVariable("id") String identifier) {
    Long id = Long.valueOf(identifier);
    service.finish(id);
  }

  @DeleteMapping(value = "/tasks/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") String identifier) {
    Long id = Long.valueOf(identifier);
    service.delete(id);
  }
}
