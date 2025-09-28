package ru.nsu.aeliseev2.task121;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * A {@code Graph} implementation using an incidence matrix.
 *
 * @param <V> The type of the vertices.
 */
public class IncidenceMatrixGraph<V> implements Graph<V>, Cloneable {
    private final ArrayList<V> vertices;
    private final IntMatrix matrix;
    private final VertexSet vertexSet;
    private final EdgeSet edgeSet;

    /**
     * Creates a new instance of {@code IncidenceMatrixGraph}.
     */
    public IncidenceMatrixGraph() {
        vertices = new ArrayList<>();
        matrix = new IntMatrix();
        vertexSet = new VertexSet();
        edgeSet = new EdgeSet();
    }

    /**
     * Creates a new instance of {@code IncidenceMatrixGraph}.
     *
     * @param graph The graph to copy edges and vertices from.
     */
    public IncidenceMatrixGraph(Graph<V> graph) {
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
            for (int row = matrix.height() - 1; row >= 0; row--) {
                if (matrix.get(row, index) != 0) {
                    matrix.removeRow(row);
                }
            }
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
            return matrix.height();
        }

        @Override
        public boolean isEmpty() {
            return matrix.height() == 0;
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
            for (int row = 0; row < matrix.height(); row++) {
                if (fromIndex == toIndex && matrix.get(row, fromIndex) == 2) {
                    return true;
                }
                if (matrix.get(row, fromIndex) == -1 && matrix.get(row, toIndex) == 1) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Iterator<Edge<V>> iterator() {
            return new Iterator<>() {
                int row = 0;

                @Override
                public boolean hasNext() {
                    return row < matrix.height();
                }

                @Override
                public Edge<V> next() {
                    if (row >= matrix.height()) {
                        throw new NoSuchElementException();
                    }
                    int fromIndex = 0;
                    int toIndex = 0;
                    for (int column = 0; column < matrix.width(); column++) {
                        switch (matrix.get(row, column)) {
                            case -1:
                                fromIndex = column;
                                break;
                            case 1:
                                toIndex = column;
                                break;
                            case 2:
                                fromIndex = column;
                                toIndex = column;
                                break;
                            default:
                                break;
                        }
                    }
                    row++;
                    return new Edge<>(vertices.get(fromIndex), vertices.get(toIndex));
                }
            };
        }

        @Override
        public boolean add(Edge<V> edge) {
            vertexSet.add(edge.from());
            vertexSet.add(edge.to());
            int fromIndex = vertices.indexOf(edge.from());
            int toIndex = vertices.indexOf(edge.to());
            for (int row = 0; row < matrix.height(); row++) {
                if (fromIndex == toIndex && matrix.get(row, fromIndex) == 2) {
                    return false;
                }
                if (matrix.get(row, fromIndex) == -1 && matrix.get(row, toIndex) == 1) {
                    return false;
                }
            }
            matrix.addRow();
            if (fromIndex == toIndex) {
                matrix.set(matrix.height() - 1, fromIndex, 2);
            } else {
                matrix.set(matrix.height() - 1, fromIndex, -1);
                matrix.set(matrix.height() - 1, toIndex, 1);
            }
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
            for (int row = 0; row < matrix.height(); row++) {
                if (fromIndex == toIndex && matrix.get(row, fromIndex) == 2) {
                    matrix.removeRow(row);
                    return true;
                }
                if (matrix.get(row, fromIndex) == -1 && matrix.get(row, toIndex) == 1) {
                    matrix.removeRow(row);
                    return true;
                }
            }
            return false;
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
            matrix.removeRows();
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
        return new IncidenceMatrixGraph<>(this);
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
        int fromColumn = vertices.indexOf(vertex);
        if (fromColumn == -1) {
            return Set.of();
        }
        var result = new ArrayList<V>();
        for (int row = 0; row < matrix.height(); row++) {
            int fromValue = matrix.get(row, fromColumn);
            if (fromValue != -1 && fromValue != 2) {
                continue;
            }
            for (int toColumn = 0; toColumn < matrix.width(); toColumn++) {
                int toValue = matrix.get(row, toColumn);
                if (toValue == 1 || toValue == 2) {
                    result.add(vertices.get(toColumn));
                    break;
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAdjacent(V vertex) {
        int column = vertices.indexOf(vertex);
        if (column == -1) {
            return false;
        }
        for (int row = 0; row < matrix.height(); row++) {
            int value = matrix.get(row, column);
            if (value == -1 || value == 2) {
                return true;
            }
        }
        return false;
    }
}
