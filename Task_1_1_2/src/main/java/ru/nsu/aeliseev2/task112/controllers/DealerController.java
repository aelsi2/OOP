package ru.nsu.aeliseev2.task112.controllers;

import ru.nsu.aeliseev2.task112.model.CardHand;

/**
 * The AI dealer's controller. Always hits when the value of their hand is below 17 and stands
 * otherwise.
 */
public final class DealerController implements PlayerController {
    private DealerController() {
    }

    /**
     * The default instance of the controller.
     */
    public static final DealerController INSTANCE = new DealerController();

    /**
     * {@inheritDoc}
     */
    @Override
    public GameAction chooseAction(CardHand hand) {
        if (hand.getValue() >= 17) {
            return GameAction.STAND;
        }
        return GameAction.HIT;
    }
}
