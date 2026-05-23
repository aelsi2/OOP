package ru.nsu.aeliseev2.task212.app.client;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import ru.nsu.aeliseev2.task212.utils.RemoteServer;
import ru.nsu.aeliseev2.task212.utils.WorkUnit;

public class PrimeClient implements Closeable {
    private static final long HEARTBEAT_TIMEOUT = 1000;

    private final Collection<RemoteServer> servers;
    private final Selector selector;
    private final ArrayList<ServerConnection> connections;

    private long jobId;

    public PrimeClient(Collection<RemoteServer> servers) throws IOException {
        this.servers = servers;
        this.selector = Selector.open();
        this.connections = new ArrayList<>();
        this.jobId = 0;
    }

    public void connect() throws IOException {
        for (RemoteServer server : servers) {
            try {
                ServerConnection connection = new ServerConnection(server, selector);
                connections.add(connection);
            } catch (IOException exception) {
                System.err.println("Connection failed: " + exception.getMessage());
            }
        }
        int readyCount = 0;
        while (readyCount < connections.size()) {
            selector.select(HEARTBEAT_TIMEOUT);

            Iterator<ServerConnection> iterator = connections.iterator();
            while (iterator.hasNext()) {
                ServerConnection connection = iterator.next();
                try {
                    connection.process();
                    if (connection.isReady()) {
                        readyCount += 1;
                    }
                } catch (Exception exception) {
                    iterator.remove();
                    System.err.println("Handshake failed: " + exception.getMessage());
                    try {
                        connection.close();
                    } catch (IOException ioException) {
                        System.err.println("Close failed");
                    }
                }
            }

        }
    }

    private void scheduleWorkUnits(Collection<WorkUnit> workUnits) {
        Iterator<ServerConnection> connectionIter = connections.iterator();
        Iterator<WorkUnit> unitIter = workUnits.iterator();
        while (unitIter.hasNext()) {
            WorkUnit unit = unitIter.next();
            if (connectionIter.hasNext()) {
                ServerConnection connection = connectionIter.next();
                connection.schedule(unit);
            } else {
                connectionIter = connections.iterator();
                if (!connectionIter.hasNext()) {
                    throw new RuntimeException("All servers are dead");
                }
                ServerConnection connection = connectionIter.next();
                connection.schedule(unit);
            }

        }
    }

    public boolean check(long[] numbers) throws IOException {
        if (connections.size() == 0) {
            throw new IllegalStateException("No active connections");
        }

        final int numCons = connections.size();
        final int numPerConn = numbers.length / numCons;
        final int numExtra = numbers.length % numCons;

        HashMap<Long, WorkUnit> workUnits = new HashMap<>();
        for (int connIndex = 0; connIndex < numCons; connIndex++) {
            int numStart = numPerConn * connIndex;
            int numEnd = numStart + numPerConn + (connIndex == numCons - 1 ? numExtra : 0);
            WorkUnit unit = new WorkUnit(jobId++, numbers, numStart, numEnd);
            workUnits.put(unit.id, unit);
        }
        scheduleWorkUnits(workUnits.values());

        while (true) {
            selector.select(HEARTBEAT_TIMEOUT);
            Iterator<ServerConnection> iterator = connections.iterator();
            while (iterator.hasNext()) {
                ServerConnection connection = iterator.next();
                try {
                    if (!connection.process()) {
                        continue;
                    }

                    boolean allFalse = true;
                    for (WorkUnit unit : workUnits.values()) {
                        if (unit.hasPrimes) {
                            return true;
                        }
                        if (!unit.isComplete) {
                            allFalse = false;
                            break;
                        }
                    }
                    if (allFalse) {
                        return false;
                    }

                } catch (Exception exception) {
                    iterator.remove();
                    System.err.println("Connection error: " + exception.getMessage());
                    try {
                        connection.close();
                    } catch (IOException ioException) {
                        System.err.println("Close failed");
                    }
                    scheduleWorkUnits(connection.unfinishedWorkUnits());
                }
            }
        }

    }

    @Override
    public void close() throws IOException {
        selector.close();
        for (ServerConnection connection : connections) {
            connection.close();
        }
    }
}
