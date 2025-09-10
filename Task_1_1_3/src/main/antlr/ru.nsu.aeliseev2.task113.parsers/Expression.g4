grammar Expression;

@header {
package ru.nsu.aeliseev2.task113.parsers;

import ru.nsu.aeliseev2.task113.expressions.Expression;
import ru.nsu.aeliseev2.task113.expressions.Number;
import ru.nsu.aeliseev2.task113.expressions.Variable;
import ru.nsu.aeliseev2.task113.expressions.Negation;
import ru.nsu.aeliseev2.task113.expressions.Addition;
import ru.nsu.aeliseev2.task113.expressions.Subtraction;
import ru.nsu.aeliseev2.task113.expressions.Multiplication;
import ru.nsu.aeliseev2.task113.expressions.Division;
}

atom returns [Expression expression]
    :   WS? NUMBER WS?          {$expression = new Number(Double.parseDouble($NUMBER.text));}
    |   WS? VARIABLE WS?        {$expression = new Variable($VARIABLE.text);}
    |   WS? '(' expr ')' WS?    {$expression = $expr.expression;}
    ;

neg returns [Expression expression]
    :   '-' neg         {$expression = new Negation($neg.expression);}
    |   atom            {$expression = $atom.expression;}
    ;

mulDiv returns [Expression expression]
    :   neg {$expression = $neg.expression;} (
        : '*' neg {$expression = new Multiplication($expression, $neg.expression);}
        | '/' neg {$expression = new Division($expression, $neg.expression);}
    )*
    ;

addSub returns [Expression expression]
    :   mulDiv {$expression = $mulDiv.expression;} (
        : '+' mulDiv {$expression = new Addition($expression, $mulDiv.expression);}
        | '-' mulDiv {$expression = new Subtraction($expression, $mulDiv.expression);}
    )*
    ;

expr returns [Expression expression]: addSub {$expression = $addSub.expression;} ;

VARIABLE : [a-zA-Z_][a-zA-Z_0-9]* ;
NUMBER  : '-'?[0-9]+('.'[0-9]+)? ;
WS : [ \t\r\n] -> skip ;