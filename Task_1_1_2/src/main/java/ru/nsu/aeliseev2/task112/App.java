package ru.nsu.aeliseev2.task112;

import java.util.Scanner;
import ru.nsu.aeliseev2.task112.model.CardPool;

@NoCoverageGenerated(reason = "Contains RNG and not much game logic.")
final class App {
    private App() {
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var messageStream = System.out;

        messageStream.println("Добро пожаловать в Блэкджек!");
        int deckCount = ConsoleUtils.inputNumber(scanner, messageStream,
            "Сколько колод по 52 карты?", 1, 8);
        int roundCount = ConsoleUtils.inputNumber(scanner, messageStream,
            "Сколько раундов?", 1, 15);
        messageStream.println();

        new Game(messageStream, scanner, () -> new CardPool(deckCount)).run(roundCount);
    }
}
