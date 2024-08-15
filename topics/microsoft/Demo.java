package topics.microsoft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {
    public static void print(String prefix, Object object) {
        StringBuilder output = new StringBuilder();
        System.out.println(output.append("\n").append(prefix).append(" ::: ").append(object.toString()));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Demo demo = new Demo();
        int[] input = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").replaceAll("\\,+$", "").split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        demo.solution(input);
        bufferedReader.close();
    }

    public int solution(Tree T) {
        // Implement your solution here
        int result  = 0;

        Set<String> innerMap = proccessT(T);

        print(">>> T", innerMap);
        return (int)innerMap.stream().filter(item -> item.length() == 3).count();
    }

    public static Set<String> proccessT(Tree tInner){
        Set<String> innerMap = new HashSet<String>();

        if(tInner.l==null && tInner.r==null)
            return innerMap.add(Integer.toString(tInner.x));

        if( tInner.l!=null){
            Set<String> leftMap =  proccessT(tInner.l);

            leftMap.stream()
                    .map(item -> String.valueOf(tInner.x)+item)
                    .forEach(innerMap::add);
        }

        if( tInner.r!=null){
            Set<String> rightMap =  proccessT(tInner.r);

            rightMap.stream()
                    .map(item -> String.valueOf(tInner.x)+item)
                    .forEach(innerMap::add);
        }
        return innerMap;
    }
}
