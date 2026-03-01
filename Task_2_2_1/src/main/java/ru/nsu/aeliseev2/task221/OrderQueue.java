package ru.nsu.aeliseev2.task221;

import java.util.ArrayList;

/**
 * A thread-safe {@code Order} queue.
 */
public class OrderQueue {
    private final int capacity;
    private final ArrayList<Order> list;

    private int activeProducers;

    /**
     * Creates a new instance of {@code OrderQueue}.
     *
     * @param capacity The capacity of the queue (-1 for unlimited capacity).
     */
    public OrderQueue(int capacity) {
        this.capacity = capacity;
        this.list = new ArrayList<>(capacity);
        this.activeProducers = 0;
    }

    /**
     * Puts an order into the queue, blocking until the queue has the required capacity.
     * {@code putBegin} must be called once before calling {@code put} as many times as needed,
     * {@code putEnd} must be called once afterward.
     *
     * @param order The order to put into the queue.
     * @throws InterruptedException  The current thread was interrupted.
     * @throws IllegalStateException This call was not preceded by a call to {@code putBegin}.
     */
    public synchronized void put(Order order) throws InterruptedException {
        if (activeProducers == 0) {
            throw new IllegalStateException("Tried to put an order into the queue without"
                + " calling putBegin");
        }
        while (capacity != -1 && list.size() >= capacity) {
            wait();
        }
        list.add(order);
        notify();
    }

    /**
     * Takes an order from the queue, blocking until there are orders available.
     *
     * @return The order taken from the queue or {@code null} if the queue has ended and there are
     *     no more orders to take.
     * @throws InterruptedException The current thread was interrupted.
     */
    public synchronized Order take() throws InterruptedException {
        while (list.isEmpty() && activeProducers != 0) {
            wait();
        }
        if (list.isEmpty()) {
            return null;
        }
        //noinspection SequencedCollectionMethodCanBeUsed
        final Order order = list.remove(0);
        notify();
        return order;
    }

    /**
     * Registers an active producer in this queue. Each call to {@code putBegin} must be succeeded
     * by a call to {@code putEnd}.
     */
    public synchronized void putBegin() {
        this.activeProducers++;
    }

    /**
     * Un-registers an active producer from this queue. Each call to {@code putEnd} must be preceded
     * by a call to {@code putBegin}.
     *
     * @throws IllegalStateException This call did not have a corresponding call to
     *                               {@code putBegin}.
     */
    public synchronized void putEnd() {
        if (activeProducers == 0) {
            throw new IllegalStateException("Called putEnd without calling putBegin first");
        }
        this.activeProducers--;
        this.notifyAll();
    }
}
