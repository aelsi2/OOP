package ru.nsu.aeliseev2.task112.tests;

import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task112.GameRound;
import ru.nsu.aeliseev2.task112.Player;
import ru.nsu.aeliseev2.task112.controllers.GameAction;
import ru.nsu.aeliseev2.task112.model.*;

class GameRoundTests {
    @Test
    void immediatePlayerWin() {
        final var playerHand = new CardHand();
        playerHand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        playerHand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        final var player = new Player(playerHand, hand -> GameAction.STAND);

        final var dealerHand = new CardHand();
        dealerHand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        dealerHand.add(new Card(CardRank.JACK, CardSuit.SPADES));
        final var dealer = new Player(dealerHand, hand -> GameAction.STAND);

        var pool = new CardPool(1);
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        var round = new GameRound(player, dealer, pool, stream);

        Assertions.assertEquals(player, round.run());
    }

    @Test
    void immediateDealerWin() {
        final var playerHand = new CardHand();
        playerHand.add(new Card(CardRank.JACK, CardSuit.SPADES));
        playerHand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        final var player = new Player(playerHand, hand -> GameAction.STAND);

        final var dealerHand = new CardHand();
        dealerHand.add(new Card(CardRank.ACE, CardSuit.SPADES));
        dealerHand.add(new Card(CardRank.JACK, CardSuit.SPADES));
        final var dealer = new Player(dealerHand, hand -> GameAction.STAND);

        var pool = new CardPool(1);
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        var round = new GameRound(player, dealer, pool, stream);

        Assertions.assertEquals(dealer, round.run());
    }

    @Test
    void playerBust() {
        var playerHand = new CardHand();
        playerHand.add(new Card(CardRank.KING, CardSuit.SPADES));
        playerHand.add(new Card(CardRank.QUEEN, CardSuit.SPADES));
        var player = new Player(playerHand, hand -> GameAction.HIT);

        final var dealerHand = new CardHand();
        dealerHand.add(new Card(CardRank.NUM_3, CardSuit.SPADES));
        dealerHand.add(new Card(CardRank.NUM_2, CardSuit.SPADES));
        final var dealer = new Player(dealerHand, hand -> GameAction.STAND);

        var pool = new CardPool(List.of(new Card(CardRank.NUM_2, CardSuit.DIAMONDS)));
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        var round = new GameRound(player, dealer, pool, stream);

        Assertions.assertEquals(dealer, round.run());
    }

    @Test
    void dealerBust() {
        final var playerHand = new CardHand();
        playerHand.add(new Card(CardRank.KING, CardSuit.SPADES));
        playerHand.add(new Card(CardRank.QUEEN, CardSuit.SPADES));
        final var player = new Player(playerHand, hand -> GameAction.STAND);

        final var dealerHand = new CardHand();
        dealerHand.add(new Card(CardRank.KING, CardSuit.SPADES));
        dealerHand.add(new Card(CardRank.QUEEN, CardSuit.SPADES));
        final var dealer = new Player(dealerHand, hand -> GameAction.HIT);

        var pool = new CardPool(List.of(new Card(CardRank.NUM_2, CardSuit.DIAMONDS)));
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        var round = new GameRound(player, dealer, pool, stream);

        Assertions.assertEquals(player, round.run());
    }

    @Test
    void playerWin() {
        final var playerHand = new CardHand();
        playerHand.add(new Card(CardRank.NUM_10, CardSuit.CLUBS));
        playerHand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        final var player = new Player(playerHand, hand -> GameAction.STAND);

        final var dealerHand = new CardHand();
        dealerHand.add(new Card(CardRank.NUM_7, CardSuit.SPADES));
        dealerHand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        final var dealer = new Player(dealerHand, hand -> GameAction.STAND);

        var pool = new CardPool(1);
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        var round = new GameRound(player, dealer, pool, stream);

        Assertions.assertEquals(player, round.run());
    }

    @Test
    void dealerWin() {
        final var playerHand = new CardHand();
        playerHand.add(new Card(CardRank.NUM_7, CardSuit.CLUBS));
        playerHand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        final var player = new Player(playerHand, hand -> GameAction.STAND);

        final var dealerHand = new CardHand();
        dealerHand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        dealerHand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        final var dealer = new Player(dealerHand, hand -> GameAction.STAND);

        var pool = new CardPool(1);
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        var round = new GameRound(player, dealer, pool, stream);

        Assertions.assertEquals(dealer, round.run());
    }

    @Test
    void draw() {
        final var playerHand = new CardHand();
        playerHand.add(new Card(CardRank.NUM_7, CardSuit.CLUBS));
        playerHand.add(new Card(CardRank.NUM_10, CardSuit.SPADES));
        final var player = new Player(playerHand, hand -> GameAction.STAND);

        final var dealerHand = new CardHand();
        dealerHand.add(new Card(CardRank.NUM_7, CardSuit.HEARTS));
        dealerHand.add(new Card(CardRank.NUM_10, CardSuit.DIAMONDS));
        final var dealer = new Player(dealerHand, hand -> GameAction.STAND);

        var pool = new CardPool(1);
        var stream = new PrintStream(DummyOutputStream.INSTANCE);
        var round = new GameRound(player, dealer, pool, stream);

        Assertions.assertNull(round.run());
    }
}
