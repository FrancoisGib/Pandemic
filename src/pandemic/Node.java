package pandemic;

import java.util.Stack;

/** The class representing a Node to get the shortest path between two towns */
public class Node {

    /** The previous Node */
    Node previous;

    /** The current town of the Node */
    Town current;

    /**
     * Builds a Node to get Path between towns
     * 
     * @param previous The previous Node
     * @param current The current node Town
     */
    public Node(Node previous, Town current) {
        this.previous = previous;
        this.current = current;
    }

    /**
     * Get the current Town
     * 
     * @return The town
     */
    public Town getCurrent() {
        return this.current;
    }
    
    /**
     * Get the previous Node
     * 
     * @return The previous Node
     */
    public Node getPrevious() {
        return this.previous;
    }

    /**
     * Get the path between this Node and the root Node
     * 
     * @return The Path
     */
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
