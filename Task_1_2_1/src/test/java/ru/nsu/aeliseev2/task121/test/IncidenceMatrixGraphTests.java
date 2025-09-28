package ru.nsu.aeliseev2.task121.test;

import ru.nsu.aeliseev2.task121.Graph;
import ru.nsu.aeliseev2.task121.IncidenceMatrixGraph;

class IncidenceMatrixGraphTests extends GraphTests {
    @Override
    Graph<String> createGraph() {
        return new IncidenceMatrixGraph<>();
    }
}
