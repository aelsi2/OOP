package ru.nsu.aeliseev2.task241.model;

import java.time.Instant;

/**
 * The status of a task for a student.
 */
public class TaskStatus {
    /**
     * Whether {@code ./gradlew build} completed successfully.
     */
    public boolean buildPassed = false;

    /**
     * Gets the value of {@code buildPassed}.
     *
     * @return The value.
     */
    public boolean getBuildPassed() {
        return buildPassed;
    }

    /**
     * Sets the value of {@code buildPassed}.
     *
     * @param value The value.
     */
    public void setBuildPassed(boolean value) {
        buildPassed = value;
    }

    /**
     * Whether {@code ./gradlew javadoc} completed successfully.
     */
    public boolean docPassed = false;

    /**
     * Gets the value of {@code docPassed}.
     *
     * @return The value.
     */
    public boolean getDocPassed() {
        return docPassed;
    }

    /**
     * Sets the value of {@code docPassed}.
     *
     * @param value The value.
     */
    public void setDocPassed(boolean value) {
        docPassed = value;
    }

    /**
     * Whether checkstyle completed successfully.
     */
    public boolean stylePassed = false;

    /**
     * Gets the value of {@code stylePassed}.
     *
     * @return The value.
     */
    public boolean getStylePassed() {
        return stylePassed;
    }

    /**
     * Sets the value of {@code stylePassed}.
     *
     * @param value The value.
     */
    public void setStylePassed(boolean value) {
        stylePassed = value;
    }

    /**
     * The result of {@code ./gradlew test}.
     */
    public TestResult tests = new TestResult(0, 0, 0);

    /**
     * Gets the value of {@code tests}.
     *
     * @return The value.
     */
    public TestResult getTests() {
        return tests;
    }

    /**
     * Sets the value of {@code tests}.
     *
     * @param value The value.
     */
    public void setTests(TestResult value) {
        tests = value;
    }

    /**
     * The soft accept date for this task.
     */
    public Instant softAccepted = null;

    /**
     * Gets the value of {@code softAccepted}.
     *
     * @return The value.
     */
    public Instant getSoftAccepted() {
        return softAccepted;
    }

    /**
     * Sets the value of {@code softAccepted}.
     *
     * @param value The value.
     */
    public void setSoftAccepted(Instant value) {
        softAccepted = value;
    }

    /**
     * The hard accept date for this task.
     */
    public Instant hardAccepted = null;

    /**
     * Gets the value of {@code hardAccepted}.
     *
     * @return The value.
     */
    public Instant getHardAccepted() {
        return hardAccepted;
    }

    /**
     * Sets the value of {@code hardAccepted}.
     *
     * @param value The value.
     */
    public void setHardAccepted(Instant value) {
        hardAccepted = value;
    }

    /**
     * The extra points given for this task.
     */
    public double extraPoints = 0;

    /**
     * Gets the value of {@code extraPoints}.
     *
     * @return The value.
     */
    public double getExtraPoints() {
        return extraPoints;
    }

    /**
     * Sets the value of {@code extraPoints}.
     *
     * @param value The value.
     */
    public void setExtraPoints(double value) {
        extraPoints = value;
    }
}
