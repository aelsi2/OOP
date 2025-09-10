grammar EvaluationContext;

@header {
package ru.nsu.aeliseev2.task113.parsers;

import ru.nsu.aeliseev2.task113.HashMapEvaluationContext;
}

varDecl[HashMapEvaluationContext ctx]
    : WS? VARIABLE WS? '=' WS? NUMBER WS?
    { ctx.setVariable($VARIABLE.text, Double.parseDouble($NUMBER.text));}
    ;

evalContext returns [HashMapEvaluationContext ctx]
    @init {
        $ctx = new HashMapEvaluationContext();
    }
    : varDecl[$ctx] (';' varDecl[$ctx])*
    ;

VARIABLE : [a-zA-Z_][a-zA-Z_0-9]* ;
NUMBER  : '-'?[0-9]+('.'[0-9]+)? ;
WS : [ \t\r\n]+ -> skip ;