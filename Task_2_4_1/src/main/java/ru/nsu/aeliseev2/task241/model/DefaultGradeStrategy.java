package ru.nsu.aeliseev2.task241.model;

import java.time.Instant;
import java.util.List;

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

    private boolean extraTasksDone(Student student, TaskDatabase taskDatabase) {
        return taskDatabase.getTasks().stream()
            .filter(Task::isExtra)
            .filter(task -> task.hardDeadline() == null
                || task.hardDeadline().toEpochMilli() <= maxDate.toEpochMilli())
            .allMatch(task -> {
                TaskStatus status = taskDatabase.getStatus(task, student);
                return status.hardAccepted != null && (task.hardDeadline() == null
                    || status.hardAccepted.toEpochMilli() <= task.hardDeadline().toEpochMilli());
            });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Task> getTasks(TaskDatabase taskDatabase) {
        return taskDatabase.getTasks().stream()
            .filter(task -> task.hardDeadline() == null
                || task.hardDeadline().toEpochMilli() <= maxDate.toEpochMilli())
            .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTaskPoints(Task task, Student student, TaskDatabase taskDatabase) {
        double result = 0;
        TaskStatus status = taskDatabase.getStatus(task, student);
        result += status.extraPoints;
        if (status.softAccepted != null && task.softDeadline() != null
            && status.softAccepted.toEpochMilli() < task.softDeadline().toEpochMilli()) {
            result += 0.5;
        }
        if (status.hardAccepted != null && (task.hardDeadline() == null
            || status.hardAccepted.toEpochMilli() < task.hardDeadline().toEpochMilli())) {
            if (task.softDeadline() == null || status.softAccepted == null) {
                result += 1;
            } else {
                result += 0.5;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTotalPoints(Student student, TaskDatabase taskDatabase) {
        double totalPoints = 0;
        for (Task task : getTasks(taskDatabase)) {
            totalPoints += getTaskPoints(task, student, taskDatabase);
        }
        return totalPoints;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate(Student student, TaskDatabase taskDatabase) {
        double maxPoints = getTasks(taskDatabase).size();
        double actualPoints = getTotalPoints(student, taskDatabase);
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
