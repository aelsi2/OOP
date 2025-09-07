package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

public class Variable extends Expression{
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public double evaluate(EvaluationContext context) {
        return context.getVariable(name);
    }

    @Override
    public Expression differentiate(String variableName) {
        if (name.equals(variableName)) {
            return Number.ONE;
        } else {
            return Number.ZERO;
        }
    }

    @Override
    public Expression optimize() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable variable && name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
