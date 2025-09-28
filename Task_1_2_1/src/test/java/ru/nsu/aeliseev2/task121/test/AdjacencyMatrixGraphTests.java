package ru.nsu.aeliseev2.task121.test;

import ru.nsu.aeliseev2.task121.AdjacencyMatrixGraph;
import ru.nsu.aeliseev2.task121.Graph;

class AdjacencyMatrixGraphTests extends GraphTests {
    @Override
    Graph<String> createGraph() {
        return new AdjacencyMatrixGraph<>();
    }
}
