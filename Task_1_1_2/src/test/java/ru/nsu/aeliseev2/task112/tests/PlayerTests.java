package ru.nsu.aeliseev2.task112.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task112.Player;
import ru.nsu.aeliseev2.task112.controllers.GameAction;
import ru.nsu.aeliseev2.task112.controllers.PlayerController;
import ru.nsu.aeliseev2.task112.model.Card;
import ru.nsu.aeliseev2.task112.model.CardHand;
import ru.nsu.aeliseev2.task112.model.CardRank;
import ru.nsu.aeliseev2.task112.model.CardSuit;

class PlayerTests {
    @Test
    void passControllerAndHandHit() {
        var controller = new PlayerController() {
            @Override
            public GameAction chooseAction(CardHand hand) {
                if (hand.getValue() == 10) {
                    return GameAction.STAND;
                }
                return GameAction.HIT;
            }
        };
        var hand = new CardHand();
        hand.add(new Card(CardRank.ACE, CardSuit.DIAMONDS));
        var player = new Player(hand, controller);

        var expected = GameAction.HIT;
        Assertions.assertEquals(expected, player.chooseAction());
    }

    @Test
    void passControllerAndHandStand() {
        var controller = new PlayerController() {
            @Override
            public GameAction chooseAction(CardHand hand) {
                if (hand.getValue() == 10) {
                    return GameAction.STAND;
                }
                return GameAction.HIT;
            }
        };
        var hand = new CardHand();
        hand.add(new Card(CardRank.KING, CardSuit.DIAMONDS));
        var player = new Player(hand, controller);

        var expected = GameAction.STAND;
        Assertions.assertEquals(expected, player.chooseAction());
    }
}
