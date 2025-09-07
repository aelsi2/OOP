package ru.nsu.aeliseev2.task112;

import ru.nsu.aeliseev2.task112.controllers.GameAction;
import ru.nsu.aeliseev2.task112.controllers.PlayerController;
import ru.nsu.aeliseev2.task112.model.CardHand;

/**
 * Represents a player in a game of blackjack.
 */
public class Player {
    private final CardHand m_hand;
    private final PlayerController m_controller;

    /**
     * Creates a new player.
     *
     * @param hand The player's hand.
     * @param controller The player's controller.
     */
    public Player(CardHand hand, PlayerController controller) {
        m_hand = hand;
        m_controller = controller;
    }

    /**
     * Gets the player's hand.
     *
     * @return The player's hand.
     */
    public CardHand getHand() {
        return m_hand;
    }

    /**
     * Chooses the next action depending on the player's hand.
     *
     * @return The next action.
     */
    public GameAction chooseAction() {
        return m_controller.chooseAction(m_hand);
    }
}
