package ru.nsu.aeliseev2.task121.test;

import ru.nsu.aeliseev2.task121.AdjacencyListGraph;
import ru.nsu.aeliseev2.task121.Graph;

class AdjacencyListGraphTests extends GraphTests {
    @Override
    Graph<String> createGraph() {
        return new AdjacencyListGraph<>();
    }
}
