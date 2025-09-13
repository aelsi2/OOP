package ru.nsu.aeliseev2.task113;

import java.util.NoSuchElementException;
import java.util.Objects;
import ru.nsu.aeliseev2.task113.parsers.EvalContextLexer;
import ru.nsu.aeliseev2.task113.parsers.EvalContextParser;
import ru.nsu.aeliseev2.task113.parsers.ExpressionParseException;

/**
 * A map containing the values of the variables needed for the evaluation of an expression.
 */
public interface EvaluationContext {
    /**
     * An empty evaluation context that always throws an exception.
     */
    EvaluationContext EMPTY = name -> {
        throw new NoSuchElementException("The context is empty");
    };

    /**
     * Gets the value of a variable.
     *
     * @param name The name of the variable.
     * @return The value of the variable.
     * @throws java.util.NoSuchElementException The requested variable is not defined in the current
     *                                          context.
     */
    double getVariable(String name);

    /**
     * Parses an evaluation context from a string.
     *
     * @param string The input string.
     * @return The parsed evaluation context.
     * @throws ExpressionParseException The input string does not contain a valid evaluation
     *                                  context.
     */
    static EvaluationContext parse(String string) throws ExpressionParseException {
        Objects.requireNonNull(string, "string must be non-null.");
        var lexer = new EvalContextLexer(string);
        var parser = new EvalContextParser(lexer);
        return parser.parseEvaluationContext();
    }
}
