package ru.nsu.aeliseev2.task112.tests;

import java.io.OutputStream;

class DummyOutputStream extends OutputStream {
    public static final DummyOutputStream INSTANCE = new DummyOutputStream();

    @Override
    public void write(int i) {
    }
}
