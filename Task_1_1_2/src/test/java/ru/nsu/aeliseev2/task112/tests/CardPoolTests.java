package ru.nsu.aeliseev2.task112.tests;

import java.util.HashMap;
import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task112.model.Card;
import ru.nsu.aeliseev2.task112.model.CardPool;
import ru.nsu.aeliseev2.task112.model.CardRank;
import ru.nsu.aeliseev2.task112.model.CardSuit;

class CardPoolTests {
    @Test
    void overtake() {
        var pool = new CardPool(1);
        for (int i = 0; i < 52; i++) {
            pool.take();
        }
        Assertions.assertThrows(IllegalStateException.class, pool::take);
    }

    @Test
    void negativeDeckCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CardPool(-1);
        });
    }

    @Test
    void zeroDeckCount() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CardPool(0);
        });
    }

    @Test
    void take1Deck() {
        var expected = new HashSet<Card>();
        for (var type : CardRank.values()) {
            for (var suit : CardSuit.values()) {
                expected.add(new Card(type, suit));
            }
        }

        var pool = new CardPool(1);

        var actual = new HashSet<Card>();
        for (int i = 0; i < 52; i++) {
            actual.add(pool.take());
        }
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void take2Decks() {
        int deckCount = 2;

        var expected = new HashMap<Card, Integer>();
        var actual = new HashMap<Card, Integer>();

        for (var type : CardRank.values()) {
            for (var suit : CardSuit.values()) {
                var card = new Card(type, suit);
                expected.put(card, deckCount);
                actual.put(card, 0);
            }
        }

        var pool = new CardPool(deckCount);

        for (int i = 0; i < 52 * deckCount; i++) {
            var card = pool.take();
            actual.put(card, actual.get(card) + 1);
        }
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void take3Decks() {
        int deckCount = 3;

        var expected = new HashMap<Card, Integer>();
        var actual = new HashMap<Card, Integer>();

        for (var type : CardRank.values()) {
            for (var suit : CardSuit.values()) {
                var card = new Card(type, suit);
                expected.put(card, deckCount);
                actual.put(card, 0);
            }
        }

        var pool = new CardPool(deckCount);

        for (int i = 0; i < 52 * deckCount; i++) {
            var card = pool.take();
            actual.put(card, actual.get(card) + 1);
        }
        Assertions.assertEquals(expected, actual);
    }
}
