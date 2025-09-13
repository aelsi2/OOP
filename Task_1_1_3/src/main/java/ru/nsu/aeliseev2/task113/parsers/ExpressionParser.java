package ru.nsu.aeliseev2.task113.parsers;

import ru.nsu.aeliseev2.task113.expressions.Addition;
import ru.nsu.aeliseev2.task113.expressions.Division;
import ru.nsu.aeliseev2.task113.expressions.Expression;
import ru.nsu.aeliseev2.task113.expressions.Multiplication;
import ru.nsu.aeliseev2.task113.expressions.Negation;
import ru.nsu.aeliseev2.task113.expressions.Number;
import ru.nsu.aeliseev2.task113.expressions.Subtraction;
import ru.nsu.aeliseev2.task113.expressions.Variable;

/**
 * A parser for {@code Expression} strings.
 */
public class ExpressionParser implements AutoCloseable {
    private final TokenReader<ExpressionTokenData> lexer;

    /**
     * Constructs a new expression parser.
     *
     * @param lexer The lexer to use.
     */
    public ExpressionParser(TokenReader<ExpressionTokenData> lexer) {
        this.lexer = lexer;
    }

    private Expression parseAtom() {
        var token = lexer.consume();
        if (token.data() instanceof ExpressionTokenData.Name(String value)) {
            return new Variable(value);
        }
        if (token.data().equals(ExpressionTokenData.INF)) {
            return new Number(Double.POSITIVE_INFINITY);
        }
        if (token.data().equals(ExpressionTokenData.NAN)) {
            return new Number(Double.NaN);
        }
        if (token.data() instanceof ExpressionTokenData.Number(double value)) {
            return new Number(value);
        }
        if (token.data().equals(ExpressionTokenData.LEFT_PAREN)) {
            var expr = parseAddSub();
            token = lexer.consume();
            if (!token.data().equals(ExpressionTokenData.RIGHT_PAREN)) {
                throw new UnexpectedExprTokenException(token);
            }
            return expr;
        }
        throw new UnexpectedExprTokenException(token);
    }

    private Expression parseNeg() {
        if (!lexer.peek().data().equals(ExpressionTokenData.MINUS)) {
            return parseAtom();
        }
        lexer.consume();
        var token = lexer.peek();
        if (token.data().equals(ExpressionTokenData.INF)) {
            lexer.consume();
            return new Number(Double.NEGATIVE_INFINITY);
        }
        if (token.data() instanceof ExpressionTokenData.Number(double value)) {
            lexer.consume();
            return new Number(-value);
        }
        return new Negation(parseNeg());
    }

    private Expression parseMulDiv() {
        var expression = parseNeg();
        while (true) {
            var token = lexer.peek();
            if (token.data().equals(ExpressionTokenData.MULTIPLY)) {
                lexer.consume();
                expression = new Multiplication(expression, parseNeg());
            } else if (token.data().equals(ExpressionTokenData.DIVIDE)) {
                lexer.consume();
                expression = new Division(expression, parseNeg());
            } else {
                break;
            }
        }
        return expression;
    }

    private Expression parseAddSub() {
        var expression = parseMulDiv();
        while (true) {
            var token = lexer.peek();
            if (token.data().equals(ExpressionTokenData.PLUS)) {
                lexer.consume();
                expression = new Addition(expression, parseMulDiv());
            } else if (token.data().equals(ExpressionTokenData.MINUS)) {
                lexer.consume();
                expression = new Subtraction(expression, parseMulDiv());
            } else {
                break;
            }
        }
        return expression;
    }

    /**
     * Parses the expression.
     *
     * @return The parsed expression.
     */
    public Expression parseExpression() {
        var expression = parseAddSub();
        var token = lexer.consume();
        if (!token.data().equals(ExpressionTokenData.EOF)) {
            throw new UnexpectedExprTokenException(token);
        }
        return expression;
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
