package topics.arrays;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class MaxArraySum {

    // Complete the maxSubsetSum function below.
    static int maxSubsetSum(int[] values) {


        List<Integer> debugValuesList = new ArrayList<>();

        if(values.length > 100000)
            return 0;

        int maxSum = 0;
        Integer positives = 0;
        int index = 0;
        List<List<Integer>> chunkStructure = new ArrayList<List<Integer>>();

        int indexOuterChunk = 0;

        for (int item : values) {


            if (chunkStructure.size() == indexOuterChunk )
                chunkStructure.add(new ArrayList<Integer>());

            if(item<-10000 || item>10000){
                return 0;
            }


            if(item>=0) {
                positives++;
                chunkStructure.get(indexOuterChunk).add( values[index] );

            } else {
                if(index!=0 && values[index-1]>=0 ) {
                    indexOuterChunk++;
                }
            }

            index++;
        }

        if( positives > 0 ){

            for ( List<Integer> chunk:chunkStructure) {
                if( chunk.size() > 0)
                maxSum += getCombinations(     chunk.stream().mapToInt(i->i).toArray()       );
            }

        }

        System.out.println("::::: maxSum :::: " + maxSum );
        System.out.println( " ???????????????????????????????????????????????  ???????????????????????????????????????????????  ??????????????????????????????????????????????? ");

        //maxSum = getCombinations(values, true);
        return maxSum;
    }

    private static Integer getMaxSumLocal(int[] values){
        int maxSumLocal = 0;
        return maxSumLocal;
    }

    private static Integer getCombinations(int[] values ){

        int maxInChunk = 0;
        int fromSize = (int)Math.ceil(values.length/2.0);

        // Array of pointer
        int[] pointerArray = new int[fromSize];
        pointerArray[0] = 0;
        int lastIndex;
        for(int i=1;i<fromSize;i++) {
            pointerArray[i] = pointerArray[i-1] + 2;
        }
        int pointerBase = fromSize-1;
        int pointerMASTER = fromSize-1;
        int sumLocal = 0;
        while(pointerBase >= 0) {
            System.out.println( "::::: pointerBase :::: " +    pointerBase       );
            System.out.println( "::::: pointerArray[pointerBase] :::: " +    pointerArray[pointerBase]       );

            if(pointerArray[pointerBase]  < values.length && pointerArray[pointerBase]!=-1) {

                //reset pointer fromSize pointerArray[pointerBase]
                lastIndex = pointerBase;
                for (int z = pointerBase+1; z < fromSize; z++) {
                    pointerArray[z] = pointerArray[z-1] == -1? -1 : pointerArray[z-1] + 2;

                    if( pointerArray[z] >= values.length){
                        pointerArray[z] = -1;
                    }

                    if(pointerArray[z] >= 0 && pointerArray[z] < values.length){
                        lastIndex = z;
                    }
                }


                System.out.println( "::::: pointerArray :::: " +    Arrays.toString(pointerArray)        );
                System.out.println( "::::: values :::: " +    Arrays.toString(values)        );

                if(values[pointerArray[0]]==2367){
                    System.out.println( "::::: debugger ::::  XD ");
                }


                //initial sum
                sumLocal = 0;
                List<String> listToDebug = new ArrayList<>();


                for (int x = 0; x < fromSize; x++) {

                    if(pointerArray[x]<values.length && pointerArray[x]!=-1) {
                        sumLocal += values[pointerArray[x]];
                        if ( sumLocal > maxInChunk)
                            maxInChunk = sumLocal;
                        listToDebug.add(Integer.toString(values[pointerArray[x]]));
                        System.out.println("-->>>>>>>>>>> " + String.join(" + ", listToDebug) + " = " + sumLocal);
                    }
                }




                if(pointerArray[lastIndex] != values.length-1) {
                    pointerArray[lastIndex]++;

                    //if(lastIndex != 0 ) {

                        System.out.println(":===: lastIndex :==: " + lastIndex);
                        System.out.println(":===: pointerArray[lastIndex] :==: " + pointerArray[lastIndex]);
                        for (; pointerArray[lastIndex] < values.length; pointerArray[lastIndex]++) {
                            sumLocal = sumLocal - values[pointerArray[lastIndex] - 1] + values[pointerArray[lastIndex]];
                            if (sumLocal > maxInChunk)
                                maxInChunk = sumLocal;

                            if (listToDebug.size() > 0)
                                listToDebug.remove(listToDebug.size() - 1);
                            listToDebug.add(Integer.toString(values[pointerArray[lastIndex]]));
                            System.out.println(">>>>>>>>>>--> " + String.join(" + ", listToDebug) + " = " + sumLocal);
                        }
                    //}
                }

                if( pointerBase == fromSize-1)
                    pointerArray[pointerBase] = values.length;
                else
                    pointerArray[pointerBase]++;
            } else {


                pointerBase--;


                if( pointerBase >= 0 ){
                    pointerArray[pointerBase]++;
                    if(pointerArray[0]<=values.length-2) {

                        for (int i = pointerBase + 1; i < fromSize; i++) {

                            //pointerArray[i] = pointerArray[i - 1] + 2;
                            pointerArray[i] = pointerArray[i-1] == -1? -1 : pointerArray[i-1] + 2;
                            if (pointerArray[i] >= values.length) {
                                pointerArray[i] = -1;
                            }

                            if(pointerArray[i] >= 0 && pointerArray[i] < values.length){
                                pointerBase = i;
                            }
                        }
                    }
                    else {
                        pointerBase = -1;
                        break;
                    }
                }


            }
            System.out.println( " ======================================================================================== ");
        }

        System.out.println( "::::: maxInChunk :::: " + maxInChunk);
        return maxInChunk;
    }

    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\___d\\globant\\hackerRank\\outputs\\graph.txt"));


        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int res = maxSubsetSum(arr);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
