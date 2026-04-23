package ru.nsu.aeliseev2.task241.model;

import java.time.Instant;

/**
 * An implementation of {@code GradeStrategy} that compares the current number points with the
 * number of points achievable if all deadlines before a fixed date are met.
 */
public class DefaultGradeStrategy implements GradeStrategy {
    private final Instant maxDate;

    /**
     * Initializes a new instance of {@code DefaultGradeStrategy}.
     *
     * @param maxDate The max hard deadline date to consider.
     */
    public DefaultGradeStrategy(Instant maxDate) {
        this.maxDate = maxDate;
    }

    private double getMaxPoints(TaskDatabase taskDatabase) {
        double maxPoints;
        maxPoints = taskDatabase.getTasks().stream()
            .filter(task -> task.hardDeadline().toEpochMilli() <= maxDate.toEpochMilli())
            .count();
        return maxPoints;
    }

    private double getActualPoints(Student student, TaskDatabase taskDatabase) {
        double actualPoints = 0;
        for (Task task : taskDatabase.getTasks()) {
            TaskStatus status = taskDatabase.getStatus(task, student);
            actualPoints += status.extraPoints;
            if (status.softAccepted != null && task.softDeadline() != null
                && status.softAccepted.toEpochMilli() < task.softDeadline().toEpochMilli()) {
                actualPoints += 0.5;
            }
            if (status.hardAccepted != null && (task.hardDeadline() == null
                || status.hardAccepted.toEpochMilli() < task.hardDeadline().toEpochMilli())) {
                if (task.softDeadline() == null || status.softAccepted == null) {
                    actualPoints += 1;
                } else {
                    actualPoints += 0.5;
                }
            }
        }
        return actualPoints;
    }

    private boolean extraTasksDone(Student student, TaskDatabase taskDatabase) {
        return taskDatabase.getTasks().stream()
            .filter(Task::isExtra)
            .filter(task -> task.hardDeadline().toEpochMilli() <= maxDate.toEpochMilli())
            .allMatch(task -> {
                TaskStatus status = taskDatabase.getStatus(task, student);
                return status.hardAccepted != null
                    && status.hardAccepted.toEpochMilli() <= task.hardDeadline().toEpochMilli();
            });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate(Student student, TaskDatabase taskDatabase) {
        double maxPoints = getMaxPoints(taskDatabase);
        double actualPoints = getActualPoints(student, taskDatabase);
        if (actualPoints >= maxPoints && extraTasksDone(student, taskDatabase)) {
            return 5;
        }
        if (actualPoints / maxPoints >= 0.8) {
            return 4;
        }
        if (actualPoints / maxPoints >= 0.6) {
            return 3;
        }
        if (Instant.now().toEpochMilli() < maxDate.toEpochMilli()) {
            return Double.NaN;
        }
        return 2;
    }
}
