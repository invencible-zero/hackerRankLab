import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

//https://www.hackerrank.com/challenges/find-the-nearest-clone

public class GraphFindShortest {

    // Complete the findShortest function below.

    private static final Scanner scanner = new Scanner(System.in);

    protected static int absolutLenght = Integer.MAX_VALUE;
    static int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] idColorsArray, int val) {

        int returnValue = -1;

        System.out.println("graphNodes......................");
        System.out.println(graphNodes);

        System.out.println("graphFrom......................");
        System.out.println(Arrays.toString(graphFrom));

        System.out.println("graphTo......................");
        System.out.println(Arrays.toString(graphTo));

        System.out.println("colors......................");
        System.out.println(Arrays.toString(idColorsArray));

        System.out.println("val......................");
        System.out.println(val);


        Long valInLong =(long)(val);


        List<Integer> indexMatchColor = new ArrayList<>();

        int outerIterator = 0;
        int innerIterator = 0;
        for (Long item : idColorsArray) {
            if (item.equals(valInLong)) {
                indexMatchColor.add(innerIterator);
            }
            innerIterator++;
        }

        System.out.println("matchColor......................");
        System.out.println(Arrays.toString(indexMatchColor.toArray()));

        List<Node> graph = parseGraph(graphFrom, graphTo, idColorsArray);
        //graph.forEach(System.out::println);

        if (indexMatchColor.size() <= 1)
            return returnValue;
        else {

            while (outerIterator < indexMatchColor.size()) {

                innerIterator = outerIterator + 1;

                final int outerIteratorFinal = outerIterator;


                Node fromNode = graph.stream()
                        .filter(nodeLocal -> nodeLocal.getName() == (indexMatchColor.get(outerIteratorFinal) + 1))
                        .collect(Collectors.toList()).get(0);

                //iteration on graph
                while (innerIterator < indexMatchColor.size()) {
                    final int innerIteratorFinal = innerIterator;
                    Node toNode = graph.stream()
                            .filter(nodeLocal -> nodeLocal.getName() == (indexMatchColor.get(innerIteratorFinal) + 1))
                            .collect(Collectors.toList()).get(0);

                    System.out.println(":: outerIteratorFinal :: " + outerIteratorFinal + ", indexMatchColor.get(outerIteratorFinal) :: " + (indexMatchColor.get(outerIteratorFinal) + 1));
                    System.out.println("????????????????????????????????? fromNode :: " + fromNode.getName() + ",  toNode :: " + toNode.getName());
                    int tempLenght = calculateLenghtPath(fromNode, toNode, null, null, false);
                    System.out.println(":: innerIteratorFinal :: " + innerIteratorFinal + ", indexMatchColor.get(innerIteratorFinal) :: " + (indexMatchColor.get(innerIteratorFinal) + 1) + ", lenght :: " + tempLenght);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

                    innerIterator++;

                    if (tempLenght != -1 && tempLenght != 0 && tempLenght < absolutLenght)
                        absolutLenght = tempLenght;
                }
                outerIterator++;
            }
        }

        if (absolutLenght != Integer.MAX_VALUE)
            returnValue = absolutLenght;

        System.out.println(" returnValue :: " + returnValue);
        return returnValue;
    }

    static int calculateLenghtPath(Node fromNode, Node toNode, Node parentDiscarded, Node childDiscarded, Boolean startWithParent) {
        int localLenghtPath = 0;
        UUID uuid = UUID.randomUUID();

        System.out.println(uuid + " >>> fromNode :: " + fromNode.getName() + ", toNode :: " + toNode.getName() + ", parentDiscarded :: " + (parentDiscarded != null ? parentDiscarded.getName() : "NULL") + ", childDiscarded :: " + (childDiscarded != null ? childDiscarded.getName() : "NULL"));


        if (parentDiscarded != fromNode && childDiscarded != fromNode) {

            while (fromNode != toNode && localLenghtPath == 0 && fromNode.getChildren().size() != 0) {
                for (Node child : fromNode.getChildren()) {
                    if (!child.equals(childDiscarded) && localLenghtPath == 0) {
                        System.out.println(uuid + " >>> child :: " + child.getName() + ", toNode :: " + toNode.getName() + ", pointer :: " + fromNode.getName());
                        if (child.equals(toNode)) {

                            localLenghtPath++;
                            break;
                        }
                        if (child.getChildren().size() != 0) {
                            int resultLenghtPath = calculateLenghtPath(child, toNode, parentDiscarded, childDiscarded, true);

                            if (resultLenghtPath == -1)
                                return -1;

                            if (resultLenghtPath != 0) {
                                localLenghtPath = 1 + resultLenghtPath;
                                if (localLenghtPath >= absolutLenght)
                                    return -1;
                            }
                        }
                    }

                }
                if (localLenghtPath == 0)
                    break;
            }

            System.out.println(uuid + " ::::::::: localLenghtPath " + localLenghtPath );
            if(!startWithParent) {
                while (fromNode != toNode && localLenghtPath == 0 && fromNode.getParents().size() != 0) {
                    for (Node parent : fromNode.getParents()) {
                            if (parent.equals(toNode)) {
                                localLenghtPath++;
                                if (localLenghtPath >= absolutLenght)
                                    return -1;
                                break;
                            }
                            if (parent.getChildren().size() != 0) {
                                System.out.println(uuid + " >>> calculateLenghtPath(parent, toNode, fromNode, childDiscarded, null) ::: " + parent.getName() + " :: " + toNode.getName() + " :: NULL :: " + fromNode.getName());
                                int resultLenghtPath = calculateLenghtPath(parent, toNode, null, fromNode, true);

                                if (resultLenghtPath == -1)
                                    return -1;

                                if (resultLenghtPath != 0) {
                                    localLenghtPath = 1 + resultLenghtPath;
                                    if (localLenghtPath >= absolutLenght)
                                        return -1;
                                }
                            }
                    }
                }
            }
        }

        return localLenghtPath;
    }

    static List<Node> parseGraph(int[] graphFrom, int[] graphTo, long[] idColorsArray) {
        List<Node> graph = new ArrayList<>();

        for (int iterator = 0; iterator < idColorsArray.length; iterator++) {
            Node node = new Node(iterator + 1, idColorsArray[iterator]);
            graph.add(node);
        }

        int iterator = 0;
        while (iterator < graphFrom.length) {
            final int iteratorFinal = iterator;
            Node fromNode = graph.stream()
                    .filter(nodeLocal -> nodeLocal.getName() ==  Math.min( graphFrom[iteratorFinal], graphTo[iteratorFinal])  )
                    .collect(Collectors.toList()).get(0);

            Node toNode = graph.stream()
                    .filter(nodeLocal -> nodeLocal.getName() ==  Math.max( graphFrom[iteratorFinal], graphTo[iteratorFinal])  )
                    .collect(Collectors.toList()).get(0);

            fromNode.getChildren().add(toNode);
            toNode.getParents().add(fromNode);
            iterator++;
        }


        return graph;
    }

    public static void main(String[] args) throws IOException {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\___d\\globant\\hackerRank\\outputs\\graph.txt"));


        String[] graphNodesEdges = scanner.nextLine().split(" ");
        int graphNodes = Integer.parseInt(graphNodesEdges[0].trim());
        int graphEdges = Integer.parseInt(graphNodesEdges[1].trim());

        int[] graphFrom = new int[graphEdges];
        int[] graphTo = new int[graphEdges];

        for (int i = 0; i < graphEdges; i++) {
            String[] graphFromTo = scanner.nextLine().split(" ");
            graphFrom[i] = Integer.parseInt(graphFromTo[0].trim());
            graphTo[i] = Integer.parseInt(graphFromTo[1].trim());
        }

        long[] ids = new long[graphNodes];

        String[] idsItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < graphNodes; i++) {
            long idsItem = Long.parseLong(idsItems[i]);
            ids[i] = idsItem;
        }

        int val = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int ans = findShortest(graphNodes, graphFrom, graphTo, ids, val);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

class Node {
    int name;
    Long color;
    List<Node> parents;
    List<Node> children;

    Node(int name, Long color) {
        this.name = name;
        this.color = color;

        parents = new ArrayList<>();
        children = new ArrayList<>();
    }

    public int getName() {
        return name;
    }

    public List<Node> getParents() {
        return parents;
    }

    public List<Node> getChildren() {
        return children;
    }



    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", children =" + children.stream().map(Object::toString).collect(Collectors.joining(", ")) +
                '}';
    }

}
