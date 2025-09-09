package ru.nsu.aeliseev2.task112.tests;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task112.Game;
import ru.nsu.aeliseev2.task112.GameResult;
import ru.nsu.aeliseev2.task112.GameRound;
import ru.nsu.aeliseev2.task112.GameRoundFactory;
import ru.nsu.aeliseev2.task112.Player;
import ru.nsu.aeliseev2.task112.controllers.GameAction;
import ru.nsu.aeliseev2.task112.controllers.PlayerController;
import ru.nsu.aeliseev2.task112.model.Card;
import ru.nsu.aeliseev2.task112.model.CardHand;
import ru.nsu.aeliseev2.task112.model.CardPool;
import ru.nsu.aeliseev2.task112.model.CardRank;
import ru.nsu.aeliseev2.task112.model.CardSuit;

class GameTests {
    @Test
    void win() {
        var scanner = new Scanner("\n\n\n\n\n\n\n\n\n");
        var messageStream = new PrintStream(DummyOutputStream.INSTANCE);

        PlayerController playerController = new ScriptedPlayerController(GameAction.STAND);
        PlayerController dealerController = new ScriptedPlayerController(GameAction.STAND);

        var cards = List.of(
            new Card(CardRank.KING, CardSuit.DIAMONDS),
            new Card(CardRank.ACE, CardSuit.CLUBS),
            new Card(CardRank.KING, CardSuit.HEARTS),
            new Card(CardRank.NUM_9, CardSuit.SPADES)
        );

        GameRoundFactory roundFactory = () -> {
            var pool = new CardPool(cards);
            var player = new Player(playerController, new CardHand(pool, 2));
            var dealer = new Player(dealerController, new CardHand(pool, 2));
            return new GameRound(player, dealer, pool, messageStream);
        };

        var game = new Game(messageStream, scanner, roundFactory);
        Assertions.assertEquals(GameResult.WIN, game.run(1));
    }

    @Test
    void lose() {
        var scanner = new Scanner("\n\n\n\n\n\n\n\n\n");
        var messageStream = new PrintStream(DummyOutputStream.INSTANCE);

        PlayerController playerController = new ScriptedPlayerController(GameAction.STAND);
        PlayerController dealerController = new ScriptedPlayerController(GameAction.STAND);

        var cards = List.of(
            new Card(CardRank.KING, CardSuit.DIAMONDS),
            new Card(CardRank.NUM_9, CardSuit.CLUBS),
            new Card(CardRank.KING, CardSuit.HEARTS),
            new Card(CardRank.KING, CardSuit.SPADES)
        );

        GameRoundFactory roundFactory = () -> {
            var pool = new CardPool(cards);
            var player = new Player(playerController, new CardHand(pool, 2));
            var dealer = new Player(dealerController, new CardHand(pool, 2));
            return new GameRound(player, dealer, pool, messageStream);
        };

        var game = new Game(messageStream, scanner, roundFactory);
        Assertions.assertEquals(GameResult.LOSE, game.run(1));
    }

    @Test
    void draw() {
        var scanner = new Scanner("\n\n\n\n\n\n\n\n\n");
        var messageStream = new PrintStream(DummyOutputStream.INSTANCE);

        PlayerController playerController = new ScriptedPlayerController(GameAction.STAND);
        PlayerController dealerController = new ScriptedPlayerController(GameAction.STAND);

        var cards = List.of(
            new Card(CardRank.NUM_9, CardSuit.DIAMONDS),
            new Card(CardRank.NUM_9, CardSuit.CLUBS),
            new Card(CardRank.NUM_9, CardSuit.HEARTS),
            new Card(CardRank.NUM_9, CardSuit.SPADES)
        );

        GameRoundFactory roundFactory = () -> {
            var pool = new CardPool(cards);
            var player = new Player(playerController, new CardHand(pool, 2));
            var dealer = new Player(dealerController, new CardHand(pool, 2));
            return new GameRound(player, dealer, pool, messageStream);
        };

        var game = new Game(messageStream, scanner, roundFactory);
        Assertions.assertEquals(GameResult.DRAW, game.run(1));
    }

    @Test
    void winDraw() {
        var scanner = new Scanner("\n\n\n\n\n\n\n\n\n");
        var messageStream = new PrintStream(DummyOutputStream.INSTANCE);

        PlayerController playerController = new ScriptedPlayerController(
            GameAction.STAND,
            GameAction.HIT
        );
        PlayerController dealerController = new ScriptedPlayerController(
            GameAction.STAND,
            GameAction.STAND
        );

        var cards = List.of(
            new Card(CardRank.NUM_9, CardSuit.DIAMONDS),
            new Card(CardRank.NUM_9, CardSuit.CLUBS),
            new Card(CardRank.NUM_9, CardSuit.HEARTS),
            new Card(CardRank.NUM_9, CardSuit.SPADES),
            new Card(CardRank.NUM_3, CardSuit.SPADES)
        );

        GameRoundFactory roundFactory = () -> {
            var pool = new CardPool(cards);
            var player = new Player(playerController, new CardHand(pool, 2));
            var dealer = new Player(dealerController, new CardHand(pool, 2));
            return new GameRound(player, dealer, pool, messageStream);
        };

        var game = new Game(messageStream, scanner, roundFactory);
        Assertions.assertEquals(GameResult.WIN, game.run(2));
    }

    @Test
    void winWin() {
        var scanner = new Scanner("\n\n\n\n\n\n\n\n\n");
        var messageStream = new PrintStream(DummyOutputStream.INSTANCE);

        PlayerController playerController = new ScriptedPlayerController(
            GameAction.HIT,
            GameAction.HIT
        );
        PlayerController dealerController = new ScriptedPlayerController(
            GameAction.STAND,
            GameAction.STAND
        );

        var cards = List.of(
            new Card(CardRank.NUM_9, CardSuit.DIAMONDS),
            new Card(CardRank.NUM_9, CardSuit.CLUBS),
            new Card(CardRank.NUM_9, CardSuit.HEARTS),
            new Card(CardRank.NUM_9, CardSuit.SPADES),
            new Card(CardRank.NUM_3, CardSuit.SPADES)
        );

        GameRoundFactory roundFactory = () -> {
            var pool = new CardPool(cards);
            var player = new Player(playerController, new CardHand(pool, 2));
            var dealer = new Player(dealerController, new CardHand(pool, 2));
            return new GameRound(player, dealer, pool, messageStream);
        };

        var game = new Game(messageStream, scanner, roundFactory);
        Assertions.assertEquals(GameResult.WIN, game.run(2));
    }

    @Test
    void winLose() {
        var scanner = new Scanner("\n\n\n\n\n\n\n\n\n");
        var messageStream = new PrintStream(DummyOutputStream.INSTANCE);

        PlayerController playerController = new ScriptedPlayerController(
            GameAction.STAND,
            GameAction.HIT
        );
        PlayerController dealerController = new ScriptedPlayerController(
            GameAction.HIT,
            GameAction.STAND
        );

        var cards = List.of(
            new Card(CardRank.NUM_9, CardSuit.DIAMONDS),
            new Card(CardRank.NUM_9, CardSuit.CLUBS),
            new Card(CardRank.NUM_9, CardSuit.HEARTS),
            new Card(CardRank.NUM_9, CardSuit.SPADES),
            new Card(CardRank.NUM_3, CardSuit.SPADES)
        );

        GameRoundFactory roundFactory = () -> {
            var pool = new CardPool(cards);
            var player = new Player(playerController, new CardHand(pool, 2));
            var dealer = new Player(dealerController, new CardHand(pool, 2));
            return new GameRound(player, dealer, pool, messageStream);
        };

        var game = new Game(messageStream, scanner, roundFactory);
        Assertions.assertEquals(GameResult.DRAW, game.run(2));
    }

    @Test
    void loseDraw() {
        var scanner = new Scanner("\n\n\n\n\n\n\n\n\n");
        var messageStream = new PrintStream(DummyOutputStream.INSTANCE);

        PlayerController playerController = new ScriptedPlayerController(
            GameAction.STAND,
            GameAction.STAND
        );
        PlayerController dealerController = new ScriptedPlayerController(
            GameAction.HIT,
            GameAction.STAND
        );

        var cards = List.of(
            new Card(CardRank.NUM_9, CardSuit.DIAMONDS),
            new Card(CardRank.NUM_9, CardSuit.CLUBS),
            new Card(CardRank.NUM_9, CardSuit.HEARTS),
            new Card(CardRank.NUM_9, CardSuit.SPADES),
            new Card(CardRank.NUM_3, CardSuit.SPADES)
        );

        GameRoundFactory roundFactory = () -> {
            var pool = new CardPool(cards);
            var player = new Player(playerController, new CardHand(pool, 2));
            var dealer = new Player(dealerController, new CardHand(pool, 2));
            return new GameRound(player, dealer, pool, messageStream);
        };

        var game = new Game(messageStream, scanner, roundFactory);
        Assertions.assertEquals(GameResult.LOSE, game.run(2));
    }

    @Test
    void loseLose() {
        var scanner = new Scanner("\n\n\n\n\n\n\n\n\n");
        var messageStream = new PrintStream(DummyOutputStream.INSTANCE);

        PlayerController playerController = new ScriptedPlayerController(
            GameAction.STAND,
            GameAction.STAND
        );
        PlayerController dealerController = new ScriptedPlayerController(
            GameAction.HIT,
            GameAction.HIT
        );

        var cards = List.of(
            new Card(CardRank.NUM_9, CardSuit.DIAMONDS),
            new Card(CardRank.NUM_9, CardSuit.CLUBS),
            new Card(CardRank.NUM_9, CardSuit.HEARTS),
            new Card(CardRank.NUM_9, CardSuit.SPADES),
            new Card(CardRank.NUM_3, CardSuit.SPADES)
        );

        GameRoundFactory roundFactory = () -> {
            var pool = new CardPool(cards);
            var player = new Player(playerController, new CardHand(pool, 2));
            var dealer = new Player(dealerController, new CardHand(pool, 2));
            return new GameRound(player, dealer, pool, messageStream);
        };

        var game = new Game(messageStream, scanner, roundFactory);
        Assertions.assertEquals(GameResult.LOSE, game.run(2));
    }

    @Test
    void drawDraw() {
        var scanner = new Scanner("\n\n\n\n\n\n\n\n\n");
        var messageStream = new PrintStream(DummyOutputStream.INSTANCE);

        PlayerController playerController = new ScriptedPlayerController(
            GameAction.STAND,
            GameAction.STAND
        );
        PlayerController dealerController = new ScriptedPlayerController(
            GameAction.STAND,
            GameAction.STAND
        );

        var cards = List.of(
            new Card(CardRank.NUM_9, CardSuit.DIAMONDS),
            new Card(CardRank.NUM_9, CardSuit.CLUBS),
            new Card(CardRank.NUM_9, CardSuit.HEARTS),
            new Card(CardRank.NUM_9, CardSuit.SPADES),
            new Card(CardRank.NUM_3, CardSuit.SPADES)
        );

        GameRoundFactory roundFactory = () -> {
            var pool = new CardPool(cards);
            var player = new Player(playerController, new CardHand(pool, 2));
            var dealer = new Player(dealerController, new CardHand(pool, 2));
            return new GameRound(player, dealer, pool, messageStream);
        };

        var game = new Game(messageStream, scanner, roundFactory);
        Assertions.assertEquals(GameResult.DRAW, game.run(2));
    }
}
