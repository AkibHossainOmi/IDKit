package org.example;

import java.time.Instant;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Instant now = Instant.now();

        long id = IDGenerator.generate(now);
        System.out.println("Generated ID: " + id);

        String localTime = IDGenerator.extractTimestamp(id);
        System.out.println("Extracted timestamp: " + localTime);

        IDGenerator.TimeRange timeRange = IDGenerator.getTimeRange(id, 60);
    }
}



