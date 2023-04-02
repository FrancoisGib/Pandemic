package pandemic;

import java.util.Stack;

public class Node {
    Node previous;
    Town current;

    public Node(Node previous, Town current) {
        this.previous = previous;
        this.current = current;
    }

    public Town getCurrent() {
        return this.current;
    }
    
    public Node getPrevious() {
        return this.previous;
    }

    public Stack<Town> getPath() {
        Stack<Town> path = new Stack<Town>();
        while (this.previous != null) {
            path.add(this.current);
            this.current = this.previous.getCurrent();
            this.previous = this.previous.getPrevious();
        }
        return path;
    }

}
