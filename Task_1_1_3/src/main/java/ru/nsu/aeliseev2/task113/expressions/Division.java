package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

/**
 * A binary expression that divides the value of the first operand
 * by the value of the second operand.
 */
public class Division extends BinaryExpression {
    /**
     * Constructs a new division expression.
     *
     * @param dividend The dividend (the numerator).
     * @param divider  The divider (the denominator).
     */
    public Division(Expression dividend, Expression divider) {
        super(dividend, divider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return left.evaluate(context) / right.evaluate(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression differentiate(String variableName) {
        var leftDiff = left.differentiate(variableName);
        var rightDiff = right.differentiate(variableName);
        var numerator = new Addition(
            new Multiplication(leftDiff, right),
            new Multiplication(left, rightDiff));
        var denominator = new Multiplication(left, left);
        return new Division(numerator, denominator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression optimize() {
        var leftOpt = left.optimize();
        var rightOpt = right.optimize();
        if (leftOpt instanceof Number leftNum && rightOpt instanceof Number rightNum) {
            return new Number(leftNum.getValue() / rightNum.getValue());
        }
        if (leftOpt.equals(rightOpt)) {
            return Number.ONE;
        }
        if (leftOpt.equals(Number.ZERO)) {
            return Number.ZERO;
        }
        if (rightOpt.equals(Number.ONE)) {
            return leftOpt;
        }
        return new Division(leftOpt, rightOpt);
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
        return String.format("%s / %s", leftString, rightString);
    }
}
