package ru.nsu.aeliseev2.task212.app.server;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;
import ru.nsu.aeliseev2.task212.algorithms.PrimeChecker;
import ru.nsu.aeliseev2.task212.protocol.MessageReader;
import ru.nsu.aeliseev2.task212.protocol.MessageWriter;
import ru.nsu.aeliseev2.task212.protocol.ProtocolException;
import ru.nsu.aeliseev2.task212.protocol.messages.Message;
import ru.nsu.aeliseev2.task212.protocol.messages.PingMessage;
import ru.nsu.aeliseev2.task212.protocol.messages.ResultMessage;
import ru.nsu.aeliseev2.task212.protocol.messages.WorkMessage;

/**
 * A connection to a single client on a server.
 */
public class ServerConnection implements Closeable {
    private static final int BUFFER_SIZE = 1024;

    private final SocketChannel channel;
    private final Selector selector;
    private final SelectionKey key;
    private final PrimeChecker algorithm;

    private final ByteBuffer sendBuffer;
    private final ByteBuffer receiveBuffer;
    private final MessageWriter writer;
    private final MessageReader reader;

    /**
     * Initializes a new instance of {@code ServerConnection}.
     *
     * @param channel   The channel to use to communicate with the client.
     * @param selector  The selector to register the channel in.
     * @param algorithm The algorithm to use to find composite numbers.
     * @throws IOException Channel configuration error.
     */
    public ServerConnection(
        SocketChannel channel, Selector selector, PrimeChecker algorithm
    ) throws IOException {
        channel.configureBlocking(false);
        this.channel = channel;
        this.selector = selector;
        this.key = channel.register(selector, SelectionKey.OP_READ);
        this.algorithm = algorithm;

        this.sendBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.receiveBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.writer = new MessageWriter();
        this.reader = new MessageReader(List.of(
            PingMessage.Deserializer.INSTANCE,
            new WorkMessage.Deserializer()
        ));
    }

    /**
     * Schedules a work unit on a separate thread.
     *
     * @param workMessage The work unit (in form of a message) to schedule.
     */
    private void scheduleWork(WorkMessage workMessage) {
        new Thread(() -> {
            boolean result = false;
            try {
                result = algorithm.hasComposites(
                    workMessage.data(),
                    workMessage.startIndex(),
                    workMessage.endIndex()
                );
            } catch (InterruptedException e) {
                System.err.println("Work thread interrupted");
            }
            synchronized (writer) {
                writer.enqueue(
                    new ResultMessage(workMessage.id(), result)
                );
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                selector.wakeup();
            }
        }).start();
    }

    /**
     * Handles read/write operations on the connection.
     *
     * @return Whether the connection should be closed.
     * @throws IOException       Read/write error.
     * @throws ProtocolException Client protocol violation.
     */
    public boolean handleOps() throws IOException, ProtocolException {
        synchronized (writer) {
            writer.write(sendBuffer);
            if (key.isWritable()) {
                sendBuffer.flip();
                channel.write(sendBuffer);
                sendBuffer.compact();
            }
        }

        while (key.isReadable()) {
            int count = channel.read(receiveBuffer);
            if (count == -1) {
                return true;
            }
            if (count == 0) {
                break;
            }
            receiveBuffer.flip();
            Message message;
            while ((message = reader.read(receiveBuffer)) != null) {
                if (message instanceof PingMessage) {
                    synchronized (writer) {
                        writer.enqueue(message);
                    }
                } else if (message instanceof WorkMessage) {
                    scheduleWork((WorkMessage) message);
                }
            }
            receiveBuffer.compact();
        }

        synchronized (writer) {
            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
            if (sendBuffer.position() != 0 || writer.hasData()) {
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
            } else {
                key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
            }
        }

        return false;
    }

    /**
     * Gets the {@code SelectionKey} for this connection.
     *
     * @return The {@code SelectionKey}.
     */
    public SelectionKey key() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        key.cancel();
        channel.close();
    }
}
