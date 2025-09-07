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
public class Game {
    private final PrintStream m_messageStream;
    private final Scanner m_scanner;
    private final int m_deckCount;
    private final int m_roundCount;

    /**
     * Creates a new game.
     *
     * @param messageStream The stream to print messages to.
     * @param scanner The scanner to read player inputs from.
     * @param deckCount The number of decks to put in the pool.
     * @param roundCount The number of rounds.
     */
    public Game(PrintStream messageStream, Scanner scanner, int deckCount, int roundCount) {
        m_messageStream = messageStream;
        m_scanner = scanner;
        m_deckCount = deckCount;
        m_roundCount = roundCount;
    }

    /**
     * Runs the game.
     */
    public void run() {
        var playerController = new ConsoleController(m_scanner, m_messageStream);
        var dealerController = DealerController.INSTANCE;

        int dealerWins = 0;
        int playerWins = 0;

        for (int roundIndex = 0; roundIndex < m_roundCount; roundIndex++) {
            var pool = new CardPool(m_deckCount);
            var player = new Player(new CardHand(pool, 2), playerController);
            var dealer = new Player(new CardHand(pool, 2), dealerController);
            var round = new GameRound(player, dealer, pool, m_messageStream);
            m_messageStream.printf("Раунд %d\n", roundIndex + 1);
            var winner = round.run();
            m_messageStream.println();
            if (winner == player) {
                m_messageStream.println("Вы выиграли раунд!");
                playerWins++;
            }
            else if (winner == dealer) {
                m_messageStream.println("Дилер выиграл раунд!");
                dealerWins++;
            } else {
                m_messageStream.println("Ничья!");
            }

            printScore(playerWins, dealerWins);
            ConsoleUtils.pause(m_messageStream, m_scanner);
        }
        printEndMessage(playerWins, dealerWins);
    }

    private void printEndMessage(int playerWins, int dealerWins) {
        if (playerWins > dealerWins) {
            m_messageStream.println("Вы победили!");
        } else if (dealerWins > playerWins) {
            m_messageStream.println("Вы проиграли!");
        } else {
            m_messageStream.println("Ничья!");
        }
        printScore(playerWins, dealerWins);
    }

    private void printScore(int playerWins, int dealerWins) {
        if (playerWins > dealerWins) {
            m_messageStream.printf("Счет %d:%d в вашу пользу.\n", playerWins, dealerWins);
        } else if (dealerWins > playerWins) {
            m_messageStream.printf("Счет %d:%d в пользу дилера.\n", dealerWins, playerWins);
        } else {
            m_messageStream.printf("Счет %d:%d.\n", playerWins, dealerWins);
        }
    }
}
