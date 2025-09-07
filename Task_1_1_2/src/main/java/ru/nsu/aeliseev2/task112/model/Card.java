package ru.nsu.aeliseev2.task112.model;

/**
 * Represents a playing card.
 */
public class Card {
    private final CardRank m_rank;
    private final CardSuit m_suit;

    /**
     * Creates a new playing card.
     *
     * @param rank The card's rank.
     * @param suit The card's suit.
     */
    public Card(CardRank rank, CardSuit suit){
        m_rank = rank;
        m_suit = suit;
    }

    /**
     * Gets the value of the card.
     *
     * @return The card's value in a game of blackjack.
     */
    public int getValue() {
        return m_rank.getValue();
    }

    /**
     * Gets the alternative value of the card, when the total is above 21.
     *
     * @return The alternative value.
     */
    public int getBustValue() {
        return m_rank.getBustValue();
    }

    /**
     * Converts the card to a string representation for display.
     *
     * @return The string representation of the card.
     */
    @Override
    public String toString() {
        return String.format("%s %s", m_rank, m_suit);
    }
}
