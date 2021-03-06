package com.example.todo.lib.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ApplicationClock {

  private final Clock clock;

  public ApplicationClock(Clock clock) {
    this.clock = clock;
  }

  public Instant instant() {
    return clock.instant();
  }

  public ZonedDateTime now() {
    return ZonedDateTime.now(clock);
  }

  public ZonedDateTime now(ZoneId zoneId) {
    return ZonedDateTime.ofInstant(clock.instant(), zoneId);
  }
}
