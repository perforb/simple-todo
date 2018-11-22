package com.example.todo.presentation.controller.task;

import com.example.todo.application.task.TaskService;
import com.example.todo.domain.task.Task;
import com.example.todo.domain.user.UserRepository;
import com.example.todo.library.datetime.DateTimeProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TaskController.class)
@MockBean(classes = {DateTimeProvider.class, UserRepository.class})
class TaskControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private TaskService service;

  @WithMockUser(roles = "USER")
  @Test
  void find() throws Throwable {
    Task task = new Task(
      1L,
      "setup",
      false,
      ZonedDateTime.of(
        LocalDate.of(2018, 5, 30),
        LocalTime.MIDNIGHT,
        ZoneId.of("Asia/Tokyo")
      ).toInstant()
    );

    when(service.findById(anyLong())).thenReturn(task);

    RequestBuilder builder = MockMvcRequestBuilders.get("/tasks/{id}", 1L)
      .accept(MediaType.APPLICATION_JSON_UTF8);

    MvcResult result = mockMvc.perform(builder)
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").value(task.getId()))
      .andExpect(jsonPath("$.title").value(task.getTitle()))
      .andExpect(jsonPath("$.done").value(task.isDone()))
      .andExpect(jsonPath("$.createdAt").value(task.getCreatedAt().getEpochSecond()))
      .andDo(print())
      .andReturn();

    String expectedResponse = mapper.writeValueAsString(task);
    String actualResponse = result.getResponse().getContentAsString();

    Assert.assertEquals(
      "should equal",
      expectedResponse,
      actualResponse
    );
  }
}
