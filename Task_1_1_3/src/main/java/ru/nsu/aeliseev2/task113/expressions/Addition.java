package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

/**
 * A binary expression that computes the sum of the values of its two operands.
 */
public class Addition extends BinaryExpression {
    /**
     * Constructs a new addition expression.
     *
     * @param left  The first operand.
     * @param right The second operand.
     */
    public Addition(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return left.evaluate(context) + right.evaluate(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression differentiate(String variableName) {
        var leftDiff = left.differentiate(variableName);
        var rightDiff = right.differentiate(variableName);
        return new Addition(leftDiff, rightDiff);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression optimize() {
        var leftOpt = left.optimize();
        var rightOpt = right.optimize();
        if (leftOpt instanceof Number leftNum && rightOpt instanceof Number rightNum) {
            return new Number(leftNum.getValue() + rightNum.getValue());
        }
        if (leftOpt.equals(Number.ZERO)) {
            return rightOpt;
        }
        if (rightOpt.equals(Number.ZERO)) {
            return leftOpt;
        }
        if (rightOpt instanceof Negation negation) {
            return new Subtraction(leftOpt, negation.getInner());
        }
        if (rightOpt instanceof Number number && number.getValue() < 0) {
            return new Subtraction(leftOpt, new Number(-number.getValue()));
        }
        return new Addition(leftOpt, rightOpt);
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
            return String.format("%s + (%s)", left, right);
        }
        return String.format("%s + %s", left, right);
    }
}
