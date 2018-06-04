package com.example.todo;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TaskApplication {

  public static void main(String[] args) {
    SpringApplicationBuilder builder = new SpringApplicationBuilder(TaskApplication.class);
    builder
      .bannerMode(Mode.CONSOLE)
      .web(WebApplicationType.SERVLET)
      .run(args);
  }
}
