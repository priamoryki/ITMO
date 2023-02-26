package com.priamoryki;

import com.priamoryki.antlr4.GrammarLexer;
import com.priamoryki.antlr4.GrammarParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * @author Pavel Lymar
 */
public class Main {
    public static void main(String[] args) {
        String pyCode = String.join(
                "\n",
                "a = input()",
                "b = input()",
                "if a:",
                "\tprint(a)",
                "else:",
                "\tprint(b)",
                "print(a)",
                ""
        );
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(pyCode));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);
        ParseTree tree = parser.code();
        ParseTreeWalker walker = new ParseTreeWalker();
        GrammarWalker grammarWalker = new GrammarWalker();
        walker.walk(grammarWalker, tree);
        System.out.println(grammarWalker.getResult());
    }
}