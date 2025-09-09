package ru.nsu.aeliseev2.task112.model;

/**
 * A factory for instances of {@code CardPool}.
 */
public interface CardPoolFactory {
    /**
     * Creates a new card pool.
     *
     * @return The created card pool.
     */
    CardPool createPool();
}
