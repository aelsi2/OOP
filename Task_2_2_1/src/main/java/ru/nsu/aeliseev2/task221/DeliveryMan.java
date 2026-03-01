package ru.nsu.aeliseev2.task221;

import java.util.ArrayList;

/**
 * Represents a person delivering processed orders.
 */
public class DeliveryMan implements Runnable {
    private final OrderQueue queue;
    private final OrderLogger logger;
    private final WorkingStrategy workingStrategy;
    private final int capacity;

    private final ArrayList<Order> orders;

    /**
     * Creates a new instance of {@code DeliveryMan}.
     *
     * @param queue           The queue to take to-deliver orders from.
     * @param capacity        The maximum number of orders that can be delivered simultaneously.
     * @param logger          The logger to log order status updates with.
     * @param workingStrategy The strategy for simulating work.
     */
    public DeliveryMan(OrderQueue queue, int capacity,
                       OrderLogger logger, WorkingStrategy workingStrategy) {
        this.queue = queue;
        this.logger = logger;
        this.workingStrategy = workingStrategy;
        this.capacity = capacity;
        this.orders = new ArrayList<>(capacity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            while (true) {
                while (orders.size() < capacity) {
                    Order order = queue.take();
                    if (order == null) {
                        break;
                    }
                    orders.add(order);
                }
                if (orders.isEmpty()) {
                    break;
                }
                for (Order order : orders) {
                    logger.logDelivering(order);
                }
                workingStrategy.doWork();
                for (Order order : orders) {
                    logger.logDelivered(order);
                }
                orders.clear();
            }
        } catch (InterruptedException e) {
            // We've been interrupted. Nothing we can do.
        }
    }
}
