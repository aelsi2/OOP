package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

/**
 * A constant number expression.
 */
public class Number extends Expression {
    public final static Number ZERO = new Number(0);
    public final static Number ONE = new Number(1);

    private final double value;

    /**
     * Constructs a new number expression.
     *
     * @param value The value of the number.
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Gets the constant value of expression.
     *
     * @return The value of the expression.
     */
    public double getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression differentiate(String variableName) {
        return ZERO;
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
        return obj instanceof Number number && value == number.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%f", value);
    }
}
