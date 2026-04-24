package ru.nsu.aeliseev2.task241.dsl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import ru.nsu.aeliseev2.task241.model.DefaultGradeStrategy;
import ru.nsu.aeliseev2.task241.model.Grade;

/**
 * Configuration DSL {@code grades} context.
 */
public class GradesContext {
    /**
     * Configuration DSL {@code grades.date} context.
     */
    public class DateContext {
        private final Instant date;

        /**
         * Initializes a new instance of {@code DateContext}.
         *
         * @param date The date of evaluation.
         */
        public DateContext(Instant date) {
            this.date = date;
        }

        /**
         * Specifies the name of the grade and adds it to the list.
         *
         * @param name The name of the grade.
         */
        public void name(String name) {
            grades.add(new Grade(name, new DefaultGradeStrategy(date)));
        }

    }

    private final List<Grade> grades;

    /**
     * Initializes a new instance of {@code GradesContext}.
     *
     * @param grades The list to add grades to.
     */
    public GradesContext(List<Grade> grades) {
        this.grades = grades;
    }

    /**
     * Begins constructing a new grade.
     *
     * @param date The date of evaluation.
     * @return The {@code grades.name} context.
     */
    public DateContext date(String date) {
        Instant maxDate;
        try {
            maxDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Grade date must be a string in the yyyy-MM-dd format");
        }
        return new DateContext(maxDate);
    }
}
