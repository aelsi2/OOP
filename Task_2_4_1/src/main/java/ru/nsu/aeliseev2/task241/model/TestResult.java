package ru.nsu.aeliseev2.task241.model;

/**
 * Represent the result of the unit test step.
 *
 * @param total   The total number of tests detected.
 * @param passed  The number of tests passed.
 * @param skipped The number of tests skipped.
 */
public record TestResult(int total, int passed, int skipped) {
    /**
     * Checks if all tests passed.
     *
     * @return {@code true} if there was at least one test, and all tests passed, {@code false}
     *     otherwise.
     */
    public boolean allPassed() {
        return total > 0 && total == passed;
    }
}
