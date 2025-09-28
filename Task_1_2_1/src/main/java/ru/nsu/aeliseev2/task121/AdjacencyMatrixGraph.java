package ru.nsu.aeliseev2.task121;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * A {@code Graph} implementation using an adjacency matrix.
 *
 * @param <V> The type of the vertices.
 */
public class AdjacencyMatrixGraph<V> implements Graph<V> {
    private final ArrayList<V> vertices;
    private final VertexSet vertexSet;
    private final EdgeSet edgeSet;
    private final IntMatrix matrix;

    /**
     * Creates a new instance of {@code AdjacencyMatrixGraph}.
     */
    public AdjacencyMatrixGraph() {
        this.vertices = new ArrayList<>();
        this.vertexSet = new VertexSet();
        this.edgeSet = new EdgeSet();
        this.matrix = new IntMatrix();
    }

    /**
     * Creates a new instance of {@code AdjacencyMatrixGraph}.
     *
     * @param graph The graph to copy edges and vertices from.
     */
    public AdjacencyMatrixGraph(Graph<V> graph) {
        this();
        vertexSet.addAll(graph.vertices());
        edgeSet.addAll(graph.edges());
    }

    private class VertexSet extends AbstractSet<V> {
        @Override
        public int size() {
            return vertices.size();
        }

        @Override
        public boolean isEmpty() {
            return vertices.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return vertices.contains(o);
        }

        @Override
        public Iterator<V> iterator() {
            return vertices.iterator();
        }

        @Override
        public boolean add(V v) {
            if (vertices.contains(v)) {
                return false;
            }
            vertices.add(v);
            matrix.addRow();
            matrix.addColumn();
            return true;
        }

        @SuppressWarnings("SuspiciousMethodCalls")
        @Override
        public boolean remove(Object o) {
            int index = vertices.indexOf(o);
            if (index == -1) {
                return false;
            }
            vertices.remove(index);
            matrix.removeRow(index);
            matrix.removeColumn(index);
            return true;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return vertices.containsAll(collection);
        }

        @SuppressWarnings({"SuspiciousMethodCalls", "SlowAbstractSetRemoveAll"})
        @Override
        public boolean retainAll(Collection<?> collection) {
            var toRemove = new ArrayList<>(vertices);
            toRemove.removeAll(collection);
            return removeAll(toRemove);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            boolean changed = false;
            for (var toRemove : collection) {
                changed = remove(toRemove) || changed;
            }
            return changed;
        }

        @Override
        public void clear() {
            vertices.clear();
            matrix.removeAll();
        }
    }

    private class EdgeSet extends AbstractSet<Edge<V>> {
        @Override
        public int size() {
            int result = 0;
            for (int i = 0; i < matrix.height(); i++) {
                for (int j = 0; j < matrix.width(); j++) {
                    if (matrix.get(i, j) != 0) {
                        result++;
                    }
                }
            }
            return result;
        }

        @Override
        public boolean isEmpty() {
            for (int i = 0; i < matrix.height(); i++) {
                for (int j = 0; j < matrix.width(); j++) {
                    if (matrix.get(i, j) != 0) {
                        return false;
                    }
                }
            }
            return true;
        }

        @SuppressWarnings("SuspiciousMethodCalls")
        @Override
        public boolean contains(Object obj) {
            if (!(obj instanceof Edge<?> edge)) {
                return false;
            }
            int fromIndex = vertices.indexOf(edge.from());
            int toIndex = vertices.indexOf(edge.to());
            if (fromIndex == -1 || toIndex == -1) {
                return false;
            }
            return matrix.get(fromIndex, toIndex) != 0;
        }

        @Override
        public Iterator<Edge<V>> iterator() {
            return new Iterator<>() {
                int row = 0;
                int column = 0;

                @Override
                public boolean hasNext() {
                    for (; row < matrix.height(); row++) {
                        for (; column < matrix.width(); column++) {
                            if (matrix.get(row, column) != 0) {
                                return true;
                            }
                        }
                        column = 0;
                    }
                    return false;
                }

                @Override
                public Edge<V> next() {
                    if (hasNext()) {
                        var edge = new Edge<>(vertices.get(row), vertices.get(column));
                        column++;
                        return edge;
                    }
                    throw new NoSuchElementException();
                }
            };
        }

        @Override
        public boolean add(Edge<V> edge) {
            vertexSet.add(edge.from());
            vertexSet.add(edge.to());
            int fromIndex = vertices.indexOf(edge.from());
            int toIndex = vertices.indexOf(edge.to());
            if (matrix.get(fromIndex, toIndex) != 0) {
                return false;
            }
            matrix.set(fromIndex, toIndex, 1);
            return true;
        }

        @SuppressWarnings("SuspiciousMethodCalls")
        @Override
        public boolean remove(Object obj) {
            if (!(obj instanceof Edge<?> edge)) {
                return false;
            }
            int fromIndex = vertices.indexOf(edge.from());
            int toIndex = vertices.indexOf(edge.to());
            if (fromIndex == -1 || toIndex == -1) {
                return false;
            }
            if (matrix.get(fromIndex, toIndex) == 0) {
                return false;
            }
            matrix.set(fromIndex, toIndex, 0);
            return true;
        }

        @SuppressWarnings({"SuspiciousMethodCalls", "SlowAbstractSetRemoveAll"})
        @Override
        public boolean retainAll(Collection<?> collection) {
            var toRemove = new ArrayList<>(this);
            toRemove.removeAll(collection);
            return removeAll(toRemove);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            boolean changed = false;
            for (var toRemove : collection) {
                changed = remove(toRemove) || changed;
            }
            return changed;
        }

        @Override
        public void clear() {
            for (int row = 0; row < matrix.height(); row++) {
                for (int column = 0; column < matrix.width(); column++) {
                    matrix.set(row, column, 0);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Graph<?> graph)) {
            return false;
        }
        return graph.vertices().equals(vertices()) && graph.edges().equals(edges());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(vertices(), edges());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("V = %s, E = %s", vertexSet, edgeSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> vertices() {
        return vertexSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Edge<V>> edges() {
        return edgeSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> getAdjacent(V vertex) {
        int index = vertices.indexOf(vertex);
        if (index == -1) {
            return Set.of();
        }
        var result = new ArrayList<V>();
        for (int column = 0; column < matrix.width(); column++) {
            if (matrix.get(index, column) != 0) {
                result.add(vertices.get(column));
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAdjacent(V vertex) {
        int index = vertices.indexOf(vertex);
        if (index == -1) {
            return false;
        }
        for (int column = 0; column < matrix.width(); column++) {
            if (matrix.get(index, column) != 0) {
                return true;
            }
        }
        return false;
    }
}
