package ru.nsu.aeliseev2.task113;

import java.text.ParseException;
import java.util.Objects;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import ru.nsu.aeliseev2.task113.parsers.EvaluationContextLexer;
import ru.nsu.aeliseev2.task113.parsers.EvaluationContextParser;

public interface EvaluationContext {
    EvaluationContext EMPTY = name -> 0;

    double getVariable(String name);

    static EvaluationContext parse(String string) throws ParseException {
        Objects.requireNonNull(string, "string must be non-null.");
        try {
            var lexer = new EvaluationContextLexer(CharStreams.fromString(string));
            var tokenStream = new CommonTokenStream(lexer);
            var parser = new EvaluationContextParser(tokenStream);
            return parser.evalContext().ctx;
        }
        catch (RecognitionException recEx){
            int position = recEx.getOffendingToken().getStartIndex();
            String message = String.format(
                "Invalid context string '%s':\n%s",
                string,
                recEx.getMessage());
            var exception = new ParseException(message, position);
            exception.initCause(recEx);
            throw exception;
        }
    }
}
