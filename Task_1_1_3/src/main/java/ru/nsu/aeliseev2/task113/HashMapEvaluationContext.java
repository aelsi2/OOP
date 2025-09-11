package ru.nsu.aeliseev2.task113;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * An implementation of {@code EvaluationContext} based on a {@code HashMap}.
 */
public class HashMapEvaluationContext implements EvaluationContext {
    private final HashMap<String, Double> variableValues = new HashMap<>();

    /**
     * Sets the value of a variable (possibly overwriting the previous value).
     *
     * @param name The name of the variable.
     * @param value The value of the variable.
     */
    public void setVariable(String name, double value) {
        variableValues.put(name, value);
    }

    /**
     * {@inheritDoc}
     */
    public double getVariable(String name) {
        if (!variableValues.containsKey(name)) {
            throw new NoSuchElementException(String.format("Variable %s not found.", name));
        }
        return variableValues.get(name);
    }
}
