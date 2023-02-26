import parser.Parser;

import java.text.ParseException;

class TestException extends Throwable {
    public TestException(String message) {
        super("TestException: " + message);
    }
}

class Main {
    public static void test(String text, String expected) throws TestException {
        String result;
        try {
            result = new Parser(text).parse().toString();
        } catch (ParseException e) {
            result = e.getMessage();
        }
        if (!expected.equals(result)) {
            throw new TestException("result \"" + result + "\" is incorrect for test \"" + text + "\"");
        }
    }

    public static void main(String[] args) throws TestException {
        test("var x: Array<Int>;", "var x:Array<Int>;");
        test("var x: Array<Array<Int>>;", "var x:Array<Array<Int>>;");
        test("var x: Array<Array<Array<Int>>>;", "var x:Array<Array<Array<Int>>>;");
        test("var x: Array<Map<Map<Int, String>, String>>;", "var x:Array<Map<Map<Int,String>,String>>;");

        test("var __x__: Array<Int>;", "var __x__:Array<Int>;");
        test("var prettyHardVariableName123: Array<Int>;", "var prettyHardVariableName123:Array<Int>;");

        test("var : Array<Array<Int>>;", "can't find variable name");
        test("var var: Array<Int>;", "can't find variable name");
        test("var Array: Array<Int>;", "can't find variable name");
        test("var Array<Int>: Array<Int>;", "can't find variable name");
        test("var Int: Array<Int>;", "can't find variable name");
        test("var x*: Array<Int>;", "can't find variable name");

        test("varx: Array<Int>;", "can't find var keyword");

        test("var x: Map<String, Int>;", "can't find Array type");
        test("var x: ArrayInt>;", "can't find Array type");

        test("var x: Array<Int;", "can't find \">\"");
        test("var x: Array<Int>", "can't find \";\"");

        test("var x: Array<Map<, String>>;", "can't find Map Key type");
        test("var x: Array<Map<Int, >>;", "can't find Map Value type");

        test("var x: Array<Int>;;", "expression is parsed, but next token exists");
    }
}