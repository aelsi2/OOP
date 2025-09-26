package ru.nsu.aeliseev2.task121;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Represents an unweighed oriented graph.
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
}
