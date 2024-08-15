package topics.Dictionaries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FrequencyQueries {
    // Complete the freqQuery function below.
    static List<Integer> freqQuery(List<List<Integer>> queries) {
        List<Integer> result = new ArrayList<Integer>();
        Map<Integer,Integer> frequencyMap =  new TreeMap<>();
        List<Integer> frequencyList = new ArrayList<>();

        if(queries.size()>100000)
            return result;

        queries.forEach(item->{
            Integer key = item.get(0);
            Integer data = item.get(1);
            Integer frequency = 0;

            if(key<1 || key>3)
                return;

            if(data<1 || data>1000000000)
                return;

            if(frequencyMap.get(data) != null ){
                frequency = frequencyMap.get(data);
            }

            switch (key){
                case 1:
                    frequencyMap.put(data, frequency + 1);

                    frequencyList.add(frequency + 1);
                    frequencyList.remove((Object)(frequency));

                    break;
                case 2:
                    if(frequencyMap.get(data) != null) {
                        if (frequency > 1 ) {
                            frequencyMap.put(data, frequency - 1);
                            frequencyList.add(frequency - 1);
                        }
                        else {
                            frequencyMap.remove(data);
                        }
                        frequencyList.remove((Object)(frequency));
                    }
                    break;
                case 3:
                    result.add( frequencyList.contains(data) ? 1 : 0);
                    break;
            }

        });
        print(">> frequencyMap ", frequencyMap);
        print(">> frequencyList ", frequencyList);
        print(">> Result ", result);
        return result;
    }

    public static void print(String prefix, Object object){
        StringBuilder output = new StringBuilder();
        System.out.println(output.append("\n").append(prefix).append(" ::: ").append(object.toString()) );
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> queries = new ArrayList<>();

        IntStream.range(0, q).forEach(i -> {
            try {
                queries.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> ans = freqQuery(queries);

        /*bufferedWriter.write(
                ans.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );*/

        bufferedReader.close();
        //bufferedWriter.close();
    }
}
