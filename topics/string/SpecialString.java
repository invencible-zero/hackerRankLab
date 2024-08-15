package topics.string;

import java.io.*;
import java.util.*;

public class SpecialString {

    // Complete the substrCount function below.

    static long substrCount(int n, String s) {
        long total = 0;

        if(n>=1000000 || !s.matches("[a-z]*") || (n != s.length()))
            return total;

        Integer p = 0;
        String previousCharacter = String.valueOf(s.charAt(0));
        String currentCharacter;
        Integer lastBatchSize = 0;
        String lastBatchCharacter = "";

        boolean caseMiddle = false;
        boolean allEquals = true;

        for(int i = 1; i<n;i++){

            System.out.println("input::::: " + s);
            System.out.println("input::::: " + s.substring(0,i+1));
            System.out.println("i ::::: " + i);
            System.out.println("p ::::: " + p);

            currentCharacter = String.valueOf(s.charAt(i));

            System.out.println("currentCharacter ::::: " + currentCharacter);
            System.out.println("lastBatchSize ::::: " + lastBatchSize);
            System.out.println("previousCharacter ::::: " + previousCharacter);
            System.out.println("lastBatchCharacter ::::: " + lastBatchCharacter);
            System.out.println("caseMiddle INIT ::::: " + caseMiddle);

            /*if(caseMiddle && lastBatchCharacter.equals( currentCharacter) && ((i-p )<=lastBatchSize) ){
                total ++;
            }*/

            if(caseMiddle && lastBatchCharacter.equals( currentCharacter) ){
                do{
                    i = Math.min(++i,n-1);
                    currentCharacter = String.valueOf(s.charAt( i ));

                    if(currentCharacter!=lastBatchCharacter)
                        previousCharacter = String.valueOf(s.charAt( i-1 ));
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.out.println("Here i ::::: " + i);
                    System.out.println("Here currentCharacter ::::: " + currentCharacter);
                    System.out.println("Here lastBatchCharacter ::::: " + lastBatchCharacter);
                    System.out.println("Here previousCharacter ::::: " + previousCharacter);
                    System.out.println("Here n ::::: " + n);
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                }while(currentCharacter.equals(lastBatchCharacter) && i!=n-1);


                total += Math.min(lastBatchSize,i-p) + (i-p)*(i-p+1)/2;
                p++;
            }

            if( !currentCharacter.equals(previousCharacter) ){

                System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ");
                System.out.println("i ::::: " + i);
                System.out.println("p ::::: " + p);
                System.out.println("currentCharacter ::::: " + currentCharacter);
                System.out.println("lastBatchSize ::::: " + lastBatchSize);
                System.out.println("previousCharacter ::::: " + previousCharacter);
                System.out.println("lastBatchCharacter ::::: " + lastBatchCharacter);

                allEquals = false;
                caseMiddle=true;
                lastBatchCharacter = previousCharacter;

                if((i-p)>1){
                    total += (i-p)*(i-p+1)/2;
                }else{
                    total += i-p;
                }

                previousCharacter = currentCharacter;
                lastBatchSize = i-p;
                p = i;
            }else
                caseMiddle=false;

            System.out.println("caseMiddle END ::::: " + caseMiddle);
            System.out.println("total ::::: " + total);
            System.out.println(" ======================================= ");
        }

        if(allEquals)
            total = (n)*(n+1)/2;
        else {
            total++;
        }

        System.out.println(" ======================================= ");
        System.out.println("total :::: " + total);
        return total;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\___d\\globant\\hRk\\outputs\\graph.txt"));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String s = scanner.nextLine();

        long result = substrCount(n, s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
