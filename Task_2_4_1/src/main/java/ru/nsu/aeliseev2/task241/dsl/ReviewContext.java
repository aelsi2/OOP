package ru.nsu.aeliseev2.task241.dsl;

import java.time.Instant;
import ru.nsu.aeliseev2.task241.model.TaskDatabase;
import ru.nsu.aeliseev2.task241.model.TaskStatus;

/**
 * Configuration DSL {@code review} context.
 */
public class ReviewContext {
    /**
     * Configuration DSL {@code review.accept} context.
     */
    public class AcceptContext {
        private final String taskName;
        private final boolean hard;
        private double extraValue;

        /**
         * Initializes a new instance of {@code AcceptContext}.
         *
         * @param taskName The name of the task being accepted.
         * @param hard     Whether this is a hard accept or a soft accept.
         */
        public AcceptContext(String taskName, boolean hard) {
            this.taskName = taskName;
            this.hard = hard;
            this.extraValue = 0;
        }

        /**
         * Adds extra points for the task.
         *
         * @param value The extra points to add.
         * @return The {@code AcceptContext} to do further configuration with.
         */
        public AcceptContext extra(double value) {
            extraValue += value;
            return this;
        }

        /**
         * Specifies the student that solved the task and adds the review to the database.
         *
         * @param username The GitHub username of the student.
         */
        public void forStudent(String username) {
            TaskStatus status = taskDatabase.getStatus(taskName, username);
            if (hard) {
                status.hardAccepted = date;
            } else {
                status.softAccepted = date;
            }
            status.extraPoints += extraValue;
        }
    }

    private final Instant date;
    private final TaskDatabase taskDatabase;

    /**
     * Initializes a new instance of {@code ReviewContext}.
     *
     * @param date         The date of the review.
     * @param taskDatabase The task database to write the reviews to.
     */
    public ReviewContext(Instant date, TaskDatabase taskDatabase) {
        this.date = date;
        this.taskDatabase = taskDatabase;
    }

    /**
     * Begins constructing a new task review.
     *
     * @param taskName The name of the task being reviewed.
     * @return The {@code review.accept} context.
     */
    public AcceptContext hardAccept(String taskName) {
        return new AcceptContext(taskName, false);
    }

    /**
     * Begins constructing a new task review.
     *
     * @param taskName The name of the task being reviewed.
     * @return The {@code review.accept} context.
     */
    public AcceptContext softAccept(String taskName) {
        return new AcceptContext(taskName, true);
    }
}
