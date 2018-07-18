package com.example.todo.config;

import com.example.todo.library.datetime.DateTimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class TimeZoneConfig {

  private static final Logger log = LoggerFactory.getLogger(TimeZoneConfig.class);

  @Bean
  DateTimeProvider dateTimeProvider(Environment env) {
    String timeZone = env.getProperty("app.time-zone", "UTC");
    log.info("Initializing the time zone with {}", timeZone);
    ZoneId zoneId = ZoneId.of(timeZone);
    Clock clock = Clock.system(zoneId);
    return new DateTimeProvider(clock);
  }
}
