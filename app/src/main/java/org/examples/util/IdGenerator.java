package org.examples.util;

import java.util.List;

public class IdGenerator {

    public static String nextId(List<String> existingIds, String prefix) {

        int max = 0;

        for (String id : existingIds) {

            if (id != null && id.startsWith(prefix)) {

                int number = Integer.parseInt(id.substring(prefix.length()));

                if (number > max) {
                    max = number;
                }
            }
        }

        return String.format("%s%04d", prefix, max + 1);
    }
}