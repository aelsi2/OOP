package ru.nsu.aeliseev2.task112;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Console utilities.
 */
public final class ConsoleUtils {
    private ConsoleUtils() {
    }

    /**
     * Prints a message and waits until the user presses Enter.
     *
     * @param messageStream The stream to print the message to.
     * @param scanner The scanner to read the line with.
     */
    public static void pause(PrintStream messageStream, Scanner scanner) {
        messageStream.println("Нажмите Enter, чтобы продолжить.");
        scanner.nextLine();
    }

    /**
     * Prompts and reads a number from the console.
     *
     * @param scanner The scanner to read the number with.
     * @param messageStream The stream to print the message to.
     * @param message The prompt message.
     * @param min The min allowed value.
     * @param max The max allowed value.
     * @return The read number.
     */
    public static int inputNumber(Scanner scanner, PrintStream messageStream,
                                 String message, int min, int max) {
        while (true) {
            messageStream.printf("%s (%d-%d)\n", message, min, max);
            try {
                String line = scanner.nextLine();
                int number = Integer.parseInt(line);
                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException exception) {
                // Just ask again.
            }
            messageStream.println("Неправильное число. Повторите попытку.");
        }
    }
}
