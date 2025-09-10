grammar EvaluationContext;

@header {
package ru.nsu.aeliseev2.task113.parsers;

import ru.nsu.aeliseev2.task113.HashMapEvaluationContext;
}

variableDefinition[HashMapEvaluationContext context]
    : WS? VARIABLE WS? '=' WS? NUMBER WS?
    { context.setVariable($VARIABLE.text, Double.parseDouble($NUMBER.text));}
    ;

evaluationContext returns [HashMapEvaluationContext context]
    @init {
        $context = new HashMapEvaluationContext();
    }
    : variableDefinition[$context] (';' variableDefinition[$context])* EOF
    | WS? EOF
    ;

VARIABLE : [a-zA-Z_][a-zA-Z_0-9]* ;
NUMBER  : '-'?[0-9]+('.'[0-9]+)? ;
WS : [ \t\r\n]+ -> skip ;