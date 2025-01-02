package topics.graphs;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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
            long startTime = System.nanoTime(); // Record the start time
            graph.dijkstra(graph.nodeSRGList.stream().filter(_node -> _node.index == startId).findFirst().get());
            IntStream.rangeClosed(1, graph.nodeSRGList.size())
                    .forEach(number->{
                        if (Objects.isNull(graph.summary.get(number))){
                            graph.summary.put(number, -1);
                        }
                    });

            graph.summary.remove(startId);
            graph.summary = new TreeMap<>(graph.summary);

            StringBuilder output = new StringBuilder();
            for (Map.Entry<Integer, Integer> entry : graph.summary.entrySet() ) {
                output.append(entry.getValue()+" ");
            }

            System.out.println(output.toString().trim());
            System.out.println("\n ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^");

            long endTime = System.nanoTime(); // Record the start time
            Long timeTaken = (endTime - startTime);
            System.out.println("\nTime taken: " + timeTaken.toString() + " seconds\n");
            System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n");
        }
        System.out.println("\n END ...... \n");
        scanner.close();
    }
}

class Graph {

    List<NodeSRG> nodeSRGList = new ArrayList<>();
    public Map<Integer, TreeSet<Integer>> adjacencyList = new HashMap<>();
    public Set<Integer> visited = new HashSet<>();
    public Map<Integer,Integer> summary = new LinkedHashMap<>();
    public Integer[] summaryArray;

    public Graph(int size) {
        summaryArray = new Integer[size];
        for (int i=1; i<=size; i++){
            nodeSRGList.add(new NodeSRG(i));
            //summary.put(i,null);
            summaryArray[i-1] = null;
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
    }

    public void dijkstraAndres (NodeSRG pointerNode){
        visited.add(pointerNode.index);

        /*Integer pointerDistance = Objects.isNull(summary.get(pointerNode.index) ) ? 0 : summary.get(pointerNode.index);

        pointerNode.neighborMap.entrySet().stream()
                .filter(_node -> !visited.contains(_node.getKey().index) )
                .forEach (_node-> {
                    Integer temporaryDistance =  pointerDistance + _node.getValue();
                    if(Objects.isNull(summary.get(_node.getKey().index)) || summary.get(_node.getKey().index) > temporaryDistance ){
                        summary.put(_node.getKey().index, temporaryDistance);
                    }
        });

        Map<Integer, Integer> unvisited = summary.entrySet().stream()
                .filter(_entry -> !visited.contains(_entry.getKey()))
                .filter(_entry -> _entry.getValue() != null)
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

        */
        Integer pointerDistance = summaryArray[pointerNode.index-1] == null ? 0 : summaryArray[pointerNode.index-1];

        pointerNode.neighborMap.entrySet().stream()
                .filter(_node -> !visited.contains(_node.getKey().index) )
                .forEach (_node-> {
                    Integer temporaryDistance =  pointerDistance + _node.getValue();
                    if((summaryArray[_node.getKey().index-1] == null) || summaryArray[_node.getKey().index-1] > temporaryDistance ){
                        summaryArray[_node.getKey().index-1] = temporaryDistance;
                    }
                });

        LinkedHashMap<Integer, Integer> unvisited = IntStream.range(0, summaryArray.length)
                .filter(index -> !visited.contains(index+1)) // Exclude visited indices
                .filter(index -> summaryArray[index] != null) // Exclude null values
                .boxed()
                .sorted(Comparator.comparing(index -> summaryArray[index])) // Sort by value
                .collect(Collectors.toMap(
                        index -> index+1,                  // Key: index of the array
                        index -> summaryArray[index],    // Value: value from the array
                        (a, b) -> a,                     // Merge function (not needed since keys are unique)
                        LinkedHashMap::new));            // Maintain insertion order


        unvisited.entrySet().forEach( _entry -> dijkstra(
                nodeSRGList.stream().filter(_node -> _node.index == _entry.getKey()).findFirst().get()
        ));

    }

    public void dijkstra (NodeSRG startNode){
        PriorityQueue<NodeSRG> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(node -> summary.getOrDefault(node.index, Integer.MAX_VALUE))
        );
        priorityQueue.add(startNode);
        summary.put(startNode.index, 0);

        while (!priorityQueue.isEmpty()) {
            NodeSRG pointerNode = priorityQueue.poll();
            int pointerDistance = summary.get(pointerNode.index);

            for (Map.Entry<NodeSRG, Integer> neighborEntry : pointerNode.neighborMap.entrySet()) {
                NodeSRG neighborNode = neighborEntry.getKey();
                int edgeWeight = neighborEntry.getValue();

                if (visited.contains(neighborNode.index)) continue;

                int newDistance = pointerDistance + edgeWeight;
                int currentDistance = summary.getOrDefault(neighborNode.index, Integer.MAX_VALUE);

                if (newDistance < currentDistance) {
                    summary.put(neighborNode.index, newDistance);
                    priorityQueue.add(neighborNode);
                }
            }
        }
    }
}

class NodeSRG{
    int index;
    Map<NodeSRG, Integer> neighborMap = new HashMap<>();

    public NodeSRG(int index){
        this.index = index;
    }
}
