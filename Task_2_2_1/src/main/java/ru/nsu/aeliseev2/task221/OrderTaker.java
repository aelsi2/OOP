package ru.nsu.aeliseev2.task221;

/**
 * Represents a person receiving orders from clients and putting them in the to-cook queue.
 */
public class OrderTaker implements Runnable {
    private final OrderFactory factory;
    private final OrderQueue queue;
    private final OrderLogger logger;
    private final WorkingStrategy workingStrategy;

    /**
     * Creates a new instance of {@code OrderTaker}.
     *
     * @param factory         The factory to create orders with.
     * @param queue           The to-cook queue to put orders into.
     * @param logger          The logger to log order status updates with.
     * @param workingStrategy The strategy for simulating work.
     */
    public OrderTaker(OrderFactory factory, OrderQueue queue,
                      OrderLogger logger, WorkingStrategy workingStrategy) {
        this.factory = factory;
        this.queue = queue;
        this.logger = logger;
        this.workingStrategy = workingStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        this.queue.putBegin();
        try {
            while (true) {
                Order order = factory.makeOrder();
                if (order == null) {
                    break;
                }
                logger.logCreated(order);
                queue.put(order);
                workingStrategy.doWork();
            }
        } catch (InterruptedException e) {
            // We've been interrupted. Nothing we can do.
        } finally {
            this.queue.putEnd();
        }
    }
}
