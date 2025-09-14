package org.example;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class IDGenerator {

    private static final DateTimeFormatter MYSQL_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId BD_ZONE = ZoneId.of("Asia/Dhaka");

    /**
     * Generate 64-bit ID from Instant by encoding milliseconds since epoch.
     */
    public static long generate(Instant instant) {
        return instant.toEpochMilli();
    }

    /**
     * Extract timestamp (Instant) from 64-bit ID.
     */
    public static String  extractTimestamp(long id) {
        Instant instant = Instant.ofEpochMilli(id);
        return instant.atZone(BD_ZONE).format(MYSQL_FORMATTER);
    }

    public static class TimeRange {
        public String startTime;
        public String endTime;
    }

    public static TimeRange getTimeRange(long id, long seconds) {
        Instant instant = Instant.ofEpochMilli(id);
        Instant start = instant.minus(Duration.ofSeconds(seconds));
        Instant end = instant.plus(Duration.ofSeconds(seconds));

        TimeRange range = new TimeRange();
        range.startTime = start.atZone(BD_ZONE).format(MYSQL_FORMATTER);
        range.endTime = end.atZone(BD_ZONE).format(MYSQL_FORMATTER);

        return range;
    }
}
