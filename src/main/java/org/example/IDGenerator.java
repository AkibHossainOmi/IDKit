package org.example;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class IDGenerator {

    private static final DateTimeFormatter MYSQL_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId BD_ZONE = ZoneId.of("Asia/Dhaka");

    // Number of bits to use for counter within the same millisecond
    private static final int COUNTER_BITS = 16; // allows 65,536 IDs per millisecond
    private static final long MAX_COUNTER = (1L << COUNTER_BITS) - 1;

    // Keep track of last timestamp and counter
    private static long lastTimestamp = -1L;
    private static long counter = 0L;

    /**
     * Generate unique 64-bit ID
     */
    public static synchronized long generate(Instant instant) {
        long currentMillis = instant.toEpochMilli();

        if (currentMillis < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards!");
        }

        if (currentMillis == lastTimestamp) {
            counter++;
            if (counter > MAX_COUNTER) {
                // Wait for next millisecond if counter exceeds
                while (System.currentTimeMillis() <= lastTimestamp) { }
                currentMillis = System.currentTimeMillis();
                counter = 0;
                lastTimestamp = currentMillis;
            }
        } else {
            counter = 0;
            lastTimestamp = currentMillis;
        }

        // Encode: [milliseconds << COUNTER_BITS] | counter
        return (currentMillis << COUNTER_BITS) | counter;
    }

    /**
     * Extract timestamp till second from ID
     */
    public static String extractTimestamp(long id) {
        long millis = id >>> COUNTER_BITS; // remove counter bits
        Instant instant = Instant.ofEpochMilli(millis);
        return instant.atZone(BD_ZONE).format(MYSQL_FORMATTER);
    }

    public static class TimeRange {
        public String startTime;
        public String endTime;
    }

    /**
     * Get ±seconds time range around ID timestamp
     */
    public static TimeRange getTimeRange(long id, long seconds) {
        long millis = id >>> COUNTER_BITS;
        Instant instant = Instant.ofEpochMilli(millis);

        Instant start = instant.minus(Duration.ofSeconds(seconds));
        Instant end = instant.plus(Duration.ofSeconds(seconds));

        TimeRange range = new TimeRange();
        range.startTime = start.atZone(BD_ZONE).format(MYSQL_FORMATTER);
        range.endTime = end.atZone(BD_ZONE).format(MYSQL_FORMATTER);

        return range;
    }
}
