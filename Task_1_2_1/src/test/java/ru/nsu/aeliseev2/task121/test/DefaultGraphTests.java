package ru.nsu.aeliseev2.task121.test;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task121.Edge;
import ru.nsu.aeliseev2.task121.Graph;

class DefaultGraphTests {

    @Test
    public void defaultGetAdjacent() {
        var vertices = Set.of("v1", "v2", "v3", "v4", "v5");
        var edges = Set.of(
            new Edge<>("v1", "v2"),
            new Edge<>("v1", "v1"),
            new Edge<>("v1", "v3"),
            new Edge<>("v4", "v5"),
            new Edge<>("v5", "v1"),
            new Edge<>("v3", "v1")
        );
        var graph = new Graph<String>() {
            @Override
            public Set<String> vertices() {
                return vertices;
            }

            @Override
            public Set<Edge<String>> edges() {
                return edges;
            }
        };
        var actual = new HashSet<>(graph.getAdjacent("v1"));
        var expected = Set.of("v1", "v2", "v3");
        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void defaultHasAdjacent() {
        var vertices = Set.of("v1", "v2", "v3", "v4", "v5");
        var edges = Set.of(
            new Edge<>("v1", "v2"),
            new Edge<>("v3", "v2"),
            new Edge<>("v5", "v5")
        );
        var graph = new Graph<String>() {
            @Override
            public Set<String> vertices() {
                return vertices;
            }

            @Override
            public Set<Edge<String>> edges() {
                return edges;
            }
        };
        Assertions.assertAll(
            () -> Assertions.assertTrue(graph.hasAdjacent("v1")),
            () -> Assertions.assertFalse(graph.hasAdjacent("v2")),
            () -> Assertions.assertTrue(graph.hasAdjacent("v3")),
            () -> Assertions.assertFalse(graph.hasAdjacent("v4")),
            () -> Assertions.assertTrue(graph.hasAdjacent("v5"))
        );
    }
}
