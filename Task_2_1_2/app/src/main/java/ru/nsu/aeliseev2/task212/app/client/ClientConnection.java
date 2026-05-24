package ru.nsu.aeliseev2.task212.app.client;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import ru.nsu.aeliseev2.task212.protocol.MessageReader;
import ru.nsu.aeliseev2.task212.protocol.MessageWriter;
import ru.nsu.aeliseev2.task212.protocol.ProtocolException;
import ru.nsu.aeliseev2.task212.protocol.messages.Message;
import ru.nsu.aeliseev2.task212.protocol.messages.PingMessage;
import ru.nsu.aeliseev2.task212.protocol.messages.ResultMessage;
import ru.nsu.aeliseev2.task212.utils.RemoteServer;
import ru.nsu.aeliseev2.task212.utils.WorkStatus;
import ru.nsu.aeliseev2.task212.utils.WorkUnit;

/**
 * A connection to a single server on the client side.
 */
class ClientConnection implements Closeable {
    private static final long HEARTBEAT_TIMEOUT = 1000;
    private static final long HANDSHAKE_TIMEOUT = 3000;
    private static final long STALE_TIMEOUT = 10000;
    private static final int BUFFER_SIZE = 1024;

    private static final List<Message.Deserializer> DESERIALIZERS = List.of(
        ResultMessage.Deserializer.INSTANCE,
        PingMessage.Deserializer.INSTANCE
    );

    private final SocketChannel channel;
    private final SelectionKey key;

    private final ByteBuffer sendBuffer;
    private final ByteBuffer receiveBuffer;
    private final MessageWriter writer;
    private final MessageReader reader;

    private final HashMap<Long, WorkUnit> workUnits;
    private final long handshakeId;
    private boolean isReady;
    private long lastPing;
    private long lastAck;

    /**
     * Initializes a new instance of {@code ClientConnection}.
     *
     * @param server   The server to connect to.
     * @param selector The selector to register the socket with.
     * @throws IOException Connection failed.
     */
    public ClientConnection(RemoteServer server, Selector selector) throws IOException {
        SocketChannel channel = null;
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(server.getAddress());
            this.key = channel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException exception) {
            if (channel != null) {
                channel.close();
            }
            throw exception;
        }
        this.channel = channel;
        this.sendBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.receiveBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.writer = new MessageWriter();
        this.reader = new MessageReader(DESERIALIZERS);
        this.handshakeId = ThreadLocalRandom.current().nextLong();
        this.workUnits = new HashMap<>();
        this.isReady = false;
        this.lastPing = System.currentTimeMillis();
        this.lastAck = this.lastPing;
        System.err.println("Connected to client: " + handshakeId);
    }

    /**
     * Handles messages received from the server.
     *
     * @return {@code true} if the result of the calculation may be known early after this call,
     *     {@code false} otherwise.
     * @throws ProtocolException Server protocol violation.
     */
    private boolean handleMessages() throws ProtocolException {
        boolean receivedResults = false;
        Message message;
        while ((message = reader.read(receiveBuffer)) != null) {
            if (message instanceof PingMessage) {
                PingMessage pingMessage = (PingMessage) message;
                if (pingMessage.id() != handshakeId) {
                    throw new ProtocolException("Unexpected ping id: " + pingMessage.id());
                }
                isReady = true;
                lastAck = System.currentTimeMillis();
            } else if (message instanceof ResultMessage) {
                ResultMessage resultMessage = (ResultMessage) message;
                if (!isReady) {
                    throw new ProtocolException("Got result before handshake");
                }
                if (workUnits.containsKey(resultMessage.id())) {
                    WorkUnit unit = workUnits.get(resultMessage.id());
                    if (resultMessage.hasComposites()) {
                        unit.status = WorkStatus.HAS_COMPOSITES;
                    } else {
                        unit.status = WorkStatus.ALL_PRIMES;
                    }
                    workUnits.remove(unit.id);
                    receivedResults = true;
                }
            }
        }
        return receivedResults;
    }

    /**
     * Handles read/write operations for this connection. Should be called in a loop with
     * {@code Selector.select()}.
     *
     * @return {@code true} if the result of the calculation may be known early after this call,
     *     {@code false} otherwise.
     * @throws IOException       Read/write error.
     * @throws ProtocolException Protocol violation.
     */
    public boolean handleOps() throws IOException, ProtocolException {
        if (!isReady && key.isConnectable()) {
            channel.finishConnect();
            writer.enqueue(new PingMessage(handshakeId));
        }

        writer.write(sendBuffer);
        if (key.isWritable()) {
            sendBuffer.flip();
            channel.write(sendBuffer);
            sendBuffer.compact();
        }

        boolean receivedResults = false;
        while (key.isReadable()) {
            int count = channel.read(receiveBuffer);
            if (count == -1) {
                throw new ProtocolException("Server has closed the connection");
            }
            if (count == 0) {
                break;
            }
            receiveBuffer.flip();
            receivedResults = handleMessages();
            receiveBuffer.compact();
        }

        if (channel.isConnected()) {
            key.interestOps(SelectionKey.OP_READ);
            if (sendBuffer.position() != 0 || writer.hasData()) {
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
            } else {
                key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
            }
        }

        return receivedResults;
    }

    /**
     * Checks and handles timeouts on this connection, sending ping messages if needed.
     *
     * @return {@code true} if this connection has timed out and need to be closed, {@code false}
     *     otherwise.
     */
    public boolean handleTimeouts() {
        final long time = System.currentTimeMillis();
        if (time - lastAck > STALE_TIMEOUT) {
            return true;
        } else if (!isReady && time - lastAck > HANDSHAKE_TIMEOUT) {
            return true;
        } else if (time - lastPing > HEARTBEAT_TIMEOUT) {
            writer.enqueue(new PingMessage(handshakeId));
            lastPing = time;
            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
        }
        return false;
    }

    /**
     * Gets the key that corresponds to this connection.
     *
     * @return The selection key for this connection.
     */
    public SelectionKey key() {
        return key;
    }

    /**
     * Checks if this connection is ready to receive work.
     *
     * @return Whether this connection is ready.
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * Schedules a work unit on this connection.
     *
     * @param workUnit The work unit to schedule.
     */
    public void schedule(WorkUnit workUnit) {
        lastPing = System.currentTimeMillis();
        lastAck = lastPing;
        writer.enqueue(workUnit.toMessage());
        workUnits.put(workUnit.id, workUnit);
        key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
    }

    /**
     * Gets the collection of work units scheduled on this connection.
     *
     * @return The work units that have been scheduled, but have not been completed.
     */
    public Collection<WorkUnit> scheduledWorkUnits() {
        return workUnits.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        key.cancel();
        try {
            channel.close();
        } catch (IOException exception) {
            System.err.println("Channel close failed: " + exception.getMessage());
        }
    }
}
