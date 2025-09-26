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
public class AdjacencyMatrixGraph<V> implements Graph<V>, Cloneable {
    private final ArrayList<V> vertices;
    private final VertexSet vertexSet;
    private final EdgeSet edgeSet;
    private boolean[][] matrix;

    /**
     * Creates a new instance of {@code AdjacencyMatrixGraph}.
     */
    public AdjacencyMatrixGraph() {
        this.vertices = new ArrayList<>();
        this.vertexSet = new VertexSet();
        this.edgeSet = new EdgeSet();
        this.matrix = new boolean[0][0];
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
            var newMatrix = new boolean[matrix.length + 1][matrix.length + 1];
            for (int i = 0; i < matrix.length; i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix.length);
            }
            matrix = newMatrix;
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
            var newMatrix = new boolean[matrix.length - 1][matrix.length - 1];
            for (int i = 0; i < index; i++) {
                for (int j = 0; j < index; j++) {
                    newMatrix[i][j] = matrix[i][j];
                }
                for (int j = index + 1; j < matrix.length; j++) {
                    newMatrix[i][j - 1] = matrix[i][j];
                }
            }
            for (int i = index + 1; i < matrix.length; i++) {
                for (int j = 0; j < index; j++) {
                    newMatrix[i - 1][j] = matrix[i][j];
                }
                for (int j = index + 1; j < matrix.length; j++) {
                    newMatrix[i - 1][j - 1] = matrix[i][j];
                }
            }
            matrix = newMatrix;
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
                changed = changed || remove(toRemove);
            }
            return changed;
        }

        @Override
        public void clear() {
            vertices.clear();
            matrix = new boolean[0][0];
        }
    }

    private class EdgeSet extends AbstractSet<Edge<V>> {
        @Override
        public int size() {
            int result = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][j]) {
                        result++;
                    }
                }
            }
            return result;
        }

        @Override
        public boolean isEmpty() {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][j]) {
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
            return matrix[fromIndex][toIndex];
        }

        @Override
        public Iterator<Edge<V>> iterator() {
            return new Iterator<>() {
                int index = 0;

                private int from() {
                    return index / matrix.length;
                }

                private int to() {
                    return index % matrix.length;
                }

                @Override
                public boolean hasNext() {
                    int length = matrix.length;
                    int lengthSquared = length * length;
                    while (!matrix[from()][to()] && index < lengthSquared) {
                        index++;
                    }
                    return index < lengthSquared;
                }

                @Override
                public Edge<V> next() {
                    if (hasNext()) {
                        var edge = new Edge<>(vertices.get(from()), vertices.get(to()));
                        index++;
                        return edge;
                    }
                    throw new NoSuchElementException();
                }
            };
        }

        @Override
        public boolean add(Edge<V> edge) {
            vertices.add(edge.from());
            vertices.add(edge.to());
            return false;
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
            if (!matrix[fromIndex][toIndex]) {
                return false;
            }
            matrix[fromIndex][toIndex] = false;
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
                changed = changed || remove(toRemove);
            }
            return changed;
        }

        @Override
        public void clear() {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    matrix[i][j] = false;
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
    protected Object clone() {
        return new AdjacencyMatrixGraph<>(this);
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
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[index][i]) {
                result.add(vertices.get(i));
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
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[index][i]) {
                return true;
            }
        }
        return false;
    }
}
