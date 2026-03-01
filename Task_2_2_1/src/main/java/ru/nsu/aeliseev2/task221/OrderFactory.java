package ru.nsu.aeliseev2.task221;

/**
 * A strategy for producing orders.
 */
public interface OrderFactory {
    /**
     * Creates a new order.
     *
     * @return The created order, or {@code null} if there are no more orders to create.
     */
    Order makeOrder();
}
