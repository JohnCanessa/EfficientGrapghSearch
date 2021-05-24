import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;


// **** adjacency type ****
enum AdjacencyType {
    LIST, MATRIX, UNDEFINED
}


/**
 * Graph class.
 */
public class MyGraph {


    /**
     * Class members.
     */
    public ArrayList<ArrayList<Integer>> adjList    = null;
    public int[][] adjMatrix                        = null;
    public AdjacencyType adjType                    = AdjacencyType.UNDEFINED;
    public int V                                    = 0;
    public int E                                    = 0;
    public HashMap<Integer, GraphNode> G            = null;


    /**
     * Constructor(s)
     */
    public MyGraph(int V, List<List<Integer>> edges, AdjacencyType adjType) {

        // **** sanity checks ****
        if (V <= 0)
            return;

        if (edges == null || edges.size() == 0)
            return;

        if (!(adjType == AdjacencyType.LIST || adjType == AdjacencyType.MATRIX))
            return;

        // **** set the number of vertices in the graph ****
        this.V = V;

        // **** create adjacency list or matrix ****
        this.adjType = adjType;
        if (adjType == AdjacencyType.LIST) {

            // **** create adjacency list ****
            this.adjList = new ArrayList<ArrayList<Integer>>();

            // **** create empty entries in the adjacency list ****
            for (int i = 0; i < (V + 1); i++)
                adjList.add(new ArrayList<Integer>());

        } else if (this.adjType == AdjacencyType.MATRIX) {


        } else {
            System.out.println("MyGraph <<< unexpected adjType: " + this.adjType.toString());
            return;
        }

        // **** create the graph ****
        this.G = new HashMap<>();

        // **** traverse the edges populating the adjacency list and the graph ****
        for (int i = 0; i < edges.size(); i++) {

            // **** get this edge ****
            List<Integer> edge = edges.get(i);

            // ???? ????
            // System.out.println("MyGraph <<< edge: " + edge.toString());

            // **** for ease of use ****
            int v = edge.get(0);
            int u = edge.get(1);

            // ???? ????
            // System.out.println("MyGraph <<< u: " + u + " v: " + v);

            // **** create the u node in the graph (if needed) ****
            if (!G.containsKey(u)) {
                G.put(u, new GraphNode(u));
            }

            // **** create the v node in the graph (if needed) ****
            if (!G.containsKey(v)) {
                G.put(v, new GraphNode(v));
            }

            // **** add this edge to the graph ****
            addEdge(u, v);
        }
    }


    // /**
    //  * Disable default constructor.
    //  */
    // private MyGraph() {}


    /**
     * Utility function to add an edge in an undirected graph.
     */
    public void addEdge(int u, int v) {

        // **** ****
        if (this.adjType == AdjacencyType.LIST) {

            // **** add bidirectional edge ****
            this.adjList.get(u).add(v);
            this.adjList.get(v).add(u);

            // **** count the edge ****
            this.E++;
        } else if (this.adjType == AdjacencyType.MATRIX) {


        } else {
            System.out.println("addEdge <<< unexpected adjType: " + this.adjType.toString());
            return;
        }
    }


    /**
     * Utility to print the contents of graph.
     */
    public void printGraph() {

        // **** print the graph based on the adjacency data structure ****
        if (this.adjType == AdjacencyType.LIST) {

            // **** ****
            for (int i = 0; i < adjList.size(); i++) {

                // **** start the list ****
                System.out.print("v (" + i + ")");

                // **** ****
                for (int j = 0; j < adjList.get(i).size(); j++)
                    System.out.print(" -> " + adjList.get(i).get(j));

                // **** done with the list ****
                System.out.println();
            }
        } else if (this.adjType == AdjacencyType.MATRIX) {

        } else {
            System.out.println("printGraph <<< unexpected adjType: " + this.adjType.toString());
            return;
        }
    }


    /**
     * toString
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("V: " + this.V + " E: " + this.E + "\n");
        for (int i = 1; i < this.G.size(); i++) {
            sb.append(this.G.get(i).toString() + "\n");
        }
        return sb.toString();
    }


    // **** counts vertices with finalized output fields ****
    static int f = 1;


    /**
     * Classic BFS.
     * 
     * BFS computes for each vertex v two related outputs:
     * 
     * o distance from the source (i.e., the number of edges on a shortest path between s and v), 
     * o and a parent vertex chosen from v's neighbors.
     * 
     * BFS computes for each vertex v two related outputs: 
     * o distance from the source (i.e., the number of edges on a shortest path between s and v), 
     * o and a parent vertex chosen from v's neighbors. 
     * 
     * Moving to v's parent, grandparent, great-grandparent, etc. traces a shortest path to s
     * backtracking yields a shortest path from s to v.
     * 
     * Runtime: O(V + E)
     */
    public void classicBFS(GraphNode s) {

        // **** sanity check(s) ****
        if (s == null)
            return;

        // **** initialization ****
        s.distance  = 0;
        s.parent    = s.val;        // source is defined to be its own parent
        f           = 1;            // # of vertices found & finalized

        LinkedList<GraphNode> q = new LinkedList<>();

        // **** prime the queue ****
        q.add(s);

        // **** loop while the queue is not empty - O(V) ****
        while (!q.isEmpty()) {

            // **** remove head node ****
            GraphNode u = q.removeFirst();

            // **** get the adjacent list for node u ****
            List<Integer> adj = this.adjList.get(u.val);

            // **** for each vertex v adjacent to u - O(2 * E) ****
            adj.forEach( (node) -> {

                // **** get vertex from the graph ****
                GraphNode v = G.get(node);

                // **** process this vertex (if needed) ****
                if (v.parent == 0) {

                    // **** ****
                    v.distance = u.distance + 1;
                    v.parent = u.val;

                    // **** update counter ****
                    ++f;

                    // **** insert vertex into queue ****
                    q.add(v);
                }
            });
        }

        // ???? ????
        System.out.println("classicBFS <<< f: " + f);
    }


    /**
     * Efficient BFS.
     * 
     * BFS computes for each vertex v two related outputs:
     * 
     * o distance from the source (i.e., the number of edges on a shortest path between s and v), 
     * o and a parent vertex chosen from v's neighbors.
     * 
     * BFS computes for each vertex v two related outputs: 
     * o distance from the source (i.e., the number of edges on a shortest path between s and v), 
     * o and a parent vertex chosen from v's neighbors. 
     * 
     * Moving to v's parent, grandparent, great-grandparent, etc. traces a shortest path to s
     * backtracking yields a shortest path from s to v.
     */
    public void efficientBFS(GraphNode s) {

        // **** sanity check(s) ****
        if (s == null)
            return;

        // **** initialization ****
        s.distance  = 0;
        s.parent    = s.val;        // source is defined to be its own parent
        f           = 1;            // # of vertices found & finalized
        LinkedList<GraphNode> q = new LinkedList<>();

        // **** prime the queue ****
        q.add(s);

        // **** loop while the queue is not empty ****
        while (!q.isEmpty()) {

            // **** remove head node ****
            GraphNode u = q.removeFirst();

            // **** get the adjacent list for node u ****
            List<Integer> adj = this.adjList.get(u.val);

            // **** for each vertex v adjacent to u ****
            adj.forEach( (node) -> {

                // **** get vertex from the graph ****
                GraphNode v = G.get(node);

                // **** process this vertex (if needed) ****
                if (v.parent == 0) {

                    // **** ****
                    v.distance = u.distance + 1;
                    v.parent = u.val;

                    // **** check if done ****
                    if (++f == this.V) {

                        // ???? ????
                        System.out.println("efficientBFS <<< f: " + f + " q.size: " + q.size());

                        // **** we are done ****
                        return;
                    }

                    // **** insert vertex into queue ****
                    q.add(v);
                }
            });
        }
    }
}

