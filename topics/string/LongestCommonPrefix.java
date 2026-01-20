package topics.string;

import java.util.Objects;
import java.util.Scanner;

public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {

        String prefix = strs[0];
        String holder = prefix;
        for(int i = 1; i < strs.length; i++) {
            if (prefix.length() > strs[i].length()){
                holder = strs[i];
            }else{
                holder = prefix;
            }


            int j = 0;
            while(j < holder.length() && strs[i-1].charAt(j) == strs[i].charAt(j)){
                j++;
            }
            prefix = holder.substring(0, j);
            if(prefix.length() == 0)
                return "";

        }

        return prefix;
    }
    /*


    ["reflower","flow","flight"]
     */

    public static void print(Object object, String tag) {
        if (Objects.isNull(tag)) {
            tag = "";
        }
        System.out.println(tag + " :: " + object.toString());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the string array in this format: [\"flower\",\"flow\",\"flight\"]");
        System.out.print("> ");

        String raw = scanner.nextLine().trim();

        // Remove outer brackets if present
        if (raw.startsWith("[") && raw.endsWith("]")) {
            raw = raw.substring(1, raw.length() - 1);
        }

        // Handle empty array: "[]"
        if (raw.isBlank()) {
            System.out.println("No strings provided");
            return;
        }

        // Split by comma and strip quotes
        String[] parts = raw.split(",");
        String[] strs = new String[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String s = parts[i].trim();
            if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
                s = s.substring(1, s.length() - 1);
            }
            strs[i] = s;
        }

        LongestCommonPrefix lcp = new LongestCommonPrefix();
        String prefix = lcp.longestCommonPrefix(strs);

        System.out.println();
        System.out.println("Longest common prefix: " + prefix);
    }
}
