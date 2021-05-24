import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


/**
 * article: Efficient Graph Search
 * by: Terence Kelly
 * 
 * https://mydigitalpublication.com/publication/?m=38068&i=673330&view=articleBrowser&article_id=3761926&search=efficient%20graph%20search
 */
public class EfficientGraphSearch {


    /**
     * Test scaffold
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** initialization ****
        List<List<Integer>> edges = new ArrayList<>();

        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read number of vertices in graph ****
        int V = Integer.parseInt(br.readLine().trim());

        // ???? ????
        System.out.println("main <<< V: " + V);

        // **** read number of edges in graph ****
        int m = Integer.parseInt(br.readLine().trim());

        // ???? ????
        System.out.println("main <<< m: " + m);

        // **** read graph edges ****
        IntStream.range(0, m).forEach(i -> {
            try {
                edges.add(
                    Arrays.stream(br.readLine().split(","))
                        .map(s -> s.replace(" ", ""))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // **** close the buffered reader ****
        br.close();

        // ???? ????
        System.out.println("main <<< edges: " + edges.toString());

        // **** create and populate the graph ****
        MyGraph g = new MyGraph(V, edges, AdjacencyType.LIST);

        // ???? ????
        g.printGraph();


        // **** perform DFS from this vertex ****
        GraphNode s = g.G.get(1);

        // ???? ????
        System.out.println("main <<< s: " + s.toString());

        // **** 'classic' BFS from specified vertex ****
        g.classicBFS(s);

        // ???? ????
        System.out.println("main <<< g:");
        System.out.print(g.toString());

        // **** create and populate the graph ****
        g = new MyGraph(V, edges, AdjacencyType.LIST);

        // ???? ????
        g.printGraph();

        // **** 'efficient' BFS from specified vertex ****
        g.efficientBFS(s);
        
        // ???? ????
        System.out.println("main <<< g:");
        System.out.print(g.toString());
    }
}