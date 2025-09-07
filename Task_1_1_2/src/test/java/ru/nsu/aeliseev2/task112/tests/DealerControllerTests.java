package ru.nsu.aeliseev2.task112.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task112.controllers.DealerController;
import ru.nsu.aeliseev2.task112.controllers.GameAction;
import ru.nsu.aeliseev2.task112.model.Card;
import ru.nsu.aeliseev2.task112.model.CardHand;
import ru.nsu.aeliseev2.task112.model.CardRank;
import ru.nsu.aeliseev2.task112.model.CardSuit;

class DealerControllerTests {
    @Test
    void above17() {
        var expected = GameAction.STAND;

        var hand = new CardHand();
        hand.add(new Card(CardRank.KING, CardSuit.SPADES));
        hand.add(new Card(CardRank.QUEEN, CardSuit.SPADES));

        var controller = DealerController.INSTANCE;
        Assertions.assertEquals(expected, controller.chooseAction(hand));
    }

    @Test
    void exactly17() {
        var expected = GameAction.STAND;

        var hand = new CardHand();
        hand.add(new Card(CardRank.KING, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_7, CardSuit.SPADES));

        var controller = DealerController.INSTANCE;
        Assertions.assertEquals(expected, controller.chooseAction(hand));
    }

    @Test
    void below17() {
        var expected = GameAction.HIT;

        var hand = new CardHand();
        hand.add(new Card(CardRank.KING, CardSuit.SPADES));
        hand.add(new Card(CardRank.NUM_5, CardSuit.SPADES));

        var controller = DealerController.INSTANCE;
        Assertions.assertEquals(expected, controller.chooseAction(hand));
    }
}
