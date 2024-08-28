package topics.graphs;

import java.util.Scanner;


//https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=graphs

public class ShortestReachGraph {

    public static class Graph {


        public Graph(int size) {

        }

        public void addEdge(int first, int second) {

        }

        public int[] shortestReach(int startId) { // 0 indexed
            return new int[]{0,0};
        }
    }

    public static void print(Object object, String prefix){
        if(prefix == null)
            prefix = "";
        else
            prefix = prefix + " ";
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
