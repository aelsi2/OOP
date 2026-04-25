package ru.nsu.aeliseev2.task241.model;

import java.time.Instant;

/**
 * The status of a task for a student.
 */
public class TaskStatus {
    /**
     * Whether {@code ./gradlew build} completed successfully.
     */
    private boolean buildPassed = false;

    /**
     * Whether {@code ./gradlew build} completed successfully.
     * Gets the value.
     *
     * @return The value.
     */
    public boolean getBuildPassed() {
        return buildPassed;
    }

    /**
     * Whether {@code ./gradlew build} completed successfully.
     * Sets the value.
     *
     * @param value The value.
     */
    public void setBuildPassed(boolean value) {
        buildPassed = value;
    }

    /**
     * Whether {@code ./gradlew javadoc} completed successfully.
     */
    private boolean docPassed = false;

    /**
     * Whether {@code ./gradlew javadoc} completed successfully.
     * Gets the value.
     *
     * @return The value.
     */
    public boolean getDocPassed() {
        return docPassed;
    }

    /**
     * Whether {@code ./gradlew javadoc} completed successfully.
     * Sets the value.
     *
     * @param value The value.
     */
    public void setDocPassed(boolean value) {
        docPassed = value;
    }

    /**
     * Whether checkstyle completed successfully.
     */
    private boolean stylePassed = false;

    /**
     * Whether checkstyle completed successfully.
     * Gets the value.
     *
     * @return The value.
     */
    public boolean getStylePassed() {
        return stylePassed;
    }

    /**
     * Whether checkstyle completed successfully.
     * Sets the value.
     *
     * @param value The value.
     */
    public void setStylePassed(boolean value) {
        stylePassed = value;
    }

    /**
     * The result of {@code ./gradlew test}.
     */
    private TestResult tests = new TestResult(0, 0, 0);

    /**
     * The result of {@code ./gradlew test}.
     * Gets the value.
     *
     * @return The value.
     */
    public TestResult getTests() {
        return tests;
    }

    /**
     * The result of {@code ./gradlew test}.
     * Sets the value.
     *
     * @param value The value.
     */
    public void setTests(TestResult value) {
        tests = value;
    }

    /**
     * The soft accept date for this task.
     */
    private Instant softAccepted = null;

    /**
     * The soft accept date for this task.
     * Gets the value.
     *
     * @return The value.
     */
    public Instant getSoftAccepted() {
        return softAccepted;
    }

    /**
     * The soft accept date for this task.
     * Sets the value.
     *
     * @param value The value.
     */
    public void setSoftAccepted(Instant value) {
        softAccepted = value;
    }

    /**
     * The hard accept date for this task.
     */
    private Instant hardAccepted = null;

    /**
     * The hard accept date for this task.
     * Gets the value.
     *
     * @return The value.
     */
    public Instant getHardAccepted() {
        return hardAccepted;
    }

    /**
     * The hard accept date for this task.
     * Sets the value.
     *
     * @param value The value.
     */
    public void setHardAccepted(Instant value) {
        hardAccepted = value;
    }

    /**
     * The extra points given for this task.
     */
    private double extraPoints = 0;

    /**
     * The extra points given for this task.
     * Gets the value.
     *
     * @return The value.
     */
    public double getExtraPoints() {
        return extraPoints;
    }

    /**
     * The extra points given for this task.
     * Sets the value.
     *
     * @param value The value.
     */
    public void setExtraPoints(double value) {
        extraPoints = value;
    }
}
