package ru.nsu.aeliseev2.task141;

/**
 * Represents the value of a {@code Grade}.
 *
 * @see Grade
 */
public enum GradeValue implements Comparable<GradeValue> {
    /**
     * Fail.
     */
    FAIL(2),
    /**
     * Satisfactory grade.
     */
    SATISFACTORY(3),
    /**
     * Good grade.
     */
    GOOD(4),
    /**
     * Excellent grade.
     */
    EXCELLENT(5);

    private final int numericalValue;

    GradeValue(int numericalValue) {
        this.numericalValue = numericalValue;
    }

    /**
     * Converts this grade to its numeric representation.
     *
     * @return The grade's numeric representation.
     */
    public int toInt() {
        return numericalValue;
    }
}
