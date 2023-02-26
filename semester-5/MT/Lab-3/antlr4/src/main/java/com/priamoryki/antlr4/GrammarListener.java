// Generated from java-escape by ANTLR 4.11.1

package com.priamoryki.antlr4;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#code}.
	 * @param ctx the parse tree
	 */
	void enterCode(GrammarParser.CodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#code}.
	 * @param ctx the parse tree
	 */
	void exitCode(GrammarParser.CodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#ifElse}.
	 * @param ctx the parse tree
	 */
	void enterIfElse(GrammarParser.IfElseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#ifElse}.
	 * @param ctx the parse tree
	 */
	void exitIfElse(GrammarParser.IfElseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#if}.
	 * @param ctx the parse tree
	 */
	void enterIf(GrammarParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#if}.
	 * @param ctx the parse tree
	 */
	void exitIf(GrammarParser.IfContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#else}.
	 * @param ctx the parse tree
	 */
	void enterElse(GrammarParser.ElseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#else}.
	 * @param ctx the parse tree
	 */
	void exitElse(GrammarParser.ElseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(GrammarParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(GrammarParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#input}.
	 * @param ctx the parse tree
	 */
	void enterInput(GrammarParser.InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#input}.
	 * @param ctx the parse tree
	 */
	void exitInput(GrammarParser.InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(GrammarParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(GrammarParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(GrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(GrammarParser.ExpressionContext ctx);
}