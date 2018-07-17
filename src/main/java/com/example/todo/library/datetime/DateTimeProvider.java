package com.example.todo.library.datetime;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeProvider {

  private static final ZoneId JST = ZoneId.of("Asia/Tokyo");

  private static Clock clock = Clock.systemDefaultZone();

  public static Instant instant() {
    return clock.instant();
  }

  public static ZonedDateTime nowJST() {
    return now(JST);
  }

  public static ZonedDateTime now(ZoneId zoneId) {
    return ZonedDateTime.ofInstant(clock.instant(), zoneId);
  }

  protected static void setClock(Clock clock) {
    DateTimeProvider.clock = clock;
  }
}
