package ru.nsu.aeliseev2.task112.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task112.model.Card;
import ru.nsu.aeliseev2.task112.model.CardRank;
import ru.nsu.aeliseev2.task112.model.CardSuit;

class CardTests {
    @Test
    void nullRank() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Card(null, CardSuit.SPADES);
        });
    }

    @Test
    void nullSuit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Card(CardRank.JACK, null);
        });
    }

    @Test
    void cardEqualitySame() {
        var card1 = new Card(CardRank.JACK, CardSuit.SPADES);
        var card2 = new Card(CardRank.JACK, CardSuit.SPADES);
        Assertions.assertEquals(card1, card2);
    }

    @Test
    void equalityDifferentRanks() {
        var card1 = new Card(CardRank.KING, CardSuit.SPADES);
        var card2 = new Card(CardRank.JACK, CardSuit.SPADES);
        Assertions.assertNotEquals(card1, card2);
    }

    @Test
    void equalityDifferentSuits() {
        var card1 = new Card(CardRank.JACK, CardSuit.SPADES);
        var card2 = new Card(CardRank.JACK, CardSuit.CLUBS);
        Assertions.assertNotEquals(card1, card2);
    }

    @Test
    void equalityDifferentObject() {
        var card = new Card(CardRank.JACK, CardSuit.SPADES);
        var object = new Object();
        Assertions.assertNotEquals(card, object);
    }

    @Test
    void aceNormalValue() {
        var card = new Card(CardRank.ACE, CardSuit.SPADES);
        Assertions.assertEquals(11, card.getValue());
    }

    @Test
    void aceBustValue() {
        var card = new Card(CardRank.ACE, CardSuit.SPADES);
        Assertions.assertEquals(1, card.getBustValue());
    }

    @Test
    void toStringTest() {
        var card = new Card(CardRank.ACE, CardSuit.SPADES);
        Assertions.assertEquals("Туз Пики", card.toString());
    }
}
