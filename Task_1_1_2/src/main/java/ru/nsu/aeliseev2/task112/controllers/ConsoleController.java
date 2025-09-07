package ru.nsu.aeliseev2.task112.controllers;

import java.io.PrintStream;
import java.util.Scanner;
import ru.nsu.aeliseev2.task112.ConsoleUtils;
import ru.nsu.aeliseev2.task112.model.CardHand;

/**
 * A player controller taking inputs from a {@code Scanner}.
 */
public class ConsoleController implements PlayerController {
    private final Scanner m_scanner;
    private final PrintStream m_messageStream;

    /**
     * Creates a new console player controller.
     *
     * @param scanner     The scanner to take inputs from.
     * @param printStream The stream to print messages to.
     */
    public ConsoleController(Scanner scanner, PrintStream printStream) {
        m_scanner = scanner;
        m_messageStream = printStream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameAction chooseAction(CardHand hand) {
        int number = ConsoleUtils.inputNumber(m_scanner, m_messageStream,
            "Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...", 0, 1);
        return GameAction.fromOrdinal(number);
    }
}
