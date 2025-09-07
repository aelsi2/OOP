package ru.nsu.aeliseev2.task112;

import java.io.PrintStream;
import ru.nsu.aeliseev2.task112.controllers.GameAction;
import ru.nsu.aeliseev2.task112.model.CardPool;

/**
 * Represents a game round.
 */
public class GameRound {
    private final Player m_player;
    private final Player m_dealer;
    private final CardPool m_cardPool;
    private final PrintStream m_messageStream;

    /**
     * Creates a new game round.
     *
     * @param player        The player.
     * @param dealer        The dealer.
     * @param pool          The card pool.
     * @param messageStream The stream to print game messages to.
     */
    public GameRound(Player player, Player dealer, CardPool pool,
                     PrintStream messageStream) {
        m_player = player;
        m_dealer = dealer;
        m_cardPool = pool;
        m_messageStream = messageStream;
    }

    private void printHands(boolean hideDealer) {
        m_messageStream.printf("    Ваши карты: %s\n", m_player.getHand());
        m_messageStream.printf("    Карты дилера: %s\n",
            m_dealer.getHand().toString(hideDealer));
    }

    private Player processTurn(Player player, Player opponent, boolean hideDealer) {
        while (player.getHand().canHit()) {
            if (player.chooseAction() == GameAction.STAND) {
                break;
            }
            var card = m_cardPool.take();
            player.getHand().add(card);
            m_messageStream.printf("Открыта карта %s\n", player.getHand().cardToString(card));
            printHands(hideDealer);
        }
        if (player.getHand().isWin()) {
            return player;
        } else if (player.getHand().isBust()) {
            return opponent;
        } else {
            return null;
        }
    }

    /**
     * Runs the game round. A game round shouldn't be run more than once.
     *
     * @return The winning player or {@code null} on draw.
     */
    public Player run() {
        m_messageStream.println("Дилер раздал карты.");
        printHands(true);
        m_messageStream.println();
        m_messageStream.println("Ваш ход\n-------");
        if (processTurn(m_player, m_dealer, true) instanceof Player winner) {
            return winner;
        }
        m_messageStream.println();
        m_messageStream.println("Ход дилера\n----------");
        m_messageStream.printf("Открыта закрытая карта %s\n",
            m_dealer.getHand().hiddenCardToString());
        printHands(false);
        if (processTurn(m_dealer, m_player, false) instanceof Player winner) {
            return winner;
        }
        if (m_player.getHand().getValue() > m_dealer.getHand().getValue()) {
            return m_player;
        } else if (m_dealer.getHand().getValue() > m_player.getHand().getValue()) {
            return m_dealer;
        } else {
            return null;
        }
    }
}
