package com.example.todo.lib.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

class DateTimeProviderTest {

  private Clock fixedClock;

  private DateTimeProvider underTest;

  @BeforeEach
  void init() {
    ZoneId tokyo = ZoneId.of("Asia/Tokyo");
    ZonedDateTime fixedDateTime = ZonedDateTime.of(
      LocalDate.of(2018, 6, 27),
      LocalTime.NOON,
      tokyo
    );

    Instant fixedInstant = fixedDateTime.toInstant();
    Clock fixedClock = Clock.fixed(fixedInstant, tokyo);

    this.fixedClock = fixedClock;
    this.underTest = new DateTimeProvider(fixedClock);
  }

  @Test
  void instant() {
    assertEquals(fixedClock.instant(), underTest.instant());
  }

  @Test
  void now() {
    ZoneId zoneId = ZoneId.of("Asia/Tokyo");
    ZonedDateTime fixedDateTime = ZonedDateTime.of(
      LocalDate.of(2018, 6, 27),
      LocalTime.NOON,
      zoneId
    );

    assertEquals(fixedDateTime, underTest.now());
  }

  @Test
  void nowWithZoneId() {
    ZoneId sydney = ZoneId.of("Australia/Sydney");
    Instant instant = fixedClock.instant();
    ZonedDateTime sydneyTime = ZonedDateTime.ofInstant(instant, sydney);

    assertEquals(sydneyTime, underTest.now(sydney));
  }
}
