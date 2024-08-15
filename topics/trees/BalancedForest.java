package topics.trees;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Node{

    public Integer index;
    public Integer value;
    public Integer deep;
    public long sum;
    public List<Node> children = new ArrayList<Node>();
    public List<Node> parents = new ArrayList<Node>();
    public List<Integer> genealogy = new ArrayList<Integer>();

    public Node(Integer index, Integer value){
        this.index = index;
        this.value = value;
    }

    public String toString(){
        String output = "\n Node \n ====  index :: " + index+
                " value :: " + value+
                " deep :: " + deep+
                " sum :: " + sum + ", \n children :::: ("+children.size()+")\n";

        for(Node item : children){
            output += "\n >>>>>>>>>>>>>>>>>>> Child  ====  index :: " + item.index + " value :: " + item.value + " deep :: " + item.deep+" sum :: " + item.sum ;
        }

        for(Node item : parents){
            output += "\n >>>>>>>>>>>>>>>>>>> Parent  ====  index :: " + item.index + " value :: " + item.value + " deep :: " + item.deep+" sum :: " + item.sum ;
        }


        output += "\n -------------------------------------- \n";
        return output;

    }
}

class Result {

    public static Long balancedForest(Integer[] c, Integer[][] edges) {

        Node[] nodeList = new Node[c.length];

        /*Node root = new Node(1,c.get(0));
        root.deep = 0;
        nodeList.add(root);*/

        Integer index =1;
        for(Integer item : c) {
            Node _node = new Node(index, item);
            nodeList[index-1] = _node;
            index++;
        }
        nodeList[0].deep = 0;
        Node root = nodeList[0];

        for(Integer[] innerList : edges){
            Node parent = nodeList[innerList[0]-1];
            Node child = nodeList[innerList[1]-1];
            parent.children.add(child);
            child.parents.add(parent);
        }


        long total = calculateSum(root);
        edges = cleanEdges(  edges, nodeList);
        //System.out.println(" ?????????????????????????????????????????????????? ");
        //System.out.println(root.toString());
        //System.out.println("total :: " + total);

        //nodeList.get(5-1).genealogy.forEach(System.out::println);
        //nodeList.forEach(System.out::println);
        //System.out.println(" ?????????????????????????????????????????????????? ");

        Set<Long> posibleList = exploreBy3Groups(edges, nodeList, total);

        if(posibleList.size() == 0)
            posibleList.addAll(exploreBy2Groups(edges, nodeList, total));

        //System.out.println(" posibleList  ");
        //posibleList.forEach(System.out::println);
        //System.out.println(" ----------------------- ----------------------- -----------------------  ");

        if(posibleList.size()==0)
            return -1l;
        return Collections.min(posibleList);
    }

    public static Set<Long> exploreBy2Groups(Integer[][] edges, Node[] nodeList, long total) {
        Set<Long> posibleList = new HashSet<>();

        //System.out.println(" ----------------------- exploreBy2Groups -----------------------  ");
        for (int i = 0; i < edges.length - 1; i++) {
            Integer[] edgeI = edges[i];

            //System.out.println("FROM >> edgeI : " + i + " ("+ edgeI.get(0)+","+edgeI.get(1)+")" );
            //System.out.println( nodeList.get(edgeI.get(0)-1).toString() );

            if(nodeList[edgeI[1]-1].sum == (total - nodeList[edgeI[1]-1].sum) ){
                posibleList.add(nodeList[edgeI[1]-1].sum);
                break;
            }

            //System.out.println(" ----------------------- ----------------------- -----------------------  ");
        }
        return posibleList;
    }

    public static Set<Long> exploreBy3Groups(Integer[][] edges, Node[] nodeList, long total){
        Set<Long> posibleList =  new HashSet<>();
        for(int i=0; i<edges.length-1;i++){

            Integer[] edgeI = edges[i];


            for(int j=i+1; j<edges.length;j++){

                Integer[] edgeJ = edges[j];

                System.out.println("FROM >> edgeI : " + i + " ("+ edgeI[0]+","+edgeI[1]+")" );
                System.out.println("TO   >> edgeJ : " + j + " ("+ edgeJ[0]+","+edgeJ[1]+")" );

                //look for the lowest node
                Node pivot = nodeList[edgeI[1]-1];
                Node counterpivot = nodeList[edgeJ[1]-1];

                if( nodeList[edgeJ[1]-1].deep > nodeList[edgeI[1]-1].deep ) {
                    counterpivot = pivot;
                    pivot = nodeList[edgeJ[1] - 1];
                }

                System.out.println("pivot        >> : " + pivot.index + " deep : " + pivot.deep );
                System.out.println("counterpivot >> : " + + counterpivot.index + " deep : " + counterpivot.deep );



                long sumGroupA, sumGroupB, sumGroupC;

                sumGroupA = pivot.sum;

                if( (total-sumGroupA)/2 <= sumGroupA) {
                    System.out.println("pivot group : " + sumGroupA + ", rest: " + (total-sumGroupA));
                    System.out.println(" >>>>>>>>> CONTINUE;");
                    System.out.println(" ----------------------- ----------------------- -----------------------  ");
                    continue;
                }

                boolean overlaped = checkParent(pivot, counterpivot);
                //System.out.println("overlaped>> " + overlaped);

                sumGroupB = counterpivot.sum;
                if(overlaped) {
                    sumGroupB = sumGroupB - sumGroupA;
                }

                sumGroupC = total - sumGroupA - sumGroupB;


                System.out.println(" sumGroupA : " + sumGroupA + " sumGroupB : " + sumGroupB + " sumGroupC : " + sumGroupC);
                if( sumGroupA == sumGroupB && sumGroupA>sumGroupC ) {
                    posibleList.add(sumGroupA - sumGroupC );
                }

                if( sumGroupA == sumGroupC && sumGroupA>sumGroupB ) {
                    posibleList.add(sumGroupA - sumGroupB );
                }

                if( sumGroupB == sumGroupC  && sumGroupB>sumGroupA){
                    posibleList.add(sumGroupB - sumGroupA);
                }

                System.out.println(" ----------------------- ----------------------- -----------------------  ");
            }
        }
        return posibleList;
    }

    public static long calculateSum(Node item){
        long total = item.value;
        if(item.parents.size()>1 || item.deep == 0) {
            for (Iterator<Node> iterator = item.parents.iterator(); iterator.hasNext(); ) {
                Node fakeParent = iterator.next();
                if (fakeParent.deep == null) {
                    iterator.remove();
                    fakeParent.children.remove(item);
                    item.children.add(fakeParent);
                    fakeParent.parents.add(item);
                }
            }
        }

        for(Node _node : item.children){
            _node.deep = item.deep +1;
            _node.genealogy.add(item.index);
            _node.genealogy.addAll(item.genealogy);
            total = total + calculateSum(_node);
        }

        item.sum = total;
        return total;
    }

    public static boolean checkParent(Node child, Node possibleParent){
        if((child.index == possibleParent.index) ||
            possibleParent.deep == 0  ||
            child.deep<= possibleParent.deep)
            return false;

        /*while (possibleParent != null) {
            if(child.parents.get(0).index == possibleParent.index) {
                return true;
            }else {
                return checkParent( child.parents.get(0), possibleParent);
                //possibleParent = possibleParent.parents.get(0);
            }
        }*/

        if( (child.parents.get(0).index == possibleParent.index) ||
             child.genealogy.contains(possibleParent.index))
            return true;

        return false;
    }

    public static Integer[][] cleanEdges( Integer[][] edges, Node[] nodeList){

        for(Integer[] innerList : edges){
            Node parent = nodeList[innerList[0]-1];
            Node child = nodeList[innerList[1]-1];

            if(parent.deep> child.deep){
                Integer temp = innerList[0];
                innerList[0] = innerList[1];
                innerList[1] = temp;
            }
        }

        return edges;
    }
}

public class BalancedForest {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("C:\\___d\\globant\\hRk\\outputs\\balancedForest.txt")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> c = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList());

                List<List<Integer>> edges = new ArrayList<>();


                IntStream.range(0, n - 1).forEach(i -> {
                    try {
                        edges.add(
                                Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                        .map(Integer::parseInt)
                                        .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                Integer[][] edgesArray = new Integer[edges.size()][2];
                for(int i=0; i<edges.size(); i++){
                    edgesArray[i] = Arrays.copyOf(edges.get(i).toArray(), 2, Integer[].class);
                }


                long start = System.currentTimeMillis();
                Long result = Result.balancedForest(
                        Arrays.copyOf(c.toArray(), c.size(), Integer[].class),
                        edgesArray
                );
                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;

                System.out.println(" ------- >>>  ------- >>>  ------- >>>  ------- >>>  ------- >>>  ------- >>> ");
                System.out.println("result :: " + result);
                System.out.println(String.format("time :: %s", timeElapsed));

                //bufferedWriter.write(String.valueOf(result));
                //bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        //bufferedWriter.close();
    }
}
