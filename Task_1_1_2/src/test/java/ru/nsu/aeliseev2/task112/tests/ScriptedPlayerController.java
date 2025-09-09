package ru.nsu.aeliseev2.task112.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.nsu.aeliseev2.task112.controllers.GameAction;
import ru.nsu.aeliseev2.task112.controllers.PlayerController;
import ru.nsu.aeliseev2.task112.model.CardHand;

class ScriptedPlayerController implements PlayerController {
    private final List<GameAction> actions;

    public ScriptedPlayerController(GameAction... actions) {
        this.actions = new ArrayList<>();
        this.actions.addAll(Arrays.asList(actions));
    }

    @Override
    public GameAction chooseAction(CardHand hand) {
        return actions.remove(0);
    }
}
