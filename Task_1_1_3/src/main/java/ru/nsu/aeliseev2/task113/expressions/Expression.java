package ru.nsu.aeliseev2.task113.expressions;

import ru.nsu.aeliseev2.task113.EvaluationContext;

public abstract class Expression {
    public abstract double evaluate(EvaluationContext context);

    public abstract Expression differentiate(String variableName);

    public abstract Expression optimize();
}
