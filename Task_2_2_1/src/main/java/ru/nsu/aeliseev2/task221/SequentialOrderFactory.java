package ru.nsu.aeliseev2.task221;

/**
 * An implementation of {@code OrderFactory} that produces a limited amount of orders with
 * sequential IDs.
 */
public class SequentialOrderFactory implements OrderFactory {
    private final int count;
    private int id;

    /**
     * Creates a new instance of {@code SequentialOrderFactory}.
     *
     * @param count The total number of orders this factory can create.
     */
    public SequentialOrderFactory(int count) {
        this.count = count;
        this.id = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Order makeOrder() {
        if (id >= count) {
            return null;
        }
        return new Order(id++);
    }
}
