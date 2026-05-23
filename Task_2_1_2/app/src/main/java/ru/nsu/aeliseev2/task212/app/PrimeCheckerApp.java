package ru.nsu.aeliseev2.task212.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import ru.nsu.aeliseev2.task212.algorithms.ParallelStreamPrimeChecker;
import ru.nsu.aeliseev2.task212.algorithms.PrimeChecker;
import ru.nsu.aeliseev2.task212.app.client.PrimeClient;
import ru.nsu.aeliseev2.task212.app.server.PrimeServer;
import ru.nsu.aeliseev2.task212.utils.Port;

public class PrimeCheckerApp {
    private static void printHelp() {
        System.err.println("""
            Usage:
            prime-checker listen <PORT>
            prime-checker check <HOST:PORT>, <HOST:PORT>, <HOST:PORT>, ...
            """);
    }

    private static int runListen(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("A port number needs to be specified");
        }
        int port = Port.parse(args[1]);
        PrimeChecker algorithm = new ParallelStreamPrimeChecker();
        try (PrimeServer server = new PrimeServer(port, algorithm)) {
            server.listen();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private static int runCheck(String[] args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException("At least one host needs to be provided.");
        }
        ArrayList<ru.nsu.aeliseev2.task212.utils.RemoteServer> servers = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            servers.add(ru.nsu.aeliseev2.task212.utils.RemoteServer.parse(args[i]));
        }
        PrimeClient client = new PrimeClient(servers);

        int numCount = 0;
        long[] array = new long[4];

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLong()) {
            array[numCount++] = scanner.nextLong();
            if (numCount == array.length) {
                long[] newArray = new long[args.length * 2];
                System.arraycopy(array, 0, newArray, 0, array.length);
                array = newArray;
            }
        }

        long[] numbers = new long[numCount];
        System.arraycopy(array, 0, numbers, 0, numCount);

        client.connect();
        System.out.println(client.check(numbers));
        return 0;
    }

    private static int run(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("An action needs to be specified.");
        }
        if (args[0].equalsIgnoreCase("listen")) {
            return runListen(args);
        } else if (args[0].equalsIgnoreCase("check")) {
            return runCheck(args);
        } else {
            throw new IllegalArgumentException("Unknown action: " + args[0]);
        }
    }

    public static void main(String[] args) {
        try {
            System.exit(run(args));
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
            printHelp();
            System.exit(1);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }
}
