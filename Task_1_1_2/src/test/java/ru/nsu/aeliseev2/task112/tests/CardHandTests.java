package ru.nsu.aeliseev2.task112.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task112.model.Card;
import ru.nsu.aeliseev2.task112.model.CardHand;
import ru.nsu.aeliseev2.task112.model.CardRank;
import ru.nsu.aeliseev2.task112.model.CardSuit;

class CardHandTests {
    @Test
    void totalValueAceNormal() {
        int expected = 17;

        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_4, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));

        Assertions.assertEquals(expected, hand.getValue());
    }

    @Test
    void totalValueAceBust() {
        int expected = 13;

        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));

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
        String expected = "Туз Пики (11)";

        var card = new Card(CardRank.ACE, CardSuit.SPADES);

        var hand = new CardHand();
        hand.add(card);
        hand.add(new Card(CardRank.NUM_4, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));

        Assertions.assertEquals(expected, hand.cardToString(card));
    }

    @Test
    void cardFormatAceBust() {
        String expected = "Туз Пики (1)";

        var card = new Card(CardRank.ACE, CardSuit.SPADES);

        var hand = new CardHand();
        hand.add(card);
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));

        Assertions.assertEquals(expected, hand.cardToString(card));
    }

    @Test
    void hiddenCardToString() {
        String expected = "Десятка Пики (10)";

        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        Assertions.assertEquals(expected, hand.hiddenCardToString());
    }

    @Test
    void formatHiddenEmpty() {
        String expected = "[] => 0";

        var hand = new CardHand();
        Assertions.assertEquals(expected, hand.toString(true));
    }

    @Test
    void formatNotHiddenEmpty() {
        String expected = "[] => 0";

        var hand = new CardHand();
        Assertions.assertEquals(expected, hand.toString());
    }

    @Test
    void formatHidden() {
        String expected = "[Тройка Пики (3), Двойка Пики (2), <закрытая карта>]";

        var hand = new CardHand();
        hand.add(new Card(CardRank.NUM_3, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        Assertions.assertEquals(expected, hand.toString(true));
    }

    @Test
    void formatNotHidden() {
        String expected = "[Тройка Пики (3), Двойка Пики (2), Десятка Пики (10)] => 15";

        var hand = new CardHand();
        hand.add(new Card(CardRank.NUM_3, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));

        Assertions.assertEquals(expected, hand.toString());
    }
}
