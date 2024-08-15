package topics.arrays;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.joining;

//Challenge: https://www.hackerrank.com/challenges/jumping-on-the-clouds

class ResultJumping {

    /*
     * Complete the 'jumpingOnClouds' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY c as parameter.
     */

    public static int jumpingOnClouds(List<Integer> c) {
    // Write your code here
        int jumps = 0;
        for (int i = 0; i< c.size(); ) {

            System.out.println("i :::::::::::: " + i);

            if( ((i+2)<c.size()) && (c.get(i+2) != 1))
                i += 2;
            else
                i += 1;

            jumps++;
        }
        jumps--;
        System.out.println("Jumps :::::::::::: " + jumps);
        return jumps;
    }

}


public class JumpingCloud {
    public static void main(String[] args) throws IOException {
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        //int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> c = new ArrayList<Integer>();/*Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());*/

            c.add(0);
            c.add(0);
            c.add(1);
            c.add(0);
            c.add(0);
            c.add(1);
            c.add(0);


        int result = ResultJumping.jumpingOnClouds(c);

        /*bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();*/
    }
}
