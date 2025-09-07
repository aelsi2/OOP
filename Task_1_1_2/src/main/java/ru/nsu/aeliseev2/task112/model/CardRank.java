package ru.nsu.aeliseev2.task112.model;

/**
 * Represents a card rank.
 */
public enum CardRank {
    /**
     * 2 card.
     */
    NUM_2("Двойка", 2, 2),

    /**
     * 3 card.
     */
    NUM_3("Тройка", 3, 3),

    /**
     * 4 card.
     */
    NUM_4("Четверка", 4, 4),

    /**
     * 5 card.
     */
    NUM_5("Пятерка", 5, 5),

    /**
     * 6 card.
     */
    NUM_6("Шестерка", 6, 6),

    /**
     * 7 card.
     */
    NUM_7("Семерка", 7, 7),

    /**
     * 8 card.
     */
    NUM_8("Восьмерка", 8, 8),

    /**
     * 9 card.
     */
    NUM_9("Девятка", 9, 9),

    /**
     * 10 card.
     */
    NUM_10("Десятка", 10, 10),

    /**
     * Jack card.
     */
    JACK("Валет", 10, 10),

    /**
     * Queen card.
     */
    QUEEN("Дама", 10, 10),

    /**
     * King card.
     */
    KING("Король", 10, 10),

    /**
     * Ace card.
     */
    ACE("Туз", 11, 1);

    private final String name;
    private final int value;
    private final int bustValue;

    CardRank(String name, int value, int bustValue) {
        this.name = name;
        this.value = value;
        this.bustValue = bustValue;
    }

    /**
     * Gets the usual value of cards of this rank, when the total is below 21.
     *
     * @return The usual value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the alternative value of cards of this rank, when the total is above 21.
     *
     * @return The alternative value.
     */
    public int getBustValue() {
        return bustValue;
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
