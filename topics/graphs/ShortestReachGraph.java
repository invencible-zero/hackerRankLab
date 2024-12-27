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
            int[] distances = graph.shortestReach(startId);

            for (int i = 0; i < distances.length; i++) {
                //if (i != startId) {
                    System.out.print(distances[i]);
                    System.out.print(" ");
                //}
            }
            System.out.println("\n **************************************************** \n");
            /*for (Map.Entry<Integer, TreeSet<Integer>> entry : graph.adjacencyList.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }*/
            System.out.println("Dijkstra SUMMARY table \n");
            graph.dijkstra(graph.nodeSRGList.stream().filter(_node -> _node.index == startId).findFirst().get());

            graph.summary = graph.summary.entrySet().stream().map(entry->{
                if (Objects.isNull(entry.getValue())) {entry.setValue(-1);}
                return entry;
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            graph.summary.remove(startId);

            for (Map.Entry<Integer, Integer> entry : graph.summary.entrySet() ) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
            System.out.println("\n ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^  \n");

        }
        System.out.println("\n END ...... \n");
        scanner.close();
    }


}

class Graph {

    public int size;
    public NodeSRG start;
    List<NodeSRG> nodeSRGList = new ArrayList<>();
    public Map<Integer, TreeSet<Integer>> adjacencyList = new HashMap<>();
    public Set<Integer> visited = new HashSet<>();
    public Map<Integer,Integer> summary = new TreeMap<>();

    public Graph(int size) {
        this.size = size;
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
            sourceNode.get().neighbor.add(destinationNode.get());
            sourceNode.get().neighborMap.put(destinationNode.get(), 6);


            destinationNode.get().neighbor.add(sourceNode.get());
            destinationNode.get().neighborMap.put(sourceNode.get(), 6);
        }

        adjacencyList.computeIfAbsent(Integer.valueOf(source), s-> adjacencyList.put(Integer.valueOf(source), new TreeSet<>()));
        adjacencyList.computeIfAbsent(Integer.valueOf(destination), d-> adjacencyList.put(Integer.valueOf(destination), new TreeSet<>()));

        adjacencyList.get(Integer.valueOf(source)).add(Integer.valueOf(destination));
        adjacencyList.get(Integer.valueOf(destination)).add(Integer.valueOf(source));
    }

    public int[] shortestReach(int startId) {
        NodeSRG startNode = nodeSRGList.stream()
                .filter(item -> item.index == startId  )
                .findFirst().get();

        Map<Integer, Integer> distanceMap = calculateDistance(startNode,0);
        TreeMap<Integer, Integer> sortedMap = new TreeMap<>(distanceMap);
        List<Integer> resultList = new ArrayList<>();

        for (int i=1; i<=size; i++){
            if( i!= startId) {
                Integer valueLocal = sortedMap.get(Integer.valueOf(i));
                if (valueLocal != null) {
                    valueLocal *= 6;
                } else {
                    valueLocal = Integer.valueOf(-1);
                }
                resultList.add(valueLocal);
                //ShortestReachGraph.print("node : " + i, ", distance : " + valueLocal);
            }
        }
        //ShortestReachGraph.print("Size ::: " , this.size);
        //resultList.stream().forEach(System.out::println);
        return resultList.stream().mapToInt(Integer::intValue).toArray();
    }

    public Map<Integer, Integer> calculateDistance(NodeSRG startNode, int depth){

        Map<Integer, Integer> innerMap = new HashMap<Integer,Integer>();
        if(!startNode.visited) {
            startNode.visited = true;

            if (depth != 0)
                innerMap.put(Integer.valueOf(startNode.index), Integer.valueOf(depth));
            for (NodeSRG child : startNode.neighbor) {
                innerMap.putAll(calculateDistance(child, depth + 1));
            }

            /*if(Objects.nonNull(startNode.parent)){
                innerMap.putAll(calculateDistance(startNode.parent, depth + 1));
            }*/
        }
        return innerMap;
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

        /*Map<Integer, Integer> unvisited =  summary.entrySet().stream()
                .filter(_entry -> !visited.contains(_entry.getKey()))
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,  (a, b) -> a, TreeMap::new));*/

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
    Set<NodeSRG> neighbor = new HashSet<>();
    Map<NodeSRG, Integer> neighborMap = new HashMap<>();
    boolean start = false;
    boolean visited = false;
    Integer distance = null;

    public NodeSRG(int index){
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }
}
