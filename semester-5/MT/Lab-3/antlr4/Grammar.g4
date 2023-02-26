grammar Grammar;
@header {
package com.priamoryki.antlr4;
}

PRINT: 'print';
IF: 'if';
ELSE: 'else';
INPUT: 'input()';
TYPE: 'int' | 'float';

NEW_LINE: '\n';
TAB: '\t' | '    ';
SPACE: ' ';
COLON: ':';
L_BRACKET: '(';
R_BRACKET: ')';
ASSIGNMENT: '=' | '+=' | '-=' | '*=' | '/=';
OPERATION: '+' | '-' | '*' | '/';
COMPARISON: '!=' | '>' | '>=' | '==' | '<=' | '<';

NUMBER: '0' | '0.' [1-9]* | [1-9][0-9]*('.' [0-9]*)?;
VARIABLE: [a-zA-Z_][a-zA-Z_0-9]*;
STRING: '"' [a-zA-Z_0-9]* '"';

code: (ifElse | line)+ EOF;
ifElse: if else?;
if: IF SPACE expression COLON NEW_LINE (TAB line)+;
else: (ELSE COLON NEW_LINE (TAB line)+);
line: (input | print) NEW_LINE;
input: VARIABLE SPACE ASSIGNMENT SPACE (INPUT | TYPE L_BRACKET INPUT R_BRACKET | expression);
print: PRINT L_BRACKET expression R_BRACKET;
expression: NUMBER | VARIABLE | STRING | expression SPACE (OPERATION | COMPARISON) SPACE expression | L_BRACKET expression R_BRACKET;
