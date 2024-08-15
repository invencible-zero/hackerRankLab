package topics.hashTables;

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


class Result {

    /*
     * Complete the 'checkMagazine' function below.
     *
     * The function accepts following parameters:
     *  1. STRING_ARRAY magazine
     *  2. STRING_ARRAY note
     */

    public static void checkMagazine(List<String> magazine, List<String> note) {
        // Write your code here
        String result = "No";
        //System.out.println();

        Map<String,Integer> frequencyNote = new HashMap<>();
        Map<String,Integer> frequencyMagazine = new HashMap<>();

        Integer[] frequencyNoteArray = new Integer[note.size()];
        Integer[] frequencyMagazineArray = new Integer[note.size()];

        if(magazine.size()== 0 || magazine.size()> 30000 ||
                note.size()== 0 || note.size()> 30000 ){
            System.out.println(result);
            return;
        }
        //System.out.println(">>>>>>>>>>>>> here ");
        for(int i=0; i<note.size();i++){

            String word = note.get(i);
            //System.out.println(">>>>>>>>>>>>> " + i + " : " + word);
            if(frequencyNote.get(word) != null){
                frequencyNote.replace(word, frequencyNote.get(word).intValue() +1 );
            }else{
                frequencyNote.put(word, 1);
            }
            //System.out.println(">>>>>>>>>>>>> frequencyNote.size " + frequencyNote.size());
        }

        //frequencyNote.forEach((key, value) -> {System.out.println("frequencyNote :: key " + key + ", value " + value);});

        for(int i=0; i<magazine.size();i++){

            String word = magazine.get(i);
            //System.out.println(">>>>>>>>>>>>> " + i + " : " + word);
            if(frequencyMagazine.get(word) != null){
                frequencyMagazine.replace(word, frequencyMagazine.get(word).intValue() + 1 );
            }else{
                frequencyMagazine.put(word, 1);
            }
            //System.out.println(">>>>>>>>>>>>> frequencyMagazine.size " + frequencyMagazine.size());
        }

        //frequencyMagazine.forEach((key, value) -> {System.out.println("frequencyMagazine :: key " + key + ", value " + value);});

        result = "Yes";
        for( Map.Entry<String,Integer> item : frequencyNote.entrySet() ){
            if( frequencyMagazine.get(item.getKey()) == null || frequencyMagazine.get(item.getKey())< item.getValue()  ){
                result = "No";
                break;
            }

        }

        /*Iterator<Entry<String, Integer>> iterator = frequencyNote.entrySet().iterator();
        result = "Yes";
        while(iterator.hashNext()){
            Map.Entry<String, Integer> item = (Map.Entry<String, Integer>) iterator.next();

            if( frequencyMagazine.get(item.getKey())< item.getValue()  ){
                result = "No";
                break;
            }

        }*/


        //frequencyMagazine.forEach((key,value)-> System.out.println(key +" " + value));

        System.out.println(result);

    }

}

public class RansomNote {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(firstMultipleInput[0]);

        int n = Integer.parseInt(firstMultipleInput[1]);

        List<String> magazine = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .collect(toList());

        List<String> note = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .collect(toList());

        Result.checkMagazine(magazine, note);

        bufferedReader.close();
    }
}
