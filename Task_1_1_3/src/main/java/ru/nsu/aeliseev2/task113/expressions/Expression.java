package ru.nsu.aeliseev2.task113.expressions;

import java.util.Objects;
import ru.nsu.aeliseev2.task113.EvaluationContext;
import ru.nsu.aeliseev2.task113.parsing.ExpressionLexer;
import ru.nsu.aeliseev2.task113.parsing.ExpressionParseException;
import ru.nsu.aeliseev2.task113.parsing.ExpressionParser;

/**
 * Represents an arithmetic expression with zero or more variables.
 */
public abstract class Expression {
    /**
     * Evaluates the expression in the provided context.
     *
     * @param context A context containing concrete variable values.
     * @return The result of evaluation, a double-precision floating point number.
     */
    public abstract double evaluate(EvaluationContext context);

    /**
     * Symbolically computes the partial derivative of the expression with respect to the specified
     * variable.
     *
     * @param variableName The name of the variable.
     * @return The derivative of the expression, also an expression.
     */
    public abstract Expression differentiate(String variableName);

    /**
     * Optimizes the expression by removing unnecessary nesting and precomputing constant values.
     *
     * @return The optimized expression.
     */
    public abstract Expression optimize();

    /**
     * Converts the expression to a string representation.
     */
    @Override
    public abstract String toString();

    /**
     * Compares the expression to an object.
     *
     * @param obj The other object
     * @return {@code true}, if {@code obj} is an expression with the same structure as the current
     *     one (same type and parameters), {@code false} otherwise.
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Computes a hash code for the current expression.
     *
     * @return The computed hash code.
     */
    @Override
    public abstract int hashCode();

    /**
     * Parses an expression from a string.
     *
     * @param string The input string.
     * @return The parsed expression.
     * @throws ExpressionParseException The input string does not contain a valid expression.
     */
    public static Expression parse(String string) throws ExpressionParseException {
        Objects.requireNonNull(string, "string must be non-null.");
        var lexer = new ExpressionLexer(string);
        var parser = new ExpressionParser(lexer);
        return parser.parseExpression();
    }
}
