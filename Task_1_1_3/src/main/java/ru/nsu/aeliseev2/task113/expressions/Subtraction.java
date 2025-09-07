package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

public class Subtraction extends BinaryExpression {
    public Subtraction(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double evaluate(EvaluationContext context) {
        return left.evaluate(context) - right.evaluate(context);
    }

    @Override
    public Expression differentiate(String variableName) {
        var leftDiff = left.differentiate(variableName);
        var rightDiff = right.differentiate(variableName);
        return new Subtraction(leftDiff, rightDiff);
    }

    @Override
    public Expression optimize() {
        var leftOpt = left.optimize();
        var rightOpt = right.optimize();
        if (leftOpt instanceof Number leftNum && rightOpt instanceof Number rightNum) {
            return new Number(leftNum.getValue() + rightNum.getValue());
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
        return new Subtraction(leftOpt, rightOpt);
    }

    @Override
    public String toString() {
        return String.format("(%s - %s)", left, right);
    }
}
