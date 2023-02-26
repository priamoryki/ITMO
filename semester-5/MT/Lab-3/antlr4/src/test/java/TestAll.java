import com.priamoryki.GrammarWalker;
import com.priamoryki.antlr4.GrammarLexer;
import com.priamoryki.antlr4.GrammarParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Pavel Lymar
 */
public class TestAll {
    private String getCode(String code) {
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);
        ParseTree tree = parser.code();
        ParseTreeWalker walker = new ParseTreeWalker();
        GrammarWalker grammarWalker = new GrammarWalker();
        walker.walk(grammarWalker, tree);
        return grammarWalker.getResult();
    }

    @Test
    public void testDefault() {
        String pyCode = String.join(
                "\n",
                "a = int(input())",
                "b = int(input())",
                "print(a + b)",
                ""
        );
        String cCode = String.join(
                "\n",
                "int a, b;",
                "",
                "int main() {",
                "\tscanf(\"%d\", &a);",
                "\tscanf(\"%d\", &b);",
                "\tprintf(\"%d\\n\", a + b);",
                "\treturn 0;",
                "}",
                ""
        );
        Assert.assertEquals(cCode, getCode(pyCode));
    }

    @Test
    public void testThirdVariable() {
        String pyCode = String.join(
                "\n",
                "a = int(input())",
                "b = int(input())",
                "c = a + b",
                "print(c)",
                ""
        );
        String cCode = String.join(
                "\n",
                "int a, b, c;",
                "",
                "int main() {",
                "\tscanf(\"%d\", &a);",
                "\tscanf(\"%d\", &b);",
                "\tc = a + b;",
                "\tprintf(\"%d\\n\", c);",
                "\treturn 0;",
                "}",
                ""
        );
        Assert.assertEquals(cCode, getCode(pyCode));
    }

    @Test
    public void testDiv() {
        String pyCode = String.join(
                "\n",
                "a = int(input())",
                "b = int(input())",
                "print(a / b)",
                ""
        );
        String cCode = String.join(
                "\n",
                "int a, b;",
                "",
                "int main() {",
                "\tscanf(\"%d\", &a);",
                "\tscanf(\"%d\", &b);",
                "\tprintf(\"%f\\n\", a / b);",
                "\treturn 0;",
                "}",
                ""
        );
        Assert.assertEquals(cCode, getCode(pyCode));
    }

    @Test
    public void testDivAssigment() {
        String pyCode = String.join(
                "\n",
                "a = int(input())",
                "b = 1",
                "b /= a",
                "print(b)",
                ""
        );
        String cCode = String.join(
                "\n",
                "int a;",
                "float b;",
                "",
                "int main() {",
                "\tscanf(\"%d\", &a);",
                "\tb = 1;",
                "\tb /= a;",
                "\tprintf(\"%f\\n\", b);",
                "\treturn 0;",
                "}",
                ""
        );
        Assert.assertEquals(cCode, getCode(pyCode));
    }

    @Test
    public void testDivVariable() {
        String pyCode = String.join(
                "\n",
                "a = int(input())",
                "b = int(input())",
                "c = a / b",
                "print(c)",
                ""
        );
        String cCode = String.join(
                "\n",
                "int a, b;",
                "float c;",
                "",
                "int main() {",
                "\tscanf(\"%d\", &a);",
                "\tscanf(\"%d\", &b);",
                "\tc = a / b;",
                "\tprintf(\"%f\\n\", c);",
                "\treturn 0;",
                "}",
                ""
        );
        Assert.assertEquals(cCode, getCode(pyCode));
    }

    @Test
    public void testString() {
        String pyCode = String.join(
                "\n",
                "a = \"test\"",
                "print(a)",
                ""
        );
        String cCode = String.join(
                "\n",
                "char[] a;",
                "",
                "int main() {",
                "\ta = \"test\";",
                "\tprintf(\"%s\\n\", a);",
                "\treturn 0;",
                "}",
                ""
        );
        Assert.assertEquals(cCode, getCode(pyCode));
    }

    @Test
    public void testComplex() {
        String pyCode = String.join(
                "\n",
                "a = int(input())",
                "b = int(input())",
                "c = 0.1",
                "print(((a + b) + c / 4) * 3)",
                ""
        );
        String cCode = String.join(
                "\n",
                "int a, b;",
                "float c;",
                "",
                "int main() {",
                "\tscanf(\"%d\", &a);",
                "\tscanf(\"%d\", &b);",
                "\tc = 0.1;",
                "\tprintf(\"%f\\n\", ((a + b) + c / 4) * 3);",
                "\treturn 0;",
                "}",
                ""
        );
        Assert.assertEquals(cCode, getCode(pyCode));
    }

    @Test
    public void testTypes() {
        String pyCode = String.join(
                "\n",
                "a = int(input())",
                "b = input()",
                "print(a + b)",
                ""
        );
        try {
            getCode(pyCode);
        } catch (RuntimeException e) {
            Assert.assertEquals("Invalid types!", e.getMessage());
            return;
        }
        Assert.fail();
    }

    @Test
    public void testIf() {
        String pyCode = String.join(
                "\n",
                "a = input()",
                "if a:",
                "\tprint(a)",
                "print(a)",
                ""
        );
        String cCode = String.join(
                "\n",
                "char[] a;",
                "",
                "int main() {",
                "\tscanf(\"%s\", &a);",
                "\tif (a) {",
                "\t\tprintf(\"%s\\n\", a);",
                "\t}",
                "\tprintf(\"%s\\n\", a);",
                "\treturn 0;",
                "}",
                ""
        );
        Assert.assertEquals(cCode, getCode(pyCode));
    }

    @Test
    public void testIfComplex() {
        String pyCode = String.join(
                "\n",
                "a = input()",
                "if a:",
                "\tprint(a)",
                "\tprint(a)",
                "print(a)",
                ""
        );
        String cCode = String.join(
                "\n",
                "char[] a;",
                "",
                "int main() {",
                "\tscanf(\"%s\", &a);",
                "\tif (a) {",
                "\t\tprintf(\"%s\\n\", a);",
                "\t\tprintf(\"%s\\n\", a);",
                "\t}",
                "\tprintf(\"%s\\n\", a);",
                "\treturn 0;",
                "}",
                ""
        );
        Assert.assertEquals(cCode, getCode(pyCode));
    }
}
