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
    :   WS atom WS?     {$expression = $atom.expression;}
    |   NUMBER          {$expression = new Number(Double.parseDouble($NUMBER.text));}
    |   VARIABLE        {$expression = new Variable($VARIABLE.text);}
    |   '(' expr ')'    {$expression = $expr.expression;}
    ;

neg returns [Expression expression]
    :   '-' neg         {$expression = new Negation($neg.expression);}
    |   atom            {$expression = $atom.expression;}
    ;

mulDiv returns [Expression expression]
    :   neg                 {$expression = $neg.expression;}
    |   l=mulDiv '*' r=neg  {$expression = new Multiplication($l.expression, $r.expression);}
    |   l=mulDiv '/' r=neg  {$expression = new Division($l.expression, $r.expression);}
    ;

addSub returns [Expression expression]
    :   mulDiv                  {$expression = $mulDiv.expression;}
    |   l=addSub '+' r=mulDiv   {$expression = new Addition($l.expression, $r.expression);}
    |   l=addSub '-' r=mulDiv   {$expression = new Subtraction($l.expression, $r.expression);}
    ;

expr returns [Expression expression]
    :   WS? addSub WS?  {$expression = $addSub.expression;}
    ;

VARIABLE : [a-zA-Z_][a-zA-Z_0-9]* ;
NUMBER  : [0-9]+('.'[0-9]+)? ;
WS : [ \t\r\n] -> skip ;