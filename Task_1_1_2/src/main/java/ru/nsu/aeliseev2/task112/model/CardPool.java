package ru.nsu.aeliseev2.task112.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a pool to take cards from in a blackjack game.
 */
public class CardPool {
    private final List<Card> cards;

    /**
     * Creates a new card pool.
     *
     * @param deckCount The number of standard 52-card decks to put into the pool.
     */
    public CardPool(int deckCount) {
        if (deckCount <= 0) {
            throw new IllegalArgumentException("deckCount must be greater than zero.");
        }
        cards = new ArrayList<>();
        for (int i = 0; i < deckCount; i++) {
            for (var type : CardRank.values()) {
                for (var suit : CardSuit.values()) {
                    cards.add(new Card(type, suit));
                }
            }
        }
        shuffle(cards);
    }

    /**
     * Creates a new card pool from a predefined collection of cards.
     * The first card in the collection is the first one to be taken.
     *
     * @param cards The array of cards.
     */
    public CardPool(Iterable<Card> cards) {
        this.cards = new ArrayList<>();
        for (var card : cards) {
            this.cards.add(0, card);
        }
    }

    /**
     * Takes a card removing it from the pool.
     *
     * @return The taken card.
     */
    public Card take() {
        if (cards.isEmpty()) {
            // This never happen.
            throw new IllegalStateException("No cards left.");
        }
        int index = cards.size() - 1;
        return cards.remove(index);
    }

    private static void shuffle(List<Card> cards) {
        var random = new Random();
        for (int index1 = 0; index1 < cards.size() - 1; index1++) {
            int index2 = random.nextInt(index1, cards.size());
            var card1 = cards.get(index1);
            var card2 = cards.get(index2);
            cards.set(index1, card2);
            cards.set(index2, card1);
        }
    }
}
