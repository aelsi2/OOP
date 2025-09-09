package ru.nsu.aeliseev2.task112;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents a game of blackjack with multiple rounds.
 */
public class Game {
    private final PrintStream messageStream;
    private final Scanner scanner;
    private final GameRoundFactory roundFactory;

    /**
     * Creates a new game.
     *
     * @param messageStream The stream to print messages to.
     * @param scanner       The scanner to read player inputs from.
     * @param roundFactory  The factory for creating card pools for each round.
     */
    public Game(PrintStream messageStream, Scanner scanner, GameRoundFactory roundFactory) {
        this.messageStream = messageStream;
        this.scanner = scanner;
        this.roundFactory = roundFactory;
    }

    /**
     * Runs the game.
     *
     * @param roundCount The number of rounds in the game.
     * @return The outcome of the game.
     */
    public GameResult run(int roundCount) {
        int dealerWins = 0;
        int playerWins = 0;

        for (int roundIndex = 0; roundIndex < roundCount; roundIndex++) {
            var round = roundFactory.create();

            printRoundStart(roundIndex + 1);
            var result = round.run();
            switch (result) {
                case WIN:
                    playerWins++;
                    break;
                case LOSE:
                    dealerWins++;
                    break;
                default:
                    break;
            }
            printRoundResult(result);
            printScore(playerWins, dealerWins);
            ConsoleUtils.pause(messageStream, scanner);
        }

        GameResult result;
        if (playerWins > dealerWins) {
            result = GameResult.WIN;
        } else if (dealerWins > playerWins) {
            result = GameResult.LOSE;
        } else {
            result = GameResult.DRAW;
        }
        printFinalResult(result);
        printScore(playerWins, dealerWins);
        return result;
    }

    private void printRoundStart(int number) {
        messageStream.printf("Раунд %d\n", number);
    }

    private void printRoundResult(GameResult result) {
        messageStream.println();
        switch (result) {
            case WIN:
                messageStream.println("Вы выиграли раунд!");
                break;
            case LOSE:
                messageStream.println("Дилер выиграл раунд!");
                break;
            default:
                messageStream.println("Ничья!");
        }
    }

    private void printFinalResult(GameResult result) {
        switch (result) {
            case WIN:
                messageStream.println("Вы победили!");
                break;
            case LOSE:
                messageStream.println("Вы проиграли!");
                break;
            default:
                messageStream.println("Ничья!");
                break;
        }
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
