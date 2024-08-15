package topics.string;

import java.io.*;
import java.util.stream.IntStream;

class Result {
    /*
     * Complete the 'alternatingCharacters' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int alternatingCharacters(String s) {
        int deletions = 0;
        int localCount = 0;
        boolean change = false;
        for (int i=0; i<s.length()-1;i++){

            if(s.charAt(i) == s.charAt(i+1)) {
                localCount++;
            }else{
                deletions += localCount;
                localCount = 0;
            }
        }

        if(s.charAt(s.length()-1) == s.charAt(s.length()-2)){
            deletions += localCount;
        }

        print("String Input", s);
        print("Output", deletions);
        return deletions;
    }

    public static void print(String prefix, Object object){
        StringBuilder output = new StringBuilder();
        System.out.println(output.append("\n").append(prefix).append(" ::: ").append(object.toString()) );
    }
}

public class AlternatingCharacters {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\___d\\globant\\hRk\\outputs\\alternating-characters.txt"));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String s = bufferedReader.readLine();

                int result = Result.alternatingCharacters(s);

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
