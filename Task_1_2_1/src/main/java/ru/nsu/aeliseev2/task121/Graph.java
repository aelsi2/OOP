package ru.nsu.aeliseev2.task121;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Represents an unweighted oriented graph.
 * <p>
 * Note: {@code hashCode} must calculate the has code with
 * {@code Objects.hashCode(vertices(), edges())}.
 * </p>
 *
 * @param <V> The type of the vertices.
 */
public interface Graph<V> {
    /**
     * Gets the (mutable) set of graph's vertices.
     *
     * @return The set.
     */
    Set<V> vertices();

    /**
     * Gets the (mutable) set of graph's edges.
     *
     * @return The set.
     */
    Set<Edge<V>> edges();

    /**
     * Gets the collection of vertices adjacent to a graph's vertex.
     *
     * @param vertex The vertex.
     * @return The collection of adjacent vertices.
     */
    default Collection<V> getAdjacent(V vertex) {
        return edges()
            .stream()
            .filter((e) -> e.from().equals(vertex))
            .map(Edge::to)
            .toList();
    }

    /**
     * Checks if a vertex has any adjacent vertices.
     *
     * @param vertex The vertex.
     * @return Whether the vertex has any adjacent vertices.
     */
    default boolean hasAdjacent(V vertex) {
        return !getAdjacent(vertex).isEmpty();
    }

    /**
     * Sorts the graph's vertices in topological order.
     *
     * @return The list of graph vertices in topological order.
     */
    default List<V> topoSort() {
        Graph<V> clone = new AdjacencyListGraph<>(this);
        var result = new ArrayList<V>();
        while (!clone.vertices().isEmpty()) {
            var option = clone.vertices().stream().filter((v) -> !clone.hasAdjacent(v)).findAny();
            if (option.isEmpty()) {
                throw new RuntimeException("Graph contains a cycle.");
            }
            clone.vertices().remove(option.get());
            result.add(0, option.get());
        }
        return result;
    }

    /**
     * Read and adds vertices and edges in the following format:
     * <pre>
     * vertex1
     * vertex2
     * vertex3
     * vertex1 -> vertex2
     * vertex1 -> vertex1
     * </pre>
     *
     * @param scanner       The scanner to read with.
     * @param vertexFactory The function to create vertices with.
     */
    default void read(Scanner scanner, Function<String, V> vertexFactory) {
        var pattern = Pattern.compile("^\\s*(\\S+?)(?:\\s*->\\s*(\\S+?))?\\s*$");
        while (scanner.hasNext(pattern)) {
            var value = scanner.next(pattern);
            var matcher = pattern.matcher(value);

            var from = vertexFactory.apply(matcher.group(0));
            vertices().add(from);

            if (matcher.groupCount() == 2) {
                var to = vertexFactory.apply(matcher.group(1));
                vertices().add(to);
                edges().add(new Edge<>(from, to));
            }
        }
    }
}
