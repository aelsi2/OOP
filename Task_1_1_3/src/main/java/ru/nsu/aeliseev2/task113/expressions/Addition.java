package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

public class Addition extends BinaryExpression {
    public Addition(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double evaluate(EvaluationContext context) {
        return left.evaluate(context) + right.evaluate(context);
    }

    @Override
    public Expression differentiate(String variableName) {
        var leftDiff = left.differentiate(variableName);
        var rightDiff = right.differentiate(variableName);
        return new Addition(leftDiff, rightDiff);
    }

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
        return new Addition(leftOpt, rightOpt);
    }

    @Override
    public String toString() {
        return String.format("(%s + %s)", left, right);
    }
}
