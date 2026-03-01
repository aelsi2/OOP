package ru.nsu.aeliseev2.task221;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a restaurant with employees. Each employee is simulated on a separate thread.
 */
public class Restaurant implements Runnable {
    private final ArrayList<Runnable> employees;

    /**
     * Creates a new instance of {@code Restaurant}.
     *
     * @param employees The collection of employees working at the restaurant.
     */
    public Restaurant(Collection<Runnable> employees) {
        this.employees = new ArrayList<>(employees);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        var threads = employees.stream().map(employee -> {
            Thread thread = new Thread(employee);
            thread.start();
            return thread;
        }).toList();

        boolean interrupted = false;
        for (var thread : threads) {
            if (interrupted) {
                thread.interrupt();
                continue;
            }
            try {
                thread.join();
            } catch (InterruptedException e) {
                interrupted = true;
                thread.interrupt();
            }
        }
    }
}
