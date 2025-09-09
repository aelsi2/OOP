package ru.nsu.aeliseev2.task112;

import java.io.PrintStream;
import java.util.Scanner;
import ru.nsu.aeliseev2.task112.controllers.ConsoleController;
import ru.nsu.aeliseev2.task112.controllers.DealerController;
import ru.nsu.aeliseev2.task112.model.CardHand;
import ru.nsu.aeliseev2.task112.model.CardPoolFactory;

/**
 * Represents a game of blackjack with multiple rounds.
 */
public class Game {
    private final PrintStream messageStream;
    private final Scanner scanner;
    private final CardPoolFactory poolFactory;

    /**
     * Creates a new game.
     *
     * @param messageStream The stream to print messages to.
     * @param scanner       The scanner to read player inputs from.
     * @param poolFactory   The factory for creating card pools for each round.
     */
    public Game(PrintStream messageStream, Scanner scanner, CardPoolFactory poolFactory) {
        this.messageStream = messageStream;
        this.scanner = scanner;
        this.poolFactory = poolFactory;
    }

    /**
     * Runs the game.
     *
     * @param roundCount The number of rounds in the game.
     */
    public void run(int roundCount) {
        var playerController = new ConsoleController(scanner, messageStream);
        var dealerController = DealerController.INSTANCE;

        int dealerWins = 0;
        int playerWins = 0;

        for (int roundIndex = 0; roundIndex < roundCount; roundIndex++) {
            var pool = poolFactory.createPool();
            var player = new Player(new CardHand(pool, 2), playerController);
            var dealer = new Player(new CardHand(pool, 2), dealerController);
            var round = new GameRound(player, dealer, pool, messageStream);
            messageStream.printf("Раунд %d\n", roundIndex + 1);
            var winner = round.run();
            messageStream.println();
            if (winner == player) {
                messageStream.println("Вы выиграли раунд!");
                playerWins++;
            } else if (winner == dealer) {
                messageStream.println("Дилер выиграл раунд!");
                dealerWins++;
            } else {
                messageStream.println("Ничья!");
            }

            printScore(playerWins, dealerWins);
            ConsoleUtils.pause(messageStream, scanner);
        }
        printEndMessage(playerWins, dealerWins);
    }

    private void printEndMessage(int playerWins, int dealerWins) {
        if (playerWins > dealerWins) {
            messageStream.println("Вы победили!");
        } else if (dealerWins > playerWins) {
            messageStream.println("Вы проиграли!");
        } else {
            messageStream.println("Ничья!");
        }
        printScore(playerWins, dealerWins);
    }

    private void printScore(int playerWins, int dealerWins) {
        if (playerWins > dealerWins) {
            messageStream.printf("Счет %d:%d в вашу пользу.\n", playerWins, dealerWins);
        } else if (dealerWins > playerWins) {
            messageStream.printf("Счет %d:%d в пользу дилера.\n", dealerWins, playerWins);
        } else {
            messageStream.printf("Счет %d:%d.\n", playerWins, dealerWins);
        }
    }
}
