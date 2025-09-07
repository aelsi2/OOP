package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

public class Multiplication extends BinaryExpression {
    public Multiplication(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double evaluate(EvaluationContext context) {
        return left.evaluate(context) * right.evaluate(context);
    }

    @Override
    public Expression differentiate(String variableName) {
        var leftDiff = left.differentiate(variableName);
        var rightDiff = right.differentiate(variableName);
        return new Addition(
            new Multiplication(leftDiff, right),
            new Multiplication(left, rightDiff));
    }

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

    @Override
    public String toString() {
        return String.format("(%s * %s)", left, right);
    }
}
