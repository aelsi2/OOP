package ru.nsu.aeliseev2.task112;

import java.io.PrintStream;
import java.util.Scanner;
import ru.nsu.aeliseev2.task112.controllers.ConsoleController;
import ru.nsu.aeliseev2.task112.controllers.DealerController;
import ru.nsu.aeliseev2.task112.model.CardHand;
import ru.nsu.aeliseev2.task112.model.CardPool;

/**
 * Represents a game of blackjack with multiple rounds.
 */
@NoCoverageGenerated(reason = "Contains RNG and not much game logic (mostly messages).")
public class Game {
    private final PrintStream messageStream;
    private final Scanner scanner;
    private final int deckCount;
    private final int roundCount;

    /**
     * Creates a new game.
     *
     * @param messageStream The stream to print messages to.
     * @param scanner       The scanner to read player inputs from.
     * @param deckCount     The number of decks to put in the pool.
     * @param roundCount    The number of rounds.
     */
    public Game(PrintStream messageStream, Scanner scanner, int deckCount, int roundCount) {
        this.messageStream = messageStream;
        this.scanner = scanner;
        this.deckCount = deckCount;
        this.roundCount = roundCount;
    }

    /**
     * Runs the game.
     */
    public void run() {
        var playerController = new ConsoleController(scanner, messageStream);
        var dealerController = DealerController.INSTANCE;

        int dealerWins = 0;
        int playerWins = 0;

        for (int roundIndex = 0; roundIndex < roundCount; roundIndex++) {
            var pool = new CardPool(deckCount);
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
