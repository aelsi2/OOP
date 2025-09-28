package ru.nsu.aeliseev2.task121.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task121.Edge;
import ru.nsu.aeliseev2.task121.Graph;

abstract class GraphTests {
    abstract Graph<String> createGraph();

    @Test
    public void enumerateVertices() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().add("v1");
        graph.vertices().add("v3");
        var vertices = new ArrayList<String>();
        for (var vertex : graph.vertices()) {
            vertices.add(vertex);
        }
        Assertions.assertAll(
            () -> Assertions.assertEquals(3, vertices.size()),
            () -> Assertions.assertTrue(vertices.contains("v1"),
                "v1 not returned by iterator"),
            () -> Assertions.assertTrue(vertices.contains("v2"),
                "v2 not returned by iterator"),
            () -> Assertions.assertTrue(vertices.contains("v3"),
                "v3 not returned by iterator")
        );
    }

    @Test
    public void addRemoveVertices() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().remove("v1");
        graph.vertices().add("v3");
        Assertions.assertAll(
            () -> Assertions.assertFalse(graph.vertices().contains("v1"),
                "v1 is not supposed to be in graph"),
            () -> Assertions.assertTrue(graph.vertices().contains("v2"),
                "v2 not in graph"),
            () -> Assertions.assertTrue(graph.vertices().contains("v3"),
                "v3 not in graph")
        );
    }

    @Test
    public void enumerateEdges() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().add("v3");
        graph.edges().add(new Edge<>("v1", "v2"));
        graph.edges().add(new Edge<>("v2", "v3"));
        graph.edges().add(new Edge<>("v3", "v3"));
        graph.edges().add(new Edge<>("v3", "v3"));
        graph.edges().add(new Edge<>("v1", "v2"));
        var edges = new ArrayList<Edge<String>>();
        for (var edge : graph.edges()) {
            edges.add(edge);
        }
        Assertions.assertAll(
            () -> Assertions.assertEquals(3, edges.size()),
            () -> Assertions.assertTrue(edges.contains(new Edge<>("v1", "v2"))),
            () -> Assertions.assertTrue(edges.contains(new Edge<>("v2", "v3"))),
            () -> Assertions.assertTrue(edges.contains(new Edge<>("v3", "v3")))
        );
    }

    @Test
    public void addRemoveEdges() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().add("v3");
        graph.edges().add(new Edge<>("v1", "v2"));
        graph.edges().add(new Edge<>("v2", "v3"));
        graph.edges().add(new Edge<>("v3", "v3"));
        graph.edges().remove(new Edge<>("v3", "v3"));
        graph.edges().remove(new Edge<>("v1", "v2"));
        Assertions.assertAll(
            () -> Assertions.assertFalse(graph.edges().contains(new Edge<>("v1", "v2"))),
            () -> Assertions.assertTrue(graph.edges().contains(new Edge<>("v2", "v3"))),
            () -> Assertions.assertFalse(graph.edges().contains(new Edge<>("v3", "v3")))
        );
    }

    @Test
    public void hasAdjacent() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().add("v3");
        graph.vertices().add("v4");
        graph.vertices().add("v5");
        graph.edges().add(new Edge<>("v1", "v2"));
        graph.edges().add(new Edge<>("v3", "v2"));
        graph.edges().add(new Edge<>("v5", "v5"));
        Assertions.assertAll(
            () -> Assertions.assertTrue(graph.hasAdjacent("v1")),
            () -> Assertions.assertFalse(graph.hasAdjacent("v2")),
            () -> Assertions.assertTrue(graph.hasAdjacent("v3")),
            () -> Assertions.assertFalse(graph.hasAdjacent("v4")),
            () -> Assertions.assertTrue(graph.hasAdjacent("v5"))
        );
    }

    @Test
    public void getAdjacent() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().add("v3");
        graph.vertices().add("v4");
        graph.vertices().add("v5");
        graph.edges().add(new Edge<>("v1", "v2"));
        graph.edges().add(new Edge<>("v1", "v1"));
        graph.edges().add(new Edge<>("v1", "v3"));
        graph.edges().add(new Edge<>("v4", "v5"));
        graph.edges().add(new Edge<>("v5", "v1"));
        graph.edges().add(new Edge<>("v3", "v1"));
        var actual = new HashSet<>(graph.getAdjacent("v1"));
        var expected = Set.of("v1", "v2", "v3");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void removeVerticesWithEdges() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().add("v3");
        graph.vertices().add("v4");
        graph.vertices().add("v5");
        graph.edges().add(new Edge<>("v1", "v2"));
        graph.edges().add(new Edge<>("v3", "v1"));
        graph.edges().add(new Edge<>("v4", "v3"));
        graph.edges().add(new Edge<>("v5", "v1"));
        graph.edges().add(new Edge<>("v1", "v4"));
        graph.edges().add(new Edge<>("v5", "v2"));
        graph.vertices().remove("v1");
        var expectedEdges = Set.of(
            new Edge<>("v4", "v3"),
            new Edge<>("v5", "v2")
        );
        Assertions.assertEquals(expectedEdges, graph.edges());
    }

    @Test
    public void verticesRetainAll() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().add("v3");
        graph.vertices().add("v4");
        graph.vertices().add("v5");
        graph.edges().add(new Edge<>("v1", "v2"));
        graph.edges().add(new Edge<>("v3", "v1"));
        graph.edges().add(new Edge<>("v4", "v3"));
        graph.edges().add(new Edge<>("v5", "v1"));
        graph.edges().add(new Edge<>("v1", "v4"));
        graph.edges().add(new Edge<>("v5", "v2"));

        var expectedVertices = Set.of("v1", "v3", "v4");
        var expectedEdges = Set.of(
            new Edge<>("v3", "v1"),
            new Edge<>("v4", "v3"),
            new Edge<>("v1", "v4")
        );
        graph.vertices().retainAll(expectedVertices);
        Assertions.assertAll(
            () -> Assertions.assertEquals(expectedVertices, graph.vertices()),
            () -> Assertions.assertEquals(expectedEdges, graph.edges())
        );
    }

    @Test
    public void verticesRemoveAll() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().add("v3");
        graph.vertices().add("v4");
        graph.vertices().add("v5");
        graph.edges().add(new Edge<>("v1", "v2"));
        graph.edges().add(new Edge<>("v3", "v1"));
        graph.edges().add(new Edge<>("v4", "v3"));
        graph.edges().add(new Edge<>("v5", "v1"));
        graph.edges().add(new Edge<>("v1", "v4"));
        graph.edges().add(new Edge<>("v5", "v2"));

        var removeVertices = Set.of("v2", "v5");
        var expectedVertices = Set.of("v1", "v3", "v4");
        var expectedEdges = Set.of(
            new Edge<>("v3", "v1"),
            new Edge<>("v4", "v3"),
            new Edge<>("v1", "v4")
        );
        graph.vertices().removeAll(removeVertices);
        Assertions.assertAll(
            () -> Assertions.assertEquals(expectedVertices, graph.vertices()),
            () -> Assertions.assertEquals(expectedEdges, graph.edges())
        );
    }

    @Test
    public void edgesRetainAll() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().add("v3");
        graph.vertices().add("v4");
        graph.vertices().add("v5");
        graph.edges().add(new Edge<>("v1", "v2"));
        graph.edges().add(new Edge<>("v3", "v1"));
        graph.edges().add(new Edge<>("v4", "v3"));
        graph.edges().add(new Edge<>("v5", "v1"));
        graph.edges().add(new Edge<>("v1", "v4"));
        graph.edges().add(new Edge<>("v5", "v2"));

        var retainEdges = Set.of(
            new Edge<>("v3", "v1"),
            new Edge<>("v1", "v5"),
            new Edge<>("v1", "v4"),
            new Edge<>("v5", "v2")
        );
        var expectedEdges = Set.of(
            new Edge<>("v3", "v1"),
            new Edge<>("v1", "v4"),
            new Edge<>("v5", "v2")
        );
        graph.edges().retainAll(retainEdges);
        Assertions.assertEquals(expectedEdges, graph.edges());
    }

    @Test
    public void edgesRemoveAll() {
        var graph = createGraph();
        graph.vertices().add("v1");
        graph.vertices().add("v2");
        graph.vertices().add("v3");
        graph.vertices().add("v4");
        graph.vertices().add("v5");
        graph.edges().add(new Edge<>("v1", "v2"));
        graph.edges().add(new Edge<>("v3", "v1"));
        graph.edges().add(new Edge<>("v4", "v3"));
        graph.edges().add(new Edge<>("v5", "v1"));
        graph.edges().add(new Edge<>("v1", "v4"));
        graph.edges().add(new Edge<>("v5", "v2"));

        var removeEdges = Set.of(
            new Edge<>("v3", "v1"),
            new Edge<>("v1", "v5"),
            new Edge<>("v1", "v4"),
            new Edge<>("v5", "v2")
        );
        var expectedEdges = Set.of(
            new Edge<>("v1", "v2"),
            new Edge<>("v4", "v3"),
            new Edge<>("v5", "v1")
        );
        graph.edges().removeAll(removeEdges);
        Assertions.assertEquals(expectedEdges, graph.edges());
    }

    @Test
    public void equalsTest() {
        var graph = createGraph();

        var vertices = Set.of("v1", "v2", "v3", "v4", "v5");
        var edges = Set.of(
            new Edge<>("v1", "v2"),
            new Edge<>("v3", "v1"),
            new Edge<>("v4", "v3"),
            new Edge<>("v5", "v1"),
            new Edge<>("v1", "v4"),
            new Edge<>("v5", "v2")
        );
        graph.vertices().addAll(vertices);
        graph.edges().addAll(edges);

        var other = new Graph<String>() {
            @Override
            public Set<String> vertices() {
                return vertices;
            }

            @Override
            public Set<Edge<String>> edges() {
                return edges;
            }
        };
        Assertions.assertEquals(graph, other);
    }

    @Test
    public void hashCodeTest() {
        var graph = createGraph();

        var vertices = Set.of("v1", "v2", "v3", "v4", "v5");
        var edges = Set.of(
            new Edge<>("v1", "v2"),
            new Edge<>("v3", "v1"),
            new Edge<>("v4", "v3"),
            new Edge<>("v5", "v1"),
            new Edge<>("v1", "v4"),
            new Edge<>("v5", "v2")
        );
        graph.vertices().addAll(vertices);
        graph.edges().addAll(edges);

        int expected = Objects.hash(vertices, edges);
        int actual = graph.hashCode();
        Assertions.assertEquals(expected, actual);
    }

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

    @Test
    public void topoSort() {
        var graph = createGraph();
        var vertices = Set.of("v1", "v2", "v3", "v4", "v5");
        var edges = Set.of(
            new Edge<>("v1", "v2"),
            new Edge<>("v4", "v3"),
            new Edge<>("v5", "v1"),
            new Edge<>("v1", "v4"),
            new Edge<>("v5", "v2")
        );
        graph.vertices().addAll(vertices);
        graph.edges().addAll(edges);
        var result = graph.topoSort();
        Assertions.assertAll(
            () -> Assertions.assertTrue(result.indexOf("v1") < result.indexOf("v2")),
            () -> Assertions.assertTrue(result.indexOf("v4") < result.indexOf("v3")),
            () -> Assertions.assertTrue(result.indexOf("v5") < result.indexOf("v1")),
            () -> Assertions.assertTrue(result.indexOf("v1") < result.indexOf("v4")),
            () -> Assertions.assertTrue(result.indexOf("v5") < result.indexOf("v2"))
        );
    }
}
