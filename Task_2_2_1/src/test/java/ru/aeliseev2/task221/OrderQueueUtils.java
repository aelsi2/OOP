package ru.aeliseev2.task221;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ru.nsu.aeliseev2.task221.Order;
import ru.nsu.aeliseev2.task221.OrderQueue;

final class OrderQueueUtils {
    private OrderQueueUtils() {
    }

    public static List<Order> getOrders(OrderQueue queue) throws InterruptedException {
        ArrayList<Order> orders = new ArrayList<>();
        while (true) {
            Order order = queue.take();
            if (order == null) {
                break;
            }
            orders.add(order);
        }
        return orders;
    }

    public static OrderQueue createQueue(Collection<Order> orders) {
        var queue = new OrderQueue(orders.size());
        queue.putBegin();
        try {
            for (var order : orders) {
                queue.put(order);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            queue.putEnd();
        }
        return queue;
    }

    public static OrderQueue createDelayedQueue(
        int delayMillis, Collection<Order> orders
    ) {
        var queue = new OrderQueue(orders.size());
        queue.putBegin();
        new Thread(() -> {
            try {
                Thread.sleep(delayMillis);
                for (var order : orders) {
                    queue.put(order);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                queue.putEnd();
            }
        }).start();
        return queue;
    }
}
