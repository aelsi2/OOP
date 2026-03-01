package ru.nsu.aeliseev2.task221;

/**
 * Represents a person processing them and handing the results over to delivery.
 */
public class Baker implements Runnable {
    private final OrderQueue inQueue;
    private final OrderQueue outQueue;
    private final OrderLogger logger;
    private final WorkingStrategy workingStrategy;

    /**
     * Creates a new instance of {@code Baker}.
     *
     * @param inQueue         The input to-cook queue.
     * @param outQueue        The output to-deliver queue.
     * @param logger          The logger to log order status updates with.
     * @param workingStrategy The strategy for simulating work.
     */
    public Baker(OrderQueue inQueue, OrderQueue outQueue,
                 OrderLogger logger, WorkingStrategy workingStrategy) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.logger = logger;
        this.workingStrategy = workingStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        outQueue.putBegin();
        try {
            while (true) {
                Order order = inQueue.take();
                if (order == null) {
                    break;
                }
                logger.logCooking(order);
                workingStrategy.doWork();
                logger.logCooked(order);
                outQueue.put(order);
            }
        } catch (InterruptedException e) {
            // We've been interrupted. Nothing we can do.
        } finally {
            outQueue.putEnd();
        }
    }
}
