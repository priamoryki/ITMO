package src;

import src.operations.Component;
import src.parser.Parser;
import src.parser.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static Component expression;
    private static List<String> vars;
    private static Map<String, Boolean> varsToValues;
    private static int valid, invalid;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Parser parser = new Parser(new Source(scanner.nextLine()));
            expression = parser.parse();
            vars = new ArrayList<>(parser.getVars());
            varsToValues = vars.stream().collect(Collectors.toMap(value -> value, key -> false));
            recursion(0);
            if (valid == 0) {
                System.out.println("Unsatisfiable");
            } else if (invalid == 0) {
                System.out.println("Valid");
            } else {
                System.out.printf("Satisfiable and invalid, %d true and %d false cases\n", valid, invalid);
            }
        }
    }

    private static void recursion(int depth) {
        if (depth == vars.size()) {
            if (expression.evaluate(varsToValues)) {
                valid++;
            } else {
                invalid++;
            }
            return;
        }
        for (boolean value : Arrays.asList(false, true)) {
            varsToValues.put(vars.get(depth), value);
            recursion(depth + 1);
        }
    }
}