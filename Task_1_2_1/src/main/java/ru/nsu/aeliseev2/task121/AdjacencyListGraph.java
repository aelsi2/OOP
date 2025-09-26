package ru.nsu.aeliseev2.task121;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * A {@code Graph} implementation using adjacency lists.
 *
 * @param <V> The type of the vertices.
 */
public class AdjacencyListGraph<V> implements Graph<V>, Cloneable {
    private final HashMap<V, ArrayList<V>> adjacent;
    private final VertexSet vertexSet;
    private final EdgeSet edgeSet;

    /**
     * Creates a new instance of {@code AdjacencyListGraph}.
     */
    public AdjacencyListGraph() {
        this.adjacent = new HashMap<>();
        this.vertexSet = new VertexSet();
        this.edgeSet = new EdgeSet();
    }

    /**
     * Creates a new instance of {@code AdjacencyListGraph}.
     */
    public AdjacencyListGraph(Graph<V> graph) {
        this();
        vertexSet.addAll(graph.vertices());
        edgeSet.addAll(graph.edges());
    }

    private class VertexSet implements Set<V> {

        @Override
        public int size() {
            return adjacent.size();
        }

        @Override
        public boolean isEmpty() {
            return adjacent.isEmpty();
        }

        @SuppressWarnings("SuspiciousMethodCalls")
        @Override
        public boolean contains(Object o) {
            return adjacent.containsKey(o);
        }

        @Override
        public Iterator<V> iterator() {
            return adjacent.keySet().iterator();
        }

        @Override
        public Object[] toArray() {
            return adjacent.keySet().toArray();
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            return adjacent.keySet().toArray(ts);
        }

        @Override
        public boolean add(V v) {
            if (adjacent.containsKey(v)) {
                return false;
            }
            adjacent.put(v, new ArrayList<>());
            return true;
        }

        @Override
        public boolean remove(Object o) {
            if (adjacent.remove(o) == null) {
                return false;
            }
            for (var list : adjacent.values()) {
                list.remove(o);
            }
            return true;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return adjacent.keySet().containsAll(collection);
        }

        @Override
        public boolean addAll(Collection<? extends V> collection) {
            boolean changed = false;
            for (var element : collection) {
                changed = changed || add(element);
            }
            return changed;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            boolean result = adjacent.keySet().retainAll(collection);
            for (var list : adjacent.values()) {
                list.removeIf((v) -> !collection.contains(v));
            }
            return result;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            boolean result = adjacent.keySet().removeAll(collection);
            for (var list : adjacent.values()) {
                list.removeIf(collection::contains);
            }
            return result;
        }

        @Override
        public void clear() {
            adjacent.clear();
        }
    }

    private class EdgeSet extends AbstractSet<Edge<V>> {
        @Override
        public int size() {
            int totalCount = 0;
            for (var list : adjacent.values()) {
                totalCount += list.size();
            }
            return totalCount;
        }

        @Override
        public boolean isEmpty() {
            for (var list : adjacent.values()) {
                if (!list.isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        @SuppressWarnings("SuspiciousMethodCalls")
        @Override
        public boolean contains(Object obj) {
            if (!(obj instanceof Edge<?> edge)) {
                return false;
            }
            if (!adjacent.containsKey(edge.from())) {
                return false;
            }
            var list = adjacent.get(edge.from());
            return list.contains(edge.from());
        }

        @Override
        public Iterator<Edge<V>> iterator() {
            return adjacent.keySet().stream().flatMap(
                (v) -> adjacent.get(v).stream().map(
                    (a) -> new Edge<>(v, a)
                )
            ).iterator();
        }

        @Override
        public boolean add(Edge<V> edge) {
            vertexSet.add(edge.from());
            vertexSet.add(edge.to());
            var list = adjacent.get(edge.from());
            if (list.contains(edge.to())) {
                return false;
            }
            list.add(edge.to());
            return true;
        }

        @SuppressWarnings("SuspiciousMethodCalls")
        @Override
        public boolean remove(Object obj) {
            if (!(obj instanceof Edge<?> edge)) {
                return false;
            }
            if (!adjacent.containsKey(edge.from())) {
                return false;
            }
            var list = adjacent.get(edge.from());
            if (!list.contains(edge.to())) {
                return false;
            }
            list.remove(edge.to());
            return true;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            boolean changed = false;
            for (var vert : adjacent.keySet()) {
                var adjList = adjacent.get(vert);
                for (int i = adjList.size() - 1; i >= 0; i--) {
                    if (collection.contains(new Edge<>(vert, adjList.get(i)))) {
                        continue;
                    }
                    adjList.remove(i);
                    changed = true;
                }
            }
            return changed;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            boolean changed = false;
            for (var vert : adjacent.keySet()) {
                var adjList = adjacent.get(vert);
                for (int i = adjList.size() - 1; i >= 0; i--) {
                    if (!collection.contains(new Edge<>(vert, adjList.get(i)))) {
                        continue;
                    }
                    adjList.remove(i);
                    changed = true;
                }
            }
            return changed;
        }

        @Override
        public void clear() {
            adjacent.clear();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object clone() {
        return new AdjacencyListGraph<>(this);
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
        return adjacent.get(vertex);
    }
}
