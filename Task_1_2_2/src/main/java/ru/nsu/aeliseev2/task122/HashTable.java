package ru.nsu.aeliseev2.task122;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * An implementation of a hash table with open addressing.
 *
 * @param <K> The key type.
 * @param <V> The value type.
 */
public class HashTable<K, V> implements Map<K, V> {
    private class Cell implements Entry<K, V> {
        public K key;
        public V value;
        public boolean isDead;

        public Cell(K key, V value) {
            init(key, value);
        }

        public V init(K key, V value) {
            final V oldValue = isDead ? null : value;
            this.key = key;
            this.value = value;
            this.isDead = false;
            return oldValue;
        }

        public V kill() {
            V oldValue = isDead ? null : value;
            value = null;
            isDead = true;
            return oldValue;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return isDead ? null : value;
        }

        @Override
        public V setValue(V v) {
            var oldValue = value;
            value = v;
            return oldValue;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Map.Entry<?, ?>)) {
                return false;
            }
            var entry = (Entry<?, ?>) other;
            return Objects.equals(key, entry.getKey())
                && Objects.equals(value, entry.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return "{ " + key + ", " + value + " }";
        }

        public static boolean isDead(HashTable<?, ?>.Cell cell) {
            return cell == null || cell.isDead;
        }
    }

    private class EntryIterator implements Iterator<Entry<K, V>> {
        private int cellIndex = 0;
        private int remainingCount = HashTable.this.size();
        private int expectedModCount = HashTable.this.modCount;
        private Cell currentCell = null;

        @Override
        public boolean hasNext() {
            if (HashTable.this.modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            return remainingCount > 0;
        }

        @Override
        public Cell next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            do {
                currentCell = HashTable.this.cells[cellIndex++];
            } while (Cell.isDead(currentCell));
            remainingCount--;
            return currentCell;
        }

        @Override
        public void remove() {
            if (currentCell == null) {
                throw new IllegalStateException();
            }
            if (HashTable.this.modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            HashTable.this.remove(currentCell.key);
            expectedModCount = HashTable.this.modCount;
            currentCell = null;
        }
    }

    private class KeySet extends AbstractSet<K> {
        @Override
        public int size() {
            return HashTable.this.size();
        }

        @Override
        public Iterator<K> iterator() {
            var entryIterator = new EntryIterator();
            return new Iterator<>() {

                @Override
                public boolean hasNext() {
                    return entryIterator.hasNext();
                }

                @Override
                public K next() {
                    return entryIterator.next().key;
                }

                @Override
                public void remove() {
                    entryIterator.remove();
                }
            };
        }

        @Override
        public boolean contains(Object key) {
            return HashTable.this.containsKey(key);
        }

        @Override
        public boolean remove(Object key) {
            boolean result = HashTable.this.containsKey(key);
            HashTable.this.remove(key);
            return result;
        }

        @Override
        public void clear() {
            HashTable.this.clear();
        }
    }

    private class EntrySet extends AbstractSet<Entry<K, V>> {
        @Override
        public int size() {
            return HashTable.this.size();
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public boolean contains(Object o) {
            if (o instanceof Entry<?, ?>) {
                var entry = (Entry<?, ?>) o;
                var key = entry.getKey();
                var value = entry.getValue();
                if (!HashTable.this.containsKey(key)) {
                    return false;
                }
                return Objects.equals(value, HashTable.this.get(key));
            }
            return false;
        }

        @Override
        public boolean remove(Object o) {
            if (o instanceof Entry<?, ?>) {
                var entry = (Entry<?, ?>) o;
                var key = entry.getKey();
                var value = entry.getValue();
                if (!HashTable.this.containsKey(key)) {
                    return false;
                }
                var actualValue = HashTable.this.get(key);
                if (Objects.equals(value, actualValue)) {
                    HashTable.this.remove(key);
                    return true;
                }
            }
            return false;
        }

        @Override
        public void clear() {
            HashTable.this.clear();
        }
    }

    private class Values extends AbstractCollection<V> {
        @Override
        public int size() {
            return HashTable.this.size();
        }

        @Override
        public Iterator<V> iterator() {
            var entryIterator = new EntryIterator();
            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return entryIterator.hasNext();
                }

                @Override
                public V next() {
                    return entryIterator.next().getValue();
                }

                @Override
                public void remove() {
                    entryIterator.remove();
                }
            };
        }

        @Override
        public boolean contains(Object value) {
            return HashTable.this.containsValue(value);
        }

        @Override
        public void clear() {
            HashTable.this.clear();
        }
    }

    private static final double LOAD_FACTOR = 0.75;
    private static final int GROWTH_FACTOR = 2;
    private static final int INITIAL_CAPACITY = 16;

    private Cell[] cells = null;
    private int count = 0;
    private int modCount = 0;

    private KeySet keySet = null;
    private EntrySet entrySet = null;
    private Values values = null;

    private int getCellIndex(Object key) {
        if (cells == null || cells.length == 0) {
            return -1;
        }
        int hashCode = Objects.hashCode(key == null);
        int index = Integer.remainderUnsigned(hashCode, cells.length);
        int deadCellIndex = -1;
        int endIndex = index;
        do {
            var cell = cells[index];
            if (cell == null) {
                if (deadCellIndex != -1) {
                    return deadCellIndex;
                }
                return index;
            }
            if (cell.isDead && deadCellIndex == -1) {
                deadCellIndex = index;
            }
            if (Objects.equals(key, cell.key)) {
                return index;
            }
            index = Integer.remainderUnsigned(index + 1, cells.length);
        } while (index != endIndex);
        return deadCellIndex;
    }

    /**
     * Ensures additional capacity needed for insertion of {@code additionalCapacity} keys.
     *
     * @param additionalCapacity The number of keys to allocate space for.
     */
    public void ensureCapacity(int additionalCapacity) {
        modCount += 1;
        int capacity = cells == null ? 0 : cells.length;
        if (count + additionalCapacity <= capacity * LOAD_FACTOR) {
            return;
        }
        if (capacity == 0) {
            capacity = INITIAL_CAPACITY;
        }
        while (count + additionalCapacity > capacity * LOAD_FACTOR) {
            capacity *= GROWTH_FACTOR;
        }
        var oldCells = cells;
        //noinspection unchecked
        cells = new HashTable.Cell[capacity];
        if (oldCells == null) {
            return;
        }
        for (var cell : oldCells) {
            if (Cell.isDead(cell)) {
                continue;
            }
            int index = getCellIndex(cell.key);
            assert index != -1;
            cells[index] = cell;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        int cellIndex = getCellIndex(key);
        if (cellIndex == -1) {
            return false;
        }
        return !Cell.isDead(cells[cellIndex]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object o) {
        if (cells == null) {
            return false;
        }
        for (var cell : cells) {
            if (cell == null) {
                continue;
            }
            if (Objects.equals(o, cell.value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(Object key) {
        int cellIndex = getCellIndex(key);
        if (cellIndex == -1) {
            return null;
        }
        var cell = cells[cellIndex];
        if (Cell.isDead(cell)) {
            return null;
        }
        return cell.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        modCount += 1;
        ensureCapacity(1);
        int cellIndex = getCellIndex(key);
        assert cellIndex != -1;
        var cell = cells[cellIndex];
        if (Cell.isDead(cell)) {
            count += 1;
        }
        if (cell == null) {
            cells[cellIndex] = new Cell(key, value);
            return null;
        } else {
            return cell.init(key, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(Object key) {
        modCount += 1;
        int cellIndex = getCellIndex(key);
        if (cellIndex == -1) {
            return null;
        }
        var cell = cells[cellIndex];
        if (Cell.isDead(cell)) {
            return null;
        }
        count -= 1;
        return cell.kill();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(map.size());
        for (var entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        modCount += 1;
        cells = null;
        count = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {
        if (keySet == null) {
            keySet = new KeySet();
        }
        return keySet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {
        if (values == null) {
            values = new Values();
        }
        return values;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        if (entrySet == null) {
            entrySet = new EntrySet();
        }
        return entrySet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Map<?, ?>)) {
            return false;
        }
        var map = (Map<?, ?>) other;
        return entrySet().equals(map.entrySet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = 0;
        for (var entry : this.entrySet()) {
            result += entry.hashCode();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return entrySet().toString();
    }
}
