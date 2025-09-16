package org.example;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class TestIDGenerator {

    public static void main(String[] args) {
        Set<Long> ids = new HashSet<>();
        Instant now = Instant.now();
        int maxIds = 65536;

        for (int i = 0; i < maxIds; i++) {
            long id = IDGenerator.generate(now);
            if (!ids.add(id)) {
                System.out.println("Duplicate ID found at iteration: " + i);
            }
        }

        System.out.println("Generated IDs: " + ids.size());
        if (ids.size() == maxIds) {
            System.out.println("All IDs are unique within the same millisecond!");
        } else {
            System.out.println("Duplicates detected!");
        }
    }
}
