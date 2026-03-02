package ru.aeliseev2.task221;

import ru.nsu.aeliseev2.task221.WorkingStrategy;

class DummyWorkingStrategy implements WorkingStrategy {
    public static final DummyWorkingStrategy INSTANCE = new DummyWorkingStrategy();

    @Override
    public void doWork() throws InterruptedException {
    }
}
