/**
 * GraphNode class
 */
public class GraphNode {
    
    /**
     * 
     */
    public int distance;
    public int parent;
    public int val;

    /**
     * Constructor
     */
    public GraphNode(int value) {
        this.val = value;
    }

    /**
     * Return string with node values. 
     */
    @Override
    public String toString() {
        return "(val: " + val + " par: " + parent + " dist: " + distance + ")" ;
    }
}
