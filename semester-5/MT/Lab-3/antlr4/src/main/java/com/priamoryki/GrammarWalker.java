package com.priamoryki;

import com.priamoryki.antlr4.GrammarBaseListener;
import com.priamoryki.antlr4.GrammarParser;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Pavel Lymar
 */
public class GrammarWalker extends GrammarBaseListener {
    private final String INT_TYPE = "int";
    private final String FLOAT_TYPE = "float";
    private final String STRING_TYPE = "char[]";

    private final Map<String, String> variables;
    private final StringBuilder code;
    private int tabsNum;
    private boolean lineSeparatorNeeded;
    private boolean hasElse;

    public GrammarWalker() {
        this.variables = new HashMap<>();
        this.code = new StringBuilder();
        this.tabsNum = 1;
        this.lineSeparatorNeeded = true;
    }

    private boolean addVariablesDeclaration(StringBuilder result, String type) {
        String vars = variables.entrySet().stream()
                .filter(entry -> entry.getValue().equals(type))
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
        if (vars.length() > 0) {
            result.append(type).append(" ").append(vars).append(";\n");
        }
        return !vars.isEmpty();
    }

    public String getResult() {
        StringBuilder result = new StringBuilder();
        boolean flag = addVariablesDeclaration(result, INT_TYPE);
        flag |= addVariablesDeclaration(result, FLOAT_TYPE);
        flag |= addVariablesDeclaration(result, STRING_TYPE);
        if (flag) {
            result.append("\n");
        }

        result.append("int main() {").append("\n");
        result.append(code);
        result.append("\t".repeat(tabsNum)).append("return 0").append(";").append("\n");
        result.append("}").append("\n");
        return result.toString();
    }

    private String getNumberType(String a) {
        return a.contains(".") ? FLOAT_TYPE : INT_TYPE;
    }

    private String getFormatSpecifier(String type) {
        return switch (type) {
            case INT_TYPE -> "%d";
            case FLOAT_TYPE -> "%f";
            case STRING_TYPE -> "%s";
            default -> "void*";
        };
    }

    private String getExpressionType(GrammarParser.ExpressionContext ctx) {
        if (ctx == null) {
            return INT_TYPE;
        }
        if (ctx.NUMBER() != null) {
            return getNumberType(ctx.NUMBER().getText());
        }
        if (ctx.STRING() != null) {
            return STRING_TYPE;
        }
        if (ctx.OPERATION() != null && ctx.OPERATION().getText().equals("/")) {
            return FLOAT_TYPE;
        }
        if (ctx.VARIABLE() != null) {
            if (!variables.containsKey(ctx.VARIABLE().getText())) {
                throw new ArithmeticException("Variable " + ctx.VARIABLE().getText() + " is not defined!");
            }
            return variables.get(ctx.VARIABLE().getText());
        }
        String type1 = getExpressionType(ctx.expression(0));
        String type2 = getExpressionType(ctx.expression(1));
        if (type1.equals(STRING_TYPE) && !type2.equals(STRING_TYPE)
                || !type1.equals(STRING_TYPE) && type2.equals(STRING_TYPE)) {
            throw new IllegalArgumentException("Invalid types!");
        }
        if (ctx.COMPARISON() != null) {
            if (type1.equals(STRING_TYPE) || type2.equals(STRING_TYPE)) {
                throw new IllegalArgumentException("Can't compare strings!");
            }
            return INT_TYPE;
        }
        return type1.equals(FLOAT_TYPE) || type2.equals(FLOAT_TYPE) ? FLOAT_TYPE : INT_TYPE;
    }

    @Override
    public void enterLine(GrammarParser.LineContext ctx) {
        lineSeparatorNeeded = true;
        code.append("\t".repeat(tabsNum));
    }

    @Override
    public void exitLine(GrammarParser.LineContext ctx) {
        if (lineSeparatorNeeded) {
            code.append(";\n");
        }
    }

    @Override
    public void enterIfElse(GrammarParser.IfElseContext ctx) {
        hasElse = ctx.else_() != null;
    }

    @Override
    public void enterIf(GrammarParser.IfContext ctx) {
        code.append("\t".repeat(tabsNum));
        code.append("if (").append(ctx.expression().getText()).append(") {\n");
        tabsNum++;
    }

    @Override
    public void exitIf(GrammarParser.IfContext ctx) {
        tabsNum--;
        code.append("\t".repeat(tabsNum));
        code.append("}");
        if (hasElse) {
            code.append(" else {");
        }
        code.append("\n");
    }

    @Override
    public void enterElse(GrammarParser.ElseContext ctx) {
        tabsNum++;
    }

    @Override
    public void exitElse(GrammarParser.ElseContext ctx) {
        tabsNum--;
        code.append("\t".repeat(tabsNum));
        code.append("}\n");
    }


    @Override
    public void exitInput(GrammarParser.InputContext ctx) {
        if (ctx.expression() != null) {
            String type = getExpressionType(ctx.expression());
            type = ctx.ASSIGNMENT().getText().equals("/=") ? FLOAT_TYPE : type;
            variables.put(ctx.VARIABLE().getText(), type);
            code.append(ctx.VARIABLE().getText()).append(" ").append(ctx.ASSIGNMENT()).append(" ").append(ctx.expression().getText());
            return;
        }
        String type = "";
        if (ctx.expression() == null && ctx.TYPE() == null) {
            type = STRING_TYPE;
        }
        if (ctx.TYPE() != null) {
            type = ctx.TYPE().getText();
            if (type.equals("str")) {
                type = STRING_TYPE;
            }
        }
        variables.put(ctx.VARIABLE().getText(), type);
        String format = getFormatSpecifier(type);
        code.append("scanf(\"").append(format).append("\", &").append(ctx.VARIABLE().getText()).append(")");
    }

    @Override
    public void exitPrint(GrammarParser.PrintContext ctx) {
        code.append("printf(\"");
        String format = getFormatSpecifier(getExpressionType(ctx.expression()));
        code.append(format).append("\\n\", ").append(ctx.expression().getText()).append(")");
    }
}
