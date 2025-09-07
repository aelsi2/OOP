package ru.nsu.aeliseev2.task112.tests;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task112.model.Card;
import ru.nsu.aeliseev2.task112.model.CardHand;
import ru.nsu.aeliseev2.task112.model.CardPool;
import ru.nsu.aeliseev2.task112.model.CardRank;
import ru.nsu.aeliseev2.task112.model.CardSuit;

class CardHandTests {
    @Test
    void createFromPool() {
        var pool = new CardPool(List.of(
            new Card(CardRank.NUM_3, CardSuit.DIAMONDS),
            new Card(CardRank.NUM_4, CardSuit.DIAMONDS)));
        var hand = new CardHand(pool, 2);

        int expectedValue = 7;
        Assertions.assertEquals(expectedValue, hand.getValue());
    }

    @Test
    void totalValueAceNormal() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_4, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));

        int expected = 17;
        Assertions.assertEquals(expected, hand.getValue());
    }

    @Test
    void totalValueAceBust() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));

        int expected = 13;
        Assertions.assertEquals(expected, hand.getValue());
    }

    @Test
    void isWinFalse() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        Assertions.assertFalse(hand.isWin());
    }

    @Test
    void isWinTrue() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        Assertions.assertTrue(hand.isWin());
    }

    @Test
    void isBustFalse() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.KING, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_3, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_5, CardSuit.SPADES));

        Assertions.assertFalse(hand.isBust());
    }

    @Test
    void isBustTrue() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.QUEEN, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_7, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_9, CardSuit.SPADES));

        Assertions.assertTrue(hand.isBust());
    }

    @Test
    void canHitTrue() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        Assertions.assertTrue(hand.canHit());
    }

    @Test
    void canHitFalseBust() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        Assertions.assertFalse(hand.canHit());
    }

    @Test
    void canHitFalseWin() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        Assertions.assertFalse(hand.canHit());
    }

    @Test
    void cardFormatAceNormal() {
        var card = new Card(CardRank.ACE, CardSuit.SPADES);
        var hand = new CardHand();
        hand.add(card);
        hand.add(new Card(CardRank.NUM_4, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));

        String expected = "Туз Пики (11)";
        Assertions.assertEquals(expected, hand.cardToString(card));
    }

    @Test
    void cardFormatAceBust() {
        var card = new Card(CardRank.ACE, CardSuit.SPADES);
        var hand = new CardHand();
        hand.add(card);
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));

        String expected = "Туз Пики (1)";
        Assertions.assertEquals(expected, hand.cardToString(card));
    }

    @Test
    void hiddenCardToString() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        String expected = "Десятка Пики (10)";
        Assertions.assertEquals(expected, hand.hiddenCardToString());
    }

    @Test
    void formatHiddenEmpty() {
        var hand = new CardHand();

        String expected = "[] => 0";
        Assertions.assertEquals(expected, hand.toString(true));
    }

    @Test
    void formatNotHiddenEmpty() {
        var hand = new CardHand();

        String expected = "[] => 0";
        Assertions.assertEquals(expected, hand.toString());
    }

    @Test
    void formatHidden() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.NUM_3, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        String expected = "[Тройка Пики (3), Двойка Пики (2), <закрытая карта>]";
        Assertions.assertEquals(expected, hand.toString(true));
    }

    @Test
    void formatNotHidden() {
        var hand = new CardHand();
        hand.add(new Card(CardRank.NUM_3, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        String expected = "[Тройка Пики (3), Двойка Пики (2), Десятка Пики (10)] => 15";
        Assertions.assertEquals(expected, hand.toString());
    }
}
