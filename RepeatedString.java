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

//https://www.hackerrank.com/challenges/repeated-string/problem

class ResultRepeatedString {

    /*
     * Complete the 'repeatedString' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. LONG_INTEGER n
     */

    public static long repeatedString(String s, long n) {
    // Write your code here
        //long maxN = 1000000000000;
        
        if(s.length() < 0 || s.length()>100)
            return 0;
        
        if(n<1 )
            return 0;
            
        //String parts[] = s.split("a",-1);
        long frecuency  = 0;
        long count = 0;
        Double chunks = 0d;        
        
        for(int i=0; i < s.length(); i++ ){
            if( s.substring(i,i+1).equals("a") )
                frecuency++;
        }
        chunks = Math.floor(  (double)(n/s.length()) );
        count = chunks.longValue()*frecuency ;

        String rest = s.substring( 0, (int)(n - chunks*s.length())  );
        System.out.println("rest ::: " + rest);

        if(!rest.equals("")){
            long restFrecuency  = 0;
            String partsFinal[] =  rest.split("a",-1);
            for(int i=0; i < rest.length(); i++ ){
                //System.out.println("itemRest ;;;;; " + item);
                if( rest.substring(i,i+1).equals("a") )
                    restFrecuency++;
            }
            count += restFrecuency;
        }

        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"   );
        System.out.println("chunks ::: " + chunks);
        System.out.println("frecuency ::: " + frecuency);
        System.out.println("count ::: " + count);
        return count;
    }

}

public class RepeatedString {
    public static void main(String[] args) throws IOException {
        //String s = "geeksss@for@geeks";
        String s = "aba";
        ResultRepeatedString.repeatedString(s, 10l);

    }
}
