package ru.nsu.aeliseev2.task112.controllers;

import ru.nsu.aeliseev2.task112.model.CardHand;

/**
 * A controller making decisions for a player depending on their hand.
 */
public interface PlayerController {
    /**
     * Chooses the next action depending on the player's hand.
     *
     * @param hand The player's hand.
     * @return the next action.
     */
    GameAction chooseAction(CardHand hand);
}
