package ru.aeliseev2.task221;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ru.nsu.aeliseev2.task221.Order;
import ru.nsu.aeliseev2.task221.OrderFactory;

class TestOrderFactory implements OrderFactory {
    private final List<Order> orders;

    public TestOrderFactory(Collection<Order> orders) {
        this.orders = new ArrayList<>(orders);
    }

    @Override
    public synchronized Order makeOrder() {
        if (orders.isEmpty()) {
            return null;
        }
        //noinspection SequencedCollectionMethodCanBeUsed
        return orders.remove(0);
    }
}
