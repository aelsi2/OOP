package ru.nsu.aeliseev2.task113.expressions;

import java.text.ParseException;
import java.util.Objects;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import ru.nsu.aeliseev2.task113.EvaluationContext;
import ru.nsu.aeliseev2.task113.parsers.ExpressionLexer;
import ru.nsu.aeliseev2.task113.parsers.ExpressionParser;

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
     * @throws ParseException The input string does not contain a valid expression.
     */
    public static Expression parse(String string) throws ParseException {
        Objects.requireNonNull(string, "string must be non-null.");
        try {
            var lexer = new ExpressionLexer(CharStreams.fromString(string));
            var tokenStream = new CommonTokenStream(lexer);
            var parser = new ExpressionParser(tokenStream);
            parser.setErrorHandler(new BailErrorStrategy());
            return parser.root().expression;
        } catch (ParseCancellationException cancelEx) {
            int position = -1;
            if (cancelEx.getCause() instanceof RecognitionException recEx) {
                position = recEx.getOffendingToken().getStartIndex();
            }
            String message = String.format("Invalid expression '%s'", string);
            var exception = new ParseException(message, position);
            exception.initCause(cancelEx);
            throw exception;
        }
    }
}
