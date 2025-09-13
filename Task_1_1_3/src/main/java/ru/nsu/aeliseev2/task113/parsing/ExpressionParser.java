package ru.nsu.aeliseev2.task113.parsing;

import ru.nsu.aeliseev2.task113.expressions.Addition;
import ru.nsu.aeliseev2.task113.expressions.Division;
import ru.nsu.aeliseev2.task113.expressions.Expression;
import ru.nsu.aeliseev2.task113.expressions.Multiplication;
import ru.nsu.aeliseev2.task113.expressions.Negation;
import ru.nsu.aeliseev2.task113.expressions.Number;
import ru.nsu.aeliseev2.task113.expressions.Subtraction;
import ru.nsu.aeliseev2.task113.expressions.Variable;

public class ExpressionParser {
    private final TokenReader<ExpressionTokenData> lexer;

    public ExpressionParser(TokenReader<ExpressionTokenData> lexer) {
        this.lexer = lexer;
    }

    private Expression parseAtom() {
        var token = lexer.consume();
        if (token.data() instanceof ExpressionTokenData.Name(String value)) {
            if (value.equalsIgnoreCase("nan")) {
                return new Number(Double.NaN);
            } else if (value.equalsIgnoreCase("inf")) {
                return new Number(Double.POSITIVE_INFINITY);
            }
            return new Variable(value);
        }
        if (token.data() instanceof ExpressionTokenData.Number(double value)) {
            return new Number(value);
        }
        if (token.data() == ExpressionTokenData.LEFT_PAREN) {
            var expr = parseAddSub();
            lexer.consume(ExpressionTokenData.RIGHT_PAREN);
            return expr;
        }
        throw new UnexpectedExprTokenException(token);
    }

    private Expression parseNeg() {
        if (lexer.peek().data() != ExpressionTokenData.MINUS) {
            return parseAtom();
        }
        lexer.consume();
        var token = lexer.peek();
        if (token.data() instanceof ExpressionTokenData.Name(String value)) {
            if (value.equalsIgnoreCase("inf")) {
                lexer.consume();
                return new Number(Double.NEGATIVE_INFINITY);
            }
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
            if (token.data() == ExpressionTokenData.MULTIPLY) {
                lexer.consume();
                expression = new Multiplication(expression, parseNeg());
            } else if (token.data() == ExpressionTokenData.DIVIDE) {
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
            if (token.data() == ExpressionTokenData.PLUS) {
                lexer.consume();
                expression = new Addition(expression, parseMulDiv());
            } else if (token.data() == ExpressionTokenData.MINUS) {
                lexer.consume();
                expression = new Subtraction(expression, parseMulDiv());
            } else {
                break;
            }
        }
        return expression;
    }

    public Expression parseExpression() {
        var expression = parseAddSub();
        lexer.consume(ExpressionTokenData.EOF);
        return expression;
    }
}
