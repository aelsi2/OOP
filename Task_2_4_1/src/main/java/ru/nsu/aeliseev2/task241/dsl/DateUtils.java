package ru.nsu.aeliseev2.task241.dsl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

class DateUtils {
    public static Instant parseDate(String string) {
        if (string == null) {
            return null;
        }
        try {
            return LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE)
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Date must be a string in the yyyy-MM-dd format");
        }
    }
}
