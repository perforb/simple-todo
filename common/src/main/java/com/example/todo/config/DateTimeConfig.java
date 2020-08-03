package com.example.todo.config;

import com.example.todo.lib.time.ApplicationClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class DateTimeConfig {

  private static final Logger log = LoggerFactory.getLogger(DateTimeConfig.class);

  @Bean
  ApplicationClock applicationClock(Environment env) {
    String timeZone = env.getProperty("app.time-zone", "UTC");
    log.info("Initializing application time zone with {}", timeZone);
    ZoneId zoneId = ZoneId.of(timeZone);
    Clock clock = Clock.system(zoneId);
    return new ApplicationClock(clock);
  }
}
