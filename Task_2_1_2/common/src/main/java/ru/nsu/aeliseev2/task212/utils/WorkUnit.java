package ru.nsu.aeliseev2.task212.utils;

import ru.nsu.aeliseev2.task212.protocol.messages.WorkMessage;

public class WorkUnit {
    public final long id;
    public final long[] data;
    public final int startIndex;
    public final int endIndex;
    public boolean hasPrimes;
    public boolean isComplete;

    public WorkUnit(long id, long[] data, int startIndex, int endIndex) {
        this.id = id;
        this.data = data;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.hasPrimes = false;
        this.isComplete = false;
    }

    public WorkMessage toMessage() {
        return new WorkMessage(id, data, startIndex, endIndex);
    }
}
