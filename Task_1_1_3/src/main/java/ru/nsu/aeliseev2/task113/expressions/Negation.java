package ru.nsu.aeliseev2.task113.expressions;

import java.util.Objects;
import ru.nsu.aeliseev2.task113.EvaluationContext;

public class Negation extends Expression {
    private final Expression inner;

    public Negation(Expression inner) {
        this.inner = inner;
    }

    @Override
    public double evaluate(EvaluationContext context) {
        return -inner.evaluate(context);
    }

    @Override
    public Expression differentiate(String variableName) {
        return new Negation(inner.differentiate(variableName));
    }

    @Override
    public Expression optimize() {
        var innerOpt = inner.optimize();
        if (innerOpt instanceof Number number) {
            return new Number(-number.getValue());
        }
        if (innerOpt instanceof Negation negation) {
            return negation.inner;
        }
        if (innerOpt instanceof Subtraction subtraction) {
            return new Subtraction(subtraction.right, subtraction.left);
        }
        return new Negation(innerOpt);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Negation negation && inner.equals(negation);
    }

    @Override
    public int hashCode() {
        return Objects.hash("negation", inner);
    }

    @Override
    public String toString() {
        return String.format("-(%s)", inner);
    }
}
