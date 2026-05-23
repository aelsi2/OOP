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
import ru.nsu.aeliseev2.task212.utils.RemoteServer;
import ru.nsu.aeliseev2.task212.protocol.MessageReader;
import ru.nsu.aeliseev2.task212.protocol.MessageWriter;
import ru.nsu.aeliseev2.task212.protocol.ProtocolException;
import ru.nsu.aeliseev2.task212.protocol.messages.CancelMessage;
import ru.nsu.aeliseev2.task212.protocol.messages.Message;
import ru.nsu.aeliseev2.task212.protocol.messages.PingMessage;
import ru.nsu.aeliseev2.task212.protocol.messages.ResultMessage;
import ru.nsu.aeliseev2.task212.utils.WorkUnit;

class ServerConnection implements Closeable {
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

    public ServerConnection(RemoteServer server, Selector selector) throws IOException {
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
    }

    public boolean process() throws IOException, ProtocolException {
        if (!isReady) {
            if (key.isConnectable()) {
                channel.finishConnect();
                writer.enqueue(new PingMessage(handshakeId));
                isReady = true;
            } else {
                if (System.currentTimeMillis() - lastPing > HANDSHAKE_TIMEOUT) {
                    throw new ProtocolException("Handshake timeout");
                }
                return false;
            }
        }

        if (lastPing != 0) {
            long timeSinceLastPing = System.currentTimeMillis() - lastPing;
            if (timeSinceLastPing > STALE_TIMEOUT) {
                throw new ProtocolException("Connection timeout");
            } else if (!isReady && timeSinceLastPing > HANDSHAKE_TIMEOUT) {
                throw new ProtocolException("Handshake timeout");
            }else if (timeSinceLastPing > HEARTBEAT_TIMEOUT) {
                writer.enqueue(new PingMessage(handshakeId));
            }
        }

        key.interestOps((key.interestOps() & ~SelectionKey.OP_CONNECT) | SelectionKey.OP_READ);

        writer.write(sendBuffer);
        if (key.isWritable()) {
            sendBuffer.flip();
            channel.write(sendBuffer);
            sendBuffer.compact();
        }

        if (sendBuffer.hasRemaining() || writer.hasData()) {
            key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
        } else {
            key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);
        }

        boolean result = false;
        while (key.isReadable()) {
            int count = channel.read(receiveBuffer);
            if (count == -1) {
                throw new ProtocolException("Server has closed the connection");
            }
            if (count == 0) {
                break;
            }
            receiveBuffer.flip();
            Message message;
            while ((message = reader.read(receiveBuffer)) != null) {
                if (message instanceof PingMessage) {
                    PingMessage pingMessage = (PingMessage) message;
                    if (pingMessage.id() != handshakeId) {
                        throw new ProtocolException("Unexpected ping id");
                    }
                    isReady = true;
                    lastPing = System.currentTimeMillis();
                } else if (message instanceof ResultMessage) {
                    ResultMessage resultMessage = (ResultMessage) message;
                    if (!isReady) {
                        throw new ProtocolException("Got result before handshake");
                    }
                    if (workUnits.containsKey(resultMessage.id())) {
                        WorkUnit unit = workUnits.get(resultMessage.id());
                        unit.isComplete = true;
                        unit.hasPrimes = resultMessage.hasPrime();
                        workUnits.remove(unit.id);
                        result = true;
                    }
                }
            }
            receiveBuffer.compact();
        }
        return result;
    }

    public boolean isReady() {
        return isReady;
    }

    public void schedule(WorkUnit workUnit) {
        lastPing = System.currentTimeMillis();
        writer.enqueue(workUnit.toMessage());
    }

    public Collection<WorkUnit> unfinishedWorkUnits() {
        return workUnits.values();
    }

    public void cancel(long id) {
        writer.enqueue(new CancelMessage(id));
        workUnits.remove(id);
    }

    @Override
    public void close() throws IOException {
        key.cancel();
        channel.close();
    }
}
