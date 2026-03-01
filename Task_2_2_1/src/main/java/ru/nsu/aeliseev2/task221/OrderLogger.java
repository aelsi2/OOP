package ru.nsu.aeliseev2.task221;

/**
 * An abstract logger for order states.
 */
public interface OrderLogger {
    /**
     * Logs when an order has just been created.
     *
     * @param order The order.
     */
    void logCreated(Order order);

    /**
     * Logs when an order has started being cooked.
     *
     * @param order The order.
     */
    void logCooking(Order order);

    /**
     * Logs when an order has been cooked.
     *
     * @param order The order.
     */
    void logCooked(Order order);

    /**
     * Logs when an order has been picked up by delivery.
     *
     * @param order The order.
     */
    void logDelivering(Order order);

    /**
     * Logs when an order has been delivered.
     *
     * @param order The order.
     */
    void logDelivered(Order order);
}
