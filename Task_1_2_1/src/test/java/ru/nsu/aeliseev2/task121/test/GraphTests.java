package ru.nsu.aeliseev2.task121.test;

import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task121.Edge;
import ru.nsu.aeliseev2.task121.Graph;

abstract class GraphTests {
    abstract Graph<String> createGraph();

    @Test
    public void read() {
        var input = """
            v1
            v2
            
            v1 -> v2
            v3 -> v1
            v3
            v2 -> v2
            v2 -> v2
            
            v4
            v5
            """;
        var scanner = new Scanner(input);
        var graph = createGraph();
        graph.read(scanner, v -> v);

        Assertions.assertAll(
            () -> Assertions.assertEquals(5, graph.vertices().size()),
            () -> Assertions.assertTrue(graph.vertices().contains("v1"),
                "v1 not in graph"),
            () -> Assertions.assertTrue(graph.vertices().contains("v2"),
                "v2 not in graph"),
            () -> Assertions.assertTrue(graph.vertices().contains("v3"),
                "v3 not in graph"),
            () -> Assertions.assertTrue(graph.vertices().contains("v4"),
                "v4 not in graph"),
            () -> Assertions.assertTrue(graph.vertices().contains("v5"),
                "v5 not in graph"),
            () -> Assertions.assertFalse(graph.vertices().contains(null),
                "null vertex is not supposed to be in graph"),
            () -> Assertions.assertEquals(3, graph.edges().size()),
            () -> Assertions.assertTrue(graph.edges().contains(new Edge<>("v1", "v2")),
                "(v1, v2) not in graph"),
            () -> Assertions.assertTrue(graph.edges().contains(new Edge<>("v2", "v2")),
                "(v2, v2) not in graph"),
            () -> Assertions.assertTrue(graph.edges().contains(new Edge<>("v3", "v1")),
                "(v3, v1) not in graph"),
            () -> Assertions.assertFalse(graph.edges().contains(new Edge<>("v2", "v1")),
                "(v2, v1) is not supposed to be in graph"),
            () -> Assertions.assertFalse(graph.edges().contains(new Edge<>("v1", "v1")),
                "(v1, v1) is not supposed to be in graph")
        );
    }
}
