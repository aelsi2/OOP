package ru.nsu.aeliseev2.task113.expressions;

import java.util.Objects;
import ru.nsu.aeliseev2.task113.EvaluationContext;

/**
 * A unary expression that negates its operand.
 */
public class Negation extends Expression {
    private final Expression inner;

    /**
     * Constructs a new negation expression.
     *
     * @param inner The operand.
     */
    public Negation(Expression inner) {
        this.inner = inner;
    }

    /**
     * Gets the inner expression (the operand) of this expression.
     *
     * @return The inner expression.
     */
    public Expression getInner(){
        return inner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return -inner.evaluate(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression differentiate(String variableName) {
        return new Negation(inner.differentiate(variableName));
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Negation negation && inner.equals(negation.inner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash("negation", inner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("-(%s)", inner);
    }
}
