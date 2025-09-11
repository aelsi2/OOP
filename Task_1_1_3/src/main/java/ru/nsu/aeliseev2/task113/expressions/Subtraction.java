package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

/**
 * A binary expression that subtracts the value of the second operand
 * from the value of the first operand.
 */
public class Subtraction extends BinaryExpression {
    /**
     * Constructs the new subtraction expression.
     *
     * @param left  The first operand.
     * @param right The second operand.
     */
    public Subtraction(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return left.evaluate(context) - right.evaluate(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression differentiate(String variableName) {
        var leftDiff = left.differentiate(variableName);
        var rightDiff = right.differentiate(variableName);
        return new Subtraction(leftDiff, rightDiff);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression optimize() {
        var leftOpt = left.optimize();
        var rightOpt = right.optimize();
        if (leftOpt instanceof Number leftNum && rightOpt instanceof Number rightNum) {
            return new Number(leftNum.getValue() - rightNum.getValue());
        }
        if (leftOpt.equals(rightOpt)) {
            return Number.ZERO;
        }
        if (rightOpt.equals(Number.ZERO)) {
            return leftOpt;
        }
        if (leftOpt.equals(Number.ZERO)) {
            return new Negation(rightOpt);
        }
        if (rightOpt instanceof Negation negation) {
            return new Addition(leftOpt, negation.getInner());
        }
        if (rightOpt instanceof Number number && number.getValue() < 0) {
            return new Addition(leftOpt, new Number(-number.getValue()));
        }
        return new Subtraction(leftOpt, rightOpt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (right instanceof Addition
            || right instanceof Subtraction
            || right instanceof Negation
            || right instanceof Number number && number.getValue() < 0) {
            return String.format("%s - (%s)", left, right);
        }
        return String.format("%s - %s", left, right);
    }
}
