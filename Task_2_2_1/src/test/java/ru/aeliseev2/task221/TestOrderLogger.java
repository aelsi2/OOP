package ru.aeliseev2.task221;

import ru.nsu.aeliseev2.task221.Order;
import ru.nsu.aeliseev2.task221.OrderLogger;

class TestOrderLogger implements OrderLogger {
    public int created = 0;
    public int cooking = 0;
    public int cooked = 0;
    public int delivering = 0;
    public int delivered = 0;

    @Override
    public void logCreated(Order order) {
        created++;
    }

    @Override
    public void logCooking(Order order) {
        cooking++;
    }

    @Override
    public void logCooked(Order order) {
        cooked++;
    }

    @Override
    public void logDelivering(Order order) {
        delivering++;
    }

    @Override
    public void logDelivered(Order order) {
        delivered++;
    }
}
