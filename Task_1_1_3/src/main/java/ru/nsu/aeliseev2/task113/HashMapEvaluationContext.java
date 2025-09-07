package ru.nsu.aeliseev2.task113;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class HashMapEvaluationContext implements EvaluationContext {
    private final HashMap<String, Double> variableValues = new HashMap<>();

    public void setVariable(String name, double value) {
        variableValues.put(name, value);
    }

    public double getVariable(String name) {
        if (!variableValues.containsKey(name)) {
            throw new NoSuchElementException(String.format("Variable %s not found.", name));
        }
        return variableValues.get(name);
    }
}
