package sherlock_and_cost;

import java.util.Scanner;

public class Solution {

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();

        for (int i = 0; i < t; i++) {
            runInstance(scanner);
        }
    }

    private static void runInstance(Scanner scanner) {
        int n = scanner.nextInt();
        int b[] = new int[n];

        for (int i = 0; i < b.length; i++) {
            b[i] = scanner.nextInt();
        }

        // Here first dimension represents each item from the array
        // The second dimension represents whether the element is 1 or max
        int s[][] = new int[n][2];

        for (int i = 1; i < n; i++) {
            s[i][0] = Math.max(s[i - 1][0] + Math.abs(1 - 1), s[i - 1][1] + Math.abs(1 - b[i - 1]));
            s[i][1] = Math.max(s[i - 1][0] + Math.abs(b[i] - 1), s[i - 1][1] + Math.abs(b[i] - b[i - 1]));
        }

        System.out.println(Math.max(s[n - 1][0], s[n - 1][1]));
    }

}
