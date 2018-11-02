package com.example.todo;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "com.example.todo")
public class TodoApplication {

  public static void main(String[] args) {
    SpringApplicationBuilder builder = new SpringApplicationBuilder(TodoApplication.class);
    builder
      .bannerMode(Mode.CONSOLE)
      .web(WebApplicationType.SERVLET)
      .run(args);
  }
}
