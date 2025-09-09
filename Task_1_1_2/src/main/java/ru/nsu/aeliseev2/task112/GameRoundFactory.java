package ru.nsu.aeliseev2.task112;

/**
 * Factory for {@code GameRound} instances.
 */
public interface GameRoundFactory {
    /**
     * Creates a new game round.
     *
     * @return The created game round.
     */
    GameRound create();
}
