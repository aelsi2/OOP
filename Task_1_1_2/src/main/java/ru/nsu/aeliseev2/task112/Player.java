package ru.nsu.aeliseev2.task112;

import ru.nsu.aeliseev2.task112.controllers.GameAction;
import ru.nsu.aeliseev2.task112.controllers.PlayerController;
import ru.nsu.aeliseev2.task112.model.CardHand;

/**
 * Represents a player in a game of blackjack.
 */
public class Player {
    private final CardHand hand;
    private final PlayerController controller;

    /**
     * Creates a new player.
     *
     * @param controller The player's controller.
     * @param hand The player's hand.
     */
    public Player(PlayerController controller, CardHand hand) {
        this.controller = controller;
        this.hand = hand;
    }

    /**
     * Gets the player's hand.
     *
     * @return The player's hand.
     */
    public CardHand getHand() {
        return hand;
    }

    /**
     * Chooses the next action depending on the player's hand.
     *
     * @return The next action.
     */
    public GameAction chooseAction() {
        return controller.chooseAction(hand);
    }
}
