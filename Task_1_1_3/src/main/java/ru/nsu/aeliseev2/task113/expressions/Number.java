package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

public class Number extends Expression {
    public final static Number ZERO = new Number(0);
    public final static Number ONE = new Number(1);

    private final double value;

    public Number(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public double evaluate(EvaluationContext context) {
        return value;
    }

    @Override
    public Expression differentiate(String variableName) {
        return ZERO;
    }

    @Override
    public Expression optimize() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Number number && value == number.value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public String toString() {
        return String.format("%f", value);
    }
}
