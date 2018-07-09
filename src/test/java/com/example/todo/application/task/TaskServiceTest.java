package com.example.todo.application.task;

import com.example.todo.domain.NotFoundException;
import com.example.todo.domain.task.Task;
import com.example.todo.domain.task.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TaskServiceTest {

  @Mock
  private TaskRepository taskRepository;

  @InjectMocks
  private TaskService underTest;

  @Test
  void list() {
  }

  @Test
  void findById() {
    ZonedDateTime zonedDateTime = ZonedDateTime.of(
      LocalDate.of(2018, 6, 27),
      LocalTime.NOON,
      ZoneId.of("Asia/Tokyo")
    );

    LocalDateTime createdAt = zonedDateTime.toLocalDateTime();

    when(taskRepository.findById(anyInt())).thenReturn(
      new Task(
        1,
        "task1",
        false,
        createdAt
      )
    );

    Task task = taskRepository.findById(1);
    assertAll("equal properties",
      () -> assertEquals(Integer.valueOf(1), task.getId()),
      () -> assertEquals("task1", task.getTitle()),
      () -> assertFalse(task.isDone()),
      () -> assertEquals(createdAt, task.getCreatedAt())
    );
  }

  @Test
  void findByIdIfNotFound() {
    when(taskRepository.findById(anyInt())).thenThrow(
      new NotFoundException(String.format(
        "A task with the given ID was not found. [taskId: %s]",
        99
      ))
    );

    assertThrows(
      NotFoundException.class,
      () -> underTest.findById(99)
    );
  }

  @Test
  void register() {
  }

  @Test
  void finish() {
  }

  @Test
  void delete() {
  }
}