package sam_and_substrings;

import java.util.Scanner;

public class Solution {

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        String array = scanner.next();
        long[] results = new long[array.length()];

        int i = 0;

        while (i < array.length()) {
            if (i != 0 ) {
                results[i] += results[i - 1];
            }
            for (int j = 0; j < i + 1; j++) {
                results[i] += Long.valueOf(array.substring(j, i + 1));
            }
            results[i] = results[i] % ((long)Math.pow(10, 9) + 7);
            i++;
        }

        System.out.println(results[results.length - 1] % ((long)Math.pow(10, 9) + 7));
    }

}
