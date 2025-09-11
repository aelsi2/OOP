package ru.nsu.aeliseev2.task113;

import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import ru.nsu.aeliseev2.task113.parsers.EvaluationContextLexer;
import ru.nsu.aeliseev2.task113.parsers.EvaluationContextParser;

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
     * @exception java.util.NoSuchElementException The requested variable is not defined in the
     * current context.
     */
    double getVariable(String name);

    /**
     * Parses an evaluation context from a string.
     *
     * @param string The input string.
     * @return The parsed evaluation context.
     * @throws ParseException The input string does not contain a valid evaluation context.
     */
    static EvaluationContext parse(String string) throws ParseException {
        Objects.requireNonNull(string, "string must be non-null.");
        try {
            var lexer = new EvaluationContextLexer(CharStreams.fromString(string));
            var tokenStream = new CommonTokenStream(lexer);
            var parser = new EvaluationContextParser(tokenStream);
            parser.setErrorHandler(new BailErrorStrategy());
            return parser.evaluationContext().context;
        } catch (ParseCancellationException cancelEx) {
            int position = -1;
            if (cancelEx.getCause() instanceof RecognitionException recEx) {
                position = recEx.getOffendingToken().getStartIndex();
            }
            String message = String.format("Invalid context string '%s'", string);
            var exception = new ParseException(message, position);
            exception.initCause(cancelEx);
            throw exception;
        }
    }
}
