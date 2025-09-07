package ru.nsu.aeliseev2.task112.model;

/**
 * Represents a playing card.
 */
public class Card {
    private final CardRank rank;
    private final CardSuit suit;

    /**
     * Creates a new playing card.
     *
     * @param rank The card's rank.
     * @param suit The card's suit.
     */
    public Card(CardRank rank, CardSuit suit){
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Gets the value of the card.
     *
     * @return The card's value in a game of blackjack.
     */
    public int getValue() {
        return rank.getValue();
    }

    /**
     * Gets the alternative value of the card, when the total is above 21.
     *
     * @return The alternative value.
     */
    public int getBustValue() {
        return rank.getBustValue();
    }

    /**
     * Converts the card to a string representation for display.
     *
     * @return The string representation of the card.
     */
    @Override
    public String toString() {
        return String.format("%s %s", rank, suit);
    }
}
