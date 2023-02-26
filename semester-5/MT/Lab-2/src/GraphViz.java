import parser.Node;
import parser.Parser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Pavel Lymar
 */
public class GraphViz {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             FileOutputStream stream = new FileOutputStream("src/graphviz/input.txt", false)) {
            System.out.println("Enter your expression: ");
            Parser parser = new Parser(scanner.nextLine());
            Node node = parser.parse();
            StringBuilder builder = new StringBuilder("digraph {\n");

            AtomicInteger maxId = new AtomicInteger();
            ArrayDeque<Integer> nums = new ArrayDeque<>();
            nums.add(maxId.get());
            node.bfs(n -> {
                int num = nums.pop();
                builder.append(
                        String.format("\t%d [label=\"%s\", color=cyan, style=filled]\n", num, n.getValue())
                );
                n.getChildren().forEach(child -> {
                    maxId.getAndIncrement();
                    nums.add(maxId.get());
                    builder.append(
                            String.format("\t%d -> %d\n", num, maxId.get())
                    );
                });
            });
            stream.write(builder.append("}\n").toString().getBytes());
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
