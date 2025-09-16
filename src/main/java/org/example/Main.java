package org.example;

import java.time.Instant;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Instant now = Instant.now();

        long id1 = IDGenerator.generate(now);
        long id2 = IDGenerator.generate(now);
        System.out.println("Generated ID: " + id1);
        System.out.println("Generated ID: " + id2);

        String localTime1 = IDGenerator.extractTimestamp(id1);
        String localTime2 = IDGenerator.extractTimestamp(id2);
        System.out.println("Extracted timestamp: " + localTime1);
        System.out.println("Extracted timestamp: " + localTime2);

        IDGenerator.TimeRange timeRange1 = IDGenerator.getTimeRange(id1, 60);
        IDGenerator.TimeRange timeRange2 = IDGenerator.getTimeRange(id2, 60);
    }
}



