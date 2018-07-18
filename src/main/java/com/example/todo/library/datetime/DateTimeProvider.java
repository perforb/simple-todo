package com.example.todo.library.datetime;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class DateTimeProvider {

  private final Clock clock;

  public DateTimeProvider(Clock clock) {
    Objects.requireNonNull(clock);
    this.clock = clock;
  }

  public Instant instant() {
    return clock.instant();
  }

  public ZonedDateTime now() {
    return ZonedDateTime.now(clock.getZone());
  }

  public ZonedDateTime now(ZoneId zoneId) {
    return ZonedDateTime.ofInstant(clock.instant(), zoneId);
  }
}
