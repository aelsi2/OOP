package ru.nsu.aeliseev2.task212.utils;

/**
 * The state of a work unit.
 */
public enum WorkStatus {
    /**
     * The work unit hasn't completed.
     */
    WORKING,

    /**
     * The work unit has completed, and there were composite numbers found.
     */
    HAS_COMPOSITES,

    /**
     * The work unit has completed, and there were no composite numbers found.
     */
    ALL_PRIMES
}
