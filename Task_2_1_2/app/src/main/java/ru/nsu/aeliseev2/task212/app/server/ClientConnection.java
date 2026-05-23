package ru.nsu.aeliseev2.task212.app.server;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;
import ru.nsu.aeliseev2.task212.protocol.MessageReader;
import ru.nsu.aeliseev2.task212.protocol.MessageWriter;
import ru.nsu.aeliseev2.task212.protocol.messages.CancelMessage;
import ru.nsu.aeliseev2.task212.protocol.messages.PingMessage;
import ru.nsu.aeliseev2.task212.protocol.messages.WorkMessage;

public class ClientConnection implements Closeable {
    private static final int BUF_SIZE = 1024;

    private final SocketChannel channel;
    private final Selector selector;

    private final ByteBuffer sendBuffer;
    private final ByteBuffer receiveBuffer;
    private final MessageWriter writer;
    private final MessageReader reader;

    public ClientConnection(SocketChannel channel, Selector selector) throws IOException {
        this.channel = channel;
        this.selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);

        this.sendBuffer = ByteBuffer.allocate(BUF_SIZE);
        this.receiveBuffer = ByteBuffer.allocate(BUF_SIZE);
        this.writer = new MessageWriter();
        this.reader = new MessageReader(List.of(
            PingMessage.Deserializer.INSTANCE,
            CancelMessage.Deserializer.INSTANCE,
            new WorkMessage.Deserializer()
        ));
    }


    @Override
    public void close() throws IOException {
        selector.close();
        channel.close();
    }
}
