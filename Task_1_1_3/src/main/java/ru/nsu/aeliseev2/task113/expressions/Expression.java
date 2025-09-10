package ru.nsu.aeliseev2.task113.expressions;

import java.text.ParseException;
import java.util.Objects;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import ru.nsu.aeliseev2.task113.EvaluationContext;
import ru.nsu.aeliseev2.task113.parsers.ExpressionLexer;
import ru.nsu.aeliseev2.task113.parsers.ExpressionParser;

public abstract class Expression {
    public abstract double evaluate(EvaluationContext context);

    public abstract Expression differentiate(String variableName);

    public abstract Expression optimize();

    public static Expression parse(String string) throws ParseException {
        Objects.requireNonNull(string, "string must be non-null.");
        try {
            var lexer = new ExpressionLexer(CharStreams.fromString(string));
            var tokenStream = new CommonTokenStream(lexer);
            var parser = new ExpressionParser(tokenStream);
            return parser.expr().expression;
        }
        catch (RecognitionException recEx){
            int position = recEx.getOffendingToken().getStartIndex();
            String message = String.format(
                "Invalid expression '%s':\n%s",
                string,
                recEx.getMessage());
            var exception = new ParseException(message, position);
            exception.initCause(recEx);
            throw exception;
        }
    }
}
