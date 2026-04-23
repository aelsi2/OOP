package ru.nsu.aeliseev2.task241.model;

import java.util.Date;

/**
 * The status of a task for a student.
 */
public class TaskStatus {
    /**
     * Whether {@code ./gradlew build} completed successfully.
     */
    public boolean buildPassed = false;

    /**
     * Whether {@code ./gradlew javadoc} completed successfully.
     */
    public boolean docPassed = false;

    /**
     * Whether checkstyle completed successfully.
     */
    public boolean stylePassed = false;

    /**
     * The result of {@code ./gradlew test}.
     */
    public TestResult tests = new TestResult(0, 0, 0);

    /**
     * The soft accept date for this task.
     */
    public Date softAccepted = null;

    /**
     * The hard accept date for this task.
     */
    public Date hardAccepted = null;

    /**
     * The extra points given for this task.
     */
    public double extraPoints = 0;
}
