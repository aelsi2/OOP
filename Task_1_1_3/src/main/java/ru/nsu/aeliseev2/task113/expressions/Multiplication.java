package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

/**
 * A binary expression that computes the product of the values of its two operands.
 */
public class Multiplication extends BinaryExpression {
    /**
     * Constructs a new multiplication expression.
     *
     * @param left  The first operand.
     * @param right The second operand.
     */
    public Multiplication(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return left.evaluate(context) * right.evaluate(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression differentiate(String variableName) {
        var leftDiff = left.differentiate(variableName);
        var rightDiff = right.differentiate(variableName);
        return new Addition(
            new Multiplication(leftDiff, right),
            new Multiplication(left, rightDiff));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression optimize() {
        var leftOpt = left.optimize();
        var rightOpt = right.optimize();
        if (leftOpt instanceof Number leftNum && rightOpt instanceof Number rightNum) {
            return new Number(leftNum.getValue() * rightNum.getValue());
        }
        if (leftOpt.equals(Number.ZERO)) {
            return Number.ZERO;
        }
        if (rightOpt.equals(Number.ZERO)) {
            return Number.ZERO;
        }
        if (leftOpt.equals(Number.ONE)) {
            return rightOpt;
        }
        if (rightOpt.equals(Number.ONE)) {
            return leftOpt;
        }
        return new Multiplication(leftOpt, rightOpt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String leftString = left.toString();
        if (left instanceof Subtraction || left instanceof Addition) {
            leftString = "(" + leftString + ")";
        }
        String rightString = right.toString();
        if (!(right instanceof Variable)
            && !(right instanceof Number number && number.getValue() >= 0)) {
            rightString = "(" + rightString + ")";
        }
        return String.format("%s * %s", leftString, rightString);
    }
}
