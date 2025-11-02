package ru.nsu.aeliseev2.task122.test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task122.HashTable;

class HashTableTests {
    @Test
    void putGet() {
        var map = new HashTable<String, String>();

        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");

        Assertions.assertAll(
            () -> Assertions.assertEquals(3, map.size()),
            () -> Assertions.assertEquals("foo_val", map.get("foo_key")),
            () -> Assertions.assertEquals("bar_val", map.get("bar_key")),
            () -> Assertions.assertEquals("baz_val", map.get("baz_key"))
        );
    }

    @Test
    void putGetRehash() {
        var map = new HashTable<String, String>();

        for (int i = 0; i < 24; i++) {
            map.put(i + "_key", i + "_el");
        }

        Assertions.assertAll(
            () -> Assertions.assertEquals(24, map.size()),
            () -> {
                for (int i = 0; i < 24; i++) {
                    Assertions.assertEquals(i + "_el", map.get(i + "_key"));
                }
            }
        );
    }

    @Test
    void putGetMany() {
        var map = new HashTable<String, String>();

        for (int i = 0; i < 1000; i++) {
            map.put(i + "_key", i + "_el");
        }

        Assertions.assertAll(
            () -> Assertions.assertEquals(1000, map.size()),
            () -> {
                for (int i = 0; i < 1000; i++) {
                    Assertions.assertEquals(i + "_el", map.get(i + "_key"));
                }
            }
        );
    }

    @Test
    void putOverwriteGet() {
        var map = new HashTable<String, String>();

        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");
        map.put("bar_key", "bar_new_val");

        Assertions.assertAll(
            () -> Assertions.assertEquals(3, map.size()),
            () -> Assertions.assertEquals("foo_val", map.get("foo_key")),
            () -> Assertions.assertEquals("bar_new_val", map.get("bar_key")),
            () -> Assertions.assertEquals("baz_val", map.get("baz_key"))
        );
    }

    @Test
    void putRemoveGet() {
        var map = new HashTable<String, String>();

        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");
        var removed = map.remove("bar_key");
        var removedNonExistent = map.remove("fizz_key");

        Assertions.assertAll(
            () -> Assertions.assertEquals(2, map.size()),
            () -> Assertions.assertEquals("foo_val", map.get("foo_key")),
            () -> Assertions.assertNull(map.get("bar_key")),
            () -> Assertions.assertEquals("bar_val", removed),
            () -> Assertions.assertNull(removedNonExistent),
            () -> Assertions.assertEquals("baz_val", map.get("baz_key"))
        );
    }

    @Test
    void putAll() {
        final var source = new HashMap<String, String>();
        source.put("foo_key", "foo_val");
        source.put("bar_key", "bar_val");
        source.put("baz_key", "baz_val");

        final var map = new HashTable<String, String>();
        map.putAll(source);

        Assertions.assertAll(
            () -> Assertions.assertEquals(3, map.size()),
            () -> Assertions.assertEquals("foo_val", map.get("foo_key")),
            () -> Assertions.assertEquals("bar_val", map.get("bar_key")),
            () -> Assertions.assertEquals("baz_val", map.get("baz_key"))
        );
    }

    @Test
    void clear() {
        var map = new HashTable<String, String>();

        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");
        map.clear();

        Assertions.assertAll(
            () -> Assertions.assertEquals(0, map.size()),
            () -> Assertions.assertFalse(map.containsKey("foo_key")),
            () -> Assertions.assertFalse(map.containsKey("bar_key")),
            () -> Assertions.assertFalse(map.containsKey("baz_key"))
        );
    }

    @Test
    void nullKey() {
        var map = new HashTable<String, String>();

        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");
        map.put(null, "fizz_val");

        Assertions.assertAll(
            () -> Assertions.assertEquals(4, map.size()),
            () -> Assertions.assertEquals("foo_val", map.get("foo_key")),
            () -> Assertions.assertEquals("bar_val", map.get("bar_key")),
            () -> Assertions.assertEquals("baz_val", map.get("baz_key")),
            () -> Assertions.assertEquals("fizz_val", map.get(null))
        );
    }

    @Test
    void getNonExistent() {
        var map = new HashTable<String, String>();

        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");

        Assertions.assertNull(map.get("baz_key"));
    }

    @Test
    void containsKey() {
        var map = new HashTable<String, String>();

        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");

        Assertions.assertAll(
            () -> Assertions.assertTrue(map.containsKey("foo_key")),
            () -> Assertions.assertTrue(map.containsKey("bar_key")),
            () -> Assertions.assertFalse(map.containsKey("baz_key"))
        );
    }

    @Test
    void containsValue() {
        var map = new HashTable<String, String>();

        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put(null, null);

        Assertions.assertAll(
            () -> Assertions.assertTrue(map.containsValue("bar_val")),
            () -> Assertions.assertTrue(map.containsValue("bar_val")),
            () -> Assertions.assertFalse(map.containsValue("baz_val")),
            () -> Assertions.assertTrue(map.containsValue(null))
        );
    }

    private record CollidingInteger(int value) {
        @Override
        public int hashCode() {
            return 15;
        }
    }

    @Test
    void collisionTest() {
        var map = new HashTable<CollidingInteger, String>();

        map.put(new CollidingInteger(69), "69");
        map.put(new CollidingInteger(420), "420");
        map.put(new CollidingInteger(1337), "1337");

        Assertions.assertAll(
            () -> Assertions.assertEquals(3, map.size()),
            () -> Assertions.assertEquals("69", map.get(new CollidingInteger(69))),
            () -> Assertions.assertEquals("420", map.get(new CollidingInteger(420))),
            () -> Assertions.assertEquals("1337",
                map.get(new CollidingInteger(1337)))
        );
    }

    @Test
    void equalsTest() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");

        var referenceMap = new HashMap<String, String>();
        referenceMap.put("foo_key", "foo_val");
        referenceMap.put("bar_key", "bar_val");
        referenceMap.put("baz_key", "baz_val");

        Assertions.assertEquals(map, referenceMap);
    }

    @Test
    void entrySetIterate() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");

        var entries = new ArrayList<Map.Entry<String, String>>();
        for (var entry : map.entrySet()) {
            entries.add(entry);
        }

        Assertions.assertAll(
            () -> Assertions.assertEquals(3, entries.size()),
            () -> Assertions.assertNotEquals(entries.get(0).getKey(), entries.get(1).getKey()),
            () -> Assertions.assertNotEquals(entries.get(0).getKey(), entries.get(2).getKey()),
            () -> Assertions.assertNotEquals(entries.get(1).getKey(), entries.get(2).getKey()),
            () -> {
                for (var entry : entries) {
                    Assertions.assertEquals(entry.getValue(), map.get(entry.getKey()));
                }
            }
        );
    }

    @Test
    void entrySetIterateConcurrentModification() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");

        var iterator = map.entrySet().iterator();
        var entry = iterator.next();
        var entry2 = iterator.next();
        map.remove(entry2.getKey());

        Assertions.assertAll(
            () -> Assertions.assertEquals(entry.getValue(), map.get(entry.getKey())),
            () -> Assertions.assertThrows(ConcurrentModificationException.class, iterator::next)
        );
    }

    @Test
    void entrySetIterateRemove() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");

        var iterator = map.entrySet().iterator();
        var entry = iterator.next();
        iterator.remove();
        var entry2 = iterator.next();
        var entry3 = iterator.next();

        Assertions.assertAll(
            () -> Assertions.assertEquals(2, map.size()),
            () -> Assertions.assertEquals(entry2.getValue(), map.get(entry2.getKey())),
            () -> Assertions.assertEquals(entry3.getValue(), map.get(entry3.getKey())),
            () -> Assertions.assertNull(map.get(entry.getKey()))
        );
    }

    @Test
    void entrySetEquality() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");

        var set = new HashSet<Map.Entry<String, String>>();
        set.add(new AbstractMap.SimpleEntry<>("foo_key", "foo_val"));
        set.add(new AbstractMap.SimpleEntry<>("bar_key", "bar_val"));
        set.add(new AbstractMap.SimpleEntry<>("baz_key", "baz_val"));

        Assertions.assertEquals(map.entrySet(), set);
    }

    @Test
    void entrySetRemove() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");
        map.entrySet().remove(new AbstractMap.SimpleEntry<>("foo_key", "foo_val"));
        map.entrySet().remove(new AbstractMap.SimpleEntry<>("bar_key", ""));

        Assertions.assertAll(
            () -> Assertions.assertEquals(2, map.size()),
            () -> Assertions.assertFalse(map.containsKey("foo_key")),
            () -> Assertions.assertTrue(map.containsKey("bar_key")),
            () -> Assertions.assertTrue(map.containsKey("baz_key"))
        );
    }

    @Test
    void entrySetContains() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");

        Assertions.assertAll(
            () -> Assertions.assertTrue(map.entrySet().contains(
                new AbstractMap.SimpleEntry<>("foo_key", "foo_val")
            )),
            () -> Assertions.assertTrue(map.entrySet().contains(
                new AbstractMap.SimpleEntry<>("bar_key", "bar_val")
            )),
            () -> Assertions.assertFalse(map.entrySet().contains(
                new AbstractMap.SimpleEntry<>("baz_key", "other_val")
            ))
        );
    }

    @Test
    void keySetEquality() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");

        var set = new HashSet<String>();
        set.add("foo_key");
        set.add("bar_key");
        set.add("baz_key");

        Assertions.assertEquals(map.keySet(), set);
    }

    @Test
    void keySetContains() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");

        Assertions.assertAll(
            () -> Assertions.assertTrue(map.keySet().contains("foo_key")),
            () -> Assertions.assertTrue(map.keySet().contains("bar_key")),
            () -> Assertions.assertFalse(map.keySet().contains(null))
        );
    }


    @Test
    void keySetRemove() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");
        map.keySet().remove("foo_key");

        Assertions.assertAll(
            () -> Assertions.assertEquals(2, map.size()),
            () -> Assertions.assertFalse(map.containsKey("foo_key")),
            () -> Assertions.assertTrue(map.containsKey("bar_key")),
            () -> Assertions.assertTrue(map.containsKey("baz_key"))
        );
    }

    @Test
    void values() {
        var map = new HashTable<String, String>();
        map.put("foo_key", "foo_val");
        map.put("bar_key", "bar_val");
        map.put("baz_key", "baz_val");
        map.put("fizz_key", "foo_val");
        Assertions.assertAll(
            () -> Assertions.assertEquals(4, map.values().size()),
            () -> Assertions.assertTrue(map.values().contains("foo_val")),
            () -> Assertions.assertTrue(map.values().contains("baz_val")),
            () -> Assertions.assertTrue(map.values().contains("bar_val")),
            () -> Assertions.assertEquals(2,
                Collections.frequency(map.values(), "foo_val")),
            () -> Assertions.assertEquals(1,
                Collections.frequency(map.values(), "bar_val")),
            () -> Assertions.assertEquals(1,
                Collections.frequency(map.values(), "baz_val"))
        );
    }
}
