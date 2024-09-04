package topics.graphs;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;


//https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=graphs

public class ShortestReachGraph {

    public static void print(Object object, String prefix){
        if(prefix == null)
            prefix = "\n";
        else
            prefix = "\n"+prefix + " ";
        System.out.println(prefix + object);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int queries = scanner.nextInt();

        for (int t = 0; t < queries; t++) {

            // Create a graph of size n where each edge weight is 6:
            Graph graph = new Graph(scanner.nextInt());
            int m = scanner.nextInt();

            // read and set edges
            for (int i = 0; i < m; i++) {
                int u = scanner.nextInt() - 1;
                int v = scanner.nextInt() - 1;

                // add each edge to the graph
                graph.addEdge(u, v);
            }

            // Find shortest reach from node s
            int startId = scanner.nextInt() - 1;
            int[] distances = graph.shortestReach(startId);

            for (int i = 0; i < distances.length; i++) {
                if (i != startId) {
                    System.out.print(distances[i]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }


}

class Graph {

    public int size;
    public NodeSRG start;
    Set<NodeSRG> nodeSRSet = new HashSet<NodeSRG>();

    public Graph(int size) {
        this.size = size;
        for (int i=1; i<=size; i++){
            nodeSRSet.add(new NodeSRG(i));
        }
    }

    public void addEdge(int source, int destination) {

        Optional<NodeSRG> sourceNode = nodeSRSet.stream()
                .filter(item -> item.index == source  )
                .findFirst();

        Optional<NodeSRG> destinationNode = nodeSRSet.stream()
                .filter(item -> item.index == destination  )
                .findFirst();

        if(sourceNode.isPresent() && destinationNode.isPresent()){
            sourceNode.get().next = destinationNode.get();
            destinationNode.get().prev = sourceNode.get();
        }

    }

    public int[] shortestReach(int startId) {

        NodeSRG startNode = nodeSRSet.stream()
                .filter(item -> item.index == startId  )
                .findFirst().get();

        ShortestReachGraph.print(startNode, null);

        return new int[]{0,0};
    }
}

class NodeSRG{
    int index;
    NodeSRG next;
    NodeSRG prev;
    Boolean start = false;

    public NodeSRG(int index){
        this.index = index;
    }
}
