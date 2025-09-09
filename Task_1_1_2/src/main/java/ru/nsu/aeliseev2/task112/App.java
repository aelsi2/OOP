package ru.nsu.aeliseev2.task112;

import java.util.Scanner;
import ru.nsu.aeliseev2.task112.controllers.ConsoleController;
import ru.nsu.aeliseev2.task112.controllers.DealerController;
import ru.nsu.aeliseev2.task112.model.CardHand;
import ru.nsu.aeliseev2.task112.model.CardPool;

@NoCoverageGenerated(reason = "Contains RNG and not much game logic.")
final class App {
    private App() {
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var messageStream = System.out;

        messageStream.println("Добро пожаловать в Блэкджек!");
        int deckCount = ConsoleUtils.inputNumber(scanner, messageStream,
            "Сколько колод по 52 карты?", 1, 8);
        int roundCount = ConsoleUtils.inputNumber(scanner, messageStream,
            "Сколько раундов?", 1, 15);
        messageStream.println();

        var playerController = new ConsoleController(messageStream, scanner);
        var dealerController = DealerController.INSTANCE;
        GameRoundFactory roundFactory = () -> {
            var pool = new CardPool(deckCount);
            var player = new Player(playerController, new CardHand(pool, 2));
            var dealer = new Player(dealerController, new CardHand(pool, 2));
            return new GameRound(player, dealer, pool, messageStream);
        };

        new Game(messageStream, scanner, roundFactory).run(roundCount);
    }
}
