package parser;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Pavel Lymar
 */
public class Node {
    private final String value;
    private final List<Node> children = new ArrayList<>();

    public Node(String value) {
        this.value = value;
    }

    public Node(String value, Node... children) {
        this(value);
        this.children.addAll(Arrays.asList(children));
    }

    public Node(String value, String... children) {
        this(value);
        this.children.addAll(Arrays.stream(children).map(Node::new).collect(Collectors.toList()));
    }

    public String getValue() {
        return value;
    }

    public List<Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        dfs(node -> builder.append(node.value));
        return builder.toString();
    }

    public void dfs(Consumer<Node> function) {
        function.accept(this);
        for (Node node : children) {
            node.dfs(function);
        }
    }

    public void bfs(Consumer<Node> function) {
        Deque<Node> q = new ArrayDeque<>();
        q.add(this);
        while (!q.isEmpty()) {
            Node n = q.pop();
            function.accept(n);
            q.addAll(n.getChildren());
        }
    }
}
