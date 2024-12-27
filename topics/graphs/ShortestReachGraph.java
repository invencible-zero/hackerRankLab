package topics.graphs;

import java.util.*;
import java.util.stream.Collectors;


//https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=graphs

public class ShortestReachGraph {

    public static void print(String prefix, Object object){
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
                int u = scanner.nextInt();
                int v = scanner.nextInt();

                // add each edge to the graph
                graph.addEdge(u, v);
            }

            // Find shortest reach from node s
            int startId = scanner.nextInt();
            graph.dijkstra(graph.nodeSRGList.stream().filter(_node -> _node.index == startId).findFirst().get());

            graph.summary = graph.summary.entrySet().stream().map(entry->{
                if (Objects.isNull(entry.getValue())) {entry.setValue(-1);}
                return entry;
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            graph.summary.remove(startId);

            for (Map.Entry<Integer, Integer> entry : graph.summary.entrySet() ) {
                System.out.print(entry.getValue()+" ");
            }
            //System.out.println("\n ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^  \n");

        }
        //System.out.println("\n END ...... \n");
        scanner.close();
    }


}

class Graph {

    List<NodeSRG> nodeSRGList = new ArrayList<>();
    public Map<Integer, TreeSet<Integer>> adjacencyList = new HashMap<>();
    public Set<Integer> visited = new HashSet<>();
    public Map<Integer,Integer> summary = new TreeMap<>();

    public Graph(int size) {
        for (int i=1; i<=size; i++){
            nodeSRGList.add(new NodeSRG(i));
            summary.put(i,null);
        }
    }

    public void addEdge(int source, int destination) {
        Optional<NodeSRG> sourceNode = nodeSRGList.stream()
                .filter(item -> item.index == source  )
                .findFirst();

        Optional<NodeSRG> destinationNode = nodeSRGList.stream()
                .filter(item -> item.index == destination  )
                .findFirst();

        if(sourceNode.isPresent() && destinationNode.isPresent()){
            sourceNode.get().neighborMap.put(destinationNode.get(), 6);
            destinationNode.get().neighborMap.put(sourceNode.get(), 6);
        }
        /*
        adjacencyList.computeIfAbsent(Integer.valueOf(source), s-> adjacencyList.put(Integer.valueOf(source), new TreeSet<>()));
        adjacencyList.computeIfAbsent(Integer.valueOf(destination), d-> adjacencyList.put(Integer.valueOf(destination), new TreeSet<>()));

        adjacencyList.get(Integer.valueOf(source)).add(Integer.valueOf(destination));
        adjacencyList.get(Integer.valueOf(destination)).add(Integer.valueOf(source));*/
    }

    public void dijkstra (NodeSRG pointerNode){
        visited.add(Integer.valueOf(pointerNode.index));

        //summary.putIfAbsent(Integer.valueOf(pointerNode.index),Integer.valueOf(0));
        Integer pointerDistance = Objects.isNull(summary.get(Integer.valueOf(pointerNode.index)) ) ? 0 : summary.get(Integer.valueOf(pointerNode.index));

        pointerNode.neighborMap.entrySet().stream()
                .filter(_node -> !visited.contains(_node.getKey().index) )
                .forEach (_node-> {
                    Integer temporaryDistance =  pointerDistance + _node.getValue() ;
                    if(Objects.isNull(summary.get(_node.getKey().index)) || summary.get(_node.getKey().index) > temporaryDistance ){
                        summary.put(_node.getKey().index, temporaryDistance);
                    }
        });

        Map<Integer, Integer> unvisited = summary.entrySet().stream()
                .filter(_entry -> !visited.contains(_entry.getKey()))
                .filter(_entry -> _entry.getValue() != null)
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, TreeMap::new));

        unvisited.entrySet().forEach( _entry -> dijkstra(
                nodeSRGList.stream().filter(_node -> _node.index == _entry.getKey()).findFirst().get()
        ));

    }
}

class NodeSRG{
    int index;
    Map<NodeSRG, Integer> neighborMap = new HashMap<>();

    public NodeSRG(int index){
        this.index = index;
    }
}
