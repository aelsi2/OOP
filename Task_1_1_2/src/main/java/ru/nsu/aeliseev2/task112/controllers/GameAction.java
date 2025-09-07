package ru.nsu.aeliseev2.task112.controllers;

/**
 * Represents an action in the game.
 */
public enum GameAction {
    /**
     * End the turn.
     */
    STAND,

    /**
     * Take a card from the pool.
     */
    HIT;

    /**
     * Gets the action corresponding to an ordinal.
     *
     * @param ordinal The ordinal of the action.
     * @return The corresponding action.
     * @exception IllegalArgumentException The ordinal was invalid.
     */
    public static GameAction fromOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal >= GameAction.values().length) {
            throw new IllegalArgumentException("Invalid number.");
        }
        return GameAction.values()[ordinal];
    }
}
