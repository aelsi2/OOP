package ru.nsu.aeliseev2.task112;

import java.io.PrintStream;
import ru.nsu.aeliseev2.task112.controllers.GameAction;
import ru.nsu.aeliseev2.task112.model.CardPool;

/**
 * Represents a game round.
 */
public class GameRound {
    private final Player player;
    private final Player dealer;
    private final CardPool cardPool;
    private final PrintStream messageStream;

    /**
     * Creates a new game round.
     *
     * @param player        The player.
     * @param dealer        The dealer.
     * @param cardPool          The card pool.
     * @param messageStream The stream to print game messages to.
     */
    public GameRound(Player player, Player dealer, CardPool cardPool,
                     PrintStream messageStream) {
        this.player = player;
        this.dealer = dealer;
        this.cardPool = cardPool;
        this.messageStream = messageStream;
    }

    private void printHands(boolean hideDealer) {
        messageStream.printf("    Ваши карты: %s\n", player.getHand());
        messageStream.printf("    Карты дилера: %s\n",
            dealer.getHand().toString(hideDealer));
    }

    private Player processTurn(Player player, Player opponent, boolean hideDealer) {
        while (player.getHand().canHit()) {
            if (player.chooseAction() == GameAction.STAND) {
                break;
            }
            var card = cardPool.take();
            player.getHand().add(card);
            messageStream.printf("Открыта карта %s\n", player.getHand().cardToString(card));
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
        messageStream.println("Дилер раздал карты.");
        printHands(true);

        messageStream.println();
        messageStream.println("Ваш ход\n-------");

        Player winner;
        winner = processTurn(player, dealer, true);
        if (winner != null) {
            return winner;
        }

        messageStream.println();
        messageStream.println("Ход дилера\n----------");
        messageStream.printf("Открыта закрытая карта %s\n",
            dealer.getHand().hiddenCardToString());
        printHands(false);

        winner = processTurn(dealer, player, false);
        if (winner != null) {
            return winner;
        }

        if (player.getHand().getValue() > dealer.getHand().getValue()) {
            return player;
        } else if (dealer.getHand().getValue() > player.getHand().getValue()) {
            return dealer;
        } else {
            return null;
        }
    }
}
