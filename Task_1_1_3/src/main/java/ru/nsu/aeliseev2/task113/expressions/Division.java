package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

public class Division extends BinaryExpression {
    public Division(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double evaluate(EvaluationContext context) {
        return left.evaluate(context) / right.evaluate(context);
    }

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

    @Override
    public String toString() {
        return String.format("(%s / %s)", left, right);
    }
}
