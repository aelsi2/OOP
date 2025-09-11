package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

/**
 * A variable expression. The produced value depends on the evaluation context.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Constructs a new variable expression.
     *
     * @param name The name of the variable.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return context.getVariable(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression differentiate(String variableName) {
        if (name.equals(variableName)) {
            return Number.ONE;
        } else {
            return Number.ZERO;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression optimize() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable variable && name.equals(variable.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name;
    }
}
