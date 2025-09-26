package ru.nsu.aeliseev2.task121;

/**
 * An edge in a graph.
 *
 * @param from The start vertex.
 * @param to The end vertex.
 * @param <V> The type of the vertices.
 */
public record Edge<V>(V from, V to) {
}
