package topics.graphs;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

//https://www.hackerrank.com/challenges/ctci-connected-cell-in-a-grid/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=graphs
class Result {
    /*
     * Complete the 'maxRegion' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY grid as parameter.
     */
    public static boolean printFlag = false;
    public static void print(String message) {
        if(Result.printFlag){
             System.out.println(message );
        }
    }

    public static int maxRegion(List<List<Integer>> grid) {
        // Write your code here
        int result = 0;
        List<Integer> visited = new ArrayList<>();
        List<Integer> filled = new ArrayList<>();
        int columns = grid.get(0).size();
        int rows = grid.size();
        List<GraphDF> graphDFList = new ArrayList<>();

        for(int i=0; i<grid.size(); i++){
            for(int j=0; j<grid.get(i).size(); j++){
                if(grid.get(i).get(j) == 1){
                    filled.add(i*columns + j);
                }
            }
        }

        print("Rows : " + rows + " Columns : " + columns );
         print("\n ::::::::::::::::::::::::::::::::::::::::::::: \n" );
        String output = filled.stream().map(String::valueOf).collect(joining(" "));
         print("\n ::::::::::::::::::::::::::::::::::::::::::::: \n" );
         print(output );
         print("\n ::::::::::::::::::::::::::::::::::::::::::::: \n" );

        int localLength = 0;
        while (!filled.isEmpty()){

            //initialize Node and Graph objects
            NodeDF startNode = new NodeDF(filled.remove(0));
            GraphDF graphDF = new GraphDF(startNode);
            graphDFList.add(graphDF);

            PriorityQueue<NodeDF> priorityQueue = new PriorityQueue<NodeDF>(
                    Comparator.comparingInt(node -> node.index)
            );
            priorityQueue.add(graphDF.start);
            visited.add(startNode.index);

            while(!priorityQueue.isEmpty()){
                NodeDF nodePointer = priorityQueue.poll();
                int currentRow = nodePointer.index / columns;
                int currentCol = nodePointer.index  % columns;
                 print("\n nodeValue: " + nodePointer.index  + " currentRow: " + currentRow + " currentCol: " + currentCol );

                for(int i=-1; i<=1; i++) {

                    if ((currentRow == 0 && i==-1) || (currentRow == rows-1 && i==1))
                        continue;

                    for(int j=-1; j<=1; j++) {

                        if ((currentCol == 0 && j==-1) || (currentCol == columns-1 && j==1))
                            continue;

                        int localIndex = nodePointer.index + columns*i + j ;
                        if (filled.contains(localIndex) && !visited.contains(localIndex)) {
                            NodeDF neighbor = new NodeDF(filled.remove(filled.indexOf(localIndex)));
                            nodePointer.neighborMap.put(neighbor, localIndex);
                            priorityQueue.add(neighbor);
                            graphDF.count++;
                            visited.add(localIndex);
                        }
                    }
                }

            }
        }



        result = graphDFList.stream().max(Comparator.comparingInt(node -> node.count)).orElse(new GraphDF()).count;
         print("\n ::::::::::::::::::::::::::::::::::::::::::::: \n" );

         print("\n maxRegion Return...... "+result+"\n" );
        return result;
    }
}

class NodeDF{
    int index;
    Map<NodeDF, Integer> neighborMap = new HashMap<>();
    public NodeDF(int index){
        this.index = index;
    }
}

class GraphDF{
    NodeDF start;
    Integer count = 0;
    public GraphDF(NodeDF start){
        this.start = start;
        count = 1;
    }

    public GraphDF(){
    }
}

public class ConnectedCellinaGrid {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        int m = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> grid = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                grid.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int res = Result.maxRegion(grid);

        /*bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();*/

        bufferedReader.close();
        //bufferedWriter.close();
    }
}
