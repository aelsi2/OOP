package ru.nsu.aeliseev2.task112.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player's hand.
 */
public class CardHand {
    private static final int WIN_VALUE = 21;
    private static final String HIDDEN_CARD_FORMAT = "<закрытая карта>";

    private final List<Card> cards;

    /**
     * Creates a new card hand.
     *
     * @param pool The pool to take cards from.
     * @param count The number of cards to take from the pool.
     */
    public CardHand(CardPool pool, int count) {
        cards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            add(pool.take());
        }
    }

    /**
     * Adds a card to the hand.
     *
     * @param card The card to add.
     */
    public void add(Card card) {
        cards.add(card);
    }

    /**
     * Gets the total value of the hand considering the alternative "bust" card values.
     *
     * @return The value of the hand.
     */
    public int getValue() {
        int value = 0;
        for (var card : cards) {
            value += card.getValue();
        }
        if (value <= WIN_VALUE) {
            return value;
        }
        value = 0;
        for (var card : cards) {
            value += card.getBustValue();
        }
        return value;
    }

    private int getCardValue(Card card) {
        int value = 0;
        for (var existingCard : cards) {
            value += existingCard.getValue();
        }
        if (value > WIN_VALUE) {
            return card.getBustValue();
        }
        return card.getValue();
    }

    /**
     * Checks if the player can hit with the current hand.
     *
     * @return Can the player hit with this hand.
     */
    public boolean canHit() {
        return !isWin() && !isBust();
    }

    /**
     * Checks if the hand has the winning value.
     *
     * @return Is the hand a win.
     */
    public boolean isWin() {
        return getValue() == WIN_VALUE;
    }

    /**
     * Checks if the hand's value exceeds the winning value.
     *
     * @return Is the hand a bust.
     */
    public boolean isBust() {
        return getValue() > WIN_VALUE;
    }

    /**
     * Gets the string representation of a card with its effective value.
     *
     * @param card The card.
     * @return The string representation of the card.
     */
    public String cardToString(Card card) {
        return String.format("%s (%d)", card, getCardValue(card));
    }

    /**
     * Gets the string representation of the previously hidden card.
     *
     * @return The string representation of the hidden card.
     */
    public String hiddenCardToString() {
        int index = cards.size() - 1;
        var card = cards.get(index);
        return cardToString(card);
    }

    /**
     * Converts the hand to a string representation for display.
     *
     * @param hideLast Whether to hide the last card.
     * @return The string representation of the hand.
     */
    public String toString(boolean hideLast) {
        if (cards.isEmpty()) {
            return "[] => 0";
        }
        var builder = new StringBuilder();
        builder.append("[");
        builder.append(cardToString(cards.get(0)));
        for (var card : cards.subList(1, cards.size() - (hideLast ? 1 : 0))) {
            builder.append(", ");
            builder.append(cardToString(card));
        }
        if (hideLast) {
            builder.append(", ");
            builder.append(HIDDEN_CARD_FORMAT);
        }
        builder.append("]");
        if (!hideLast){
            builder.append(" => ");
            builder.append(getValue());
        }
        return builder.toString();
    }

    /**
     * Converts the hand to a string representation for display.
     *
     * @return The string representation of the hand.
     */
    @Override
    public String toString() {
        return toString(false);
    }
}
