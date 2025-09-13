package ru.nsu.aeliseev2.task113.parsers;

import ru.nsu.aeliseev2.task113.EvaluationContext;
import ru.nsu.aeliseev2.task113.HashMapEvaluationContext;

/**
 * A parser for {@code EvaluationContext} strings.
 */
public class EvalContextParser implements AutoCloseable {
    private final TokenReader<EvalContextTokenData> lexer;

    /**
     * Constructs a new evaluation context parser.
     *
     * @param lexer The lexer to use.
     */
    public EvalContextParser(TokenReader<EvalContextTokenData> lexer) {
        this.lexer = lexer;
    }

    private String readName() {
        var token = lexer.consume();
        if (!(token.data() instanceof EvalContextTokenData.Name(String text))) {
            throw new UnexpectedCtxTokenException(token);
        }
        return text;
    }

    private double readValue() {
        var token = lexer.consume();
        boolean negative = false;
        if (token.data().equals(EvalContextTokenData.MINUS)) {
            negative = true;
            token = lexer.consume();
        }
        if (token.data().equals(EvalContextTokenData.NAN)) {
            return Double.NaN;
        }
        if (token.data().equals(EvalContextTokenData.INF)) {
            return negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        if (token.data() instanceof EvalContextTokenData.Number(double value)) {
            return negative ? -value : value;
        }
        throw new UnexpectedCtxTokenException(token);
    }

    private void parseVariableDefinition(HashMapEvaluationContext context) {
        var name = readName();
        var equals = lexer.consume();
        if (!equals.data().equals(EvalContextTokenData.EQUALS)) {
            throw new UnexpectedCtxTokenException(equals);
        }
        double value = readValue();
        context.setVariable(name, value);
    }

    /**
     * Parses the evaluation context.
     *
     * @return The parsed evaluation context.
     */
    public EvaluationContext parseEvaluationContext() {
        var evaluationContext = new HashMapEvaluationContext();
        if (lexer.peek().data().equals(EvalContextTokenData.EOF)) {
            return evaluationContext;
        }
        while (true) {
            parseVariableDefinition(evaluationContext);
            var token = lexer.consume();
            if (token.data().equals(EvalContextTokenData.EOF)) {
                break;
            }
            if (!token.data().equals(EvalContextTokenData.SEMICOLON)) {
                throw new UnexpectedCtxTokenException(token);
            }
        }
        return evaluationContext;
    }

    /**
     * Closes the lexer.
     *
     * @throws Exception Closing the lexer failed.
     */
    @Override
    public void close() throws Exception {
        lexer.close();
    }
}
