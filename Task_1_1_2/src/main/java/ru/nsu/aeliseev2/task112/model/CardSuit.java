package ru.nsu.aeliseev2.task112.model;

/**
 * Represents a card suit.
 */
public enum CardSuit {
    /**
     * Clubs card suit.
     */
    CLUBS("Трефы"),

    /**
     * Hearts card suit.
     */
    HEARTS("Черви"),

    /**
     * Spades card suit.
     */
    SPADES("Пики"),

    /**
     * Diamonds card suit.
     */
    DIAMONDS("Бубны");

    private final String name;

    CardSuit(String name) {
        this.name = name;
    }

    /**
     * Gets the string representation for display.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return name;
    }
}
