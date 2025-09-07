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
     * Takes a card removing it from the pool.
     *
     * @return The taken card.
     */
    public Card take() {
        if (cards.isEmpty()) {
            // This shouldn't ever happen.
            throw new IllegalStateException("No cards left.");
        }
        int index = cards.size() - 1;
        var card = cards.get(index);
        cards.remove(index);
        return card;
    }

    private static void shuffle(List<Card> cards) {
        var random = new Random();
        for (int index1 = 0; index1 < cards.size() - 1; index1++){
            int index2 = random.nextInt(index1, cards.size());
            var card1 = cards.get(index1);
            var card2 = cards.get(index2);
            cards.set(index1, card2);
            cards.set(index2, card1);
        }
    }
}
