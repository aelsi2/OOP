package ru.nsu.aeliseev2.task113;

public interface EvaluationContext {
    EvaluationContext EMPTY = name -> 0;

    double getVariable(String name);
}
