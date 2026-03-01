package ru.nsu.aeliseev2.task221;

import java.io.PrintStream;

/**
 * An implementation of {@code OrderLogger} that prints status messages to a {@code PrintStream}.
 */
public class PrintStreamOrderLogger implements OrderLogger {
    private final PrintStream printStream;

    /**
     * Creates a new instance of {@code PrintStreamOrderLogger}.
     *
     * @param printStream The {@code PrintStream} to print log messages to.
     */
    public PrintStreamOrderLogger(PrintStream printStream) {
        this.printStream = printStream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logCreated(Order order) {
        printStream.printf("[%d] created\n", order.id());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logCooking(Order order) {
        printStream.printf("[%d] cooking\n", order.id());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logCooked(Order order) {
        printStream.printf("[%d] cooked\n", order.id());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logDelivering(Order order) {
        printStream.printf("[%d] delivering\n", order.id());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logDelivered(Order order) {
        printStream.printf("[%d] delivered\n", order.id());
    }
}
