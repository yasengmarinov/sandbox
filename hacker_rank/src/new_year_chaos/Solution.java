package new_year_chaos;

import java.util.Scanner;

public class Solution {
    // This solution is not mine :(
    static void minimumBribes(int[] q) {
        int numberOfBribes = 0;
        for (int i = q.length - 1; i >= 0; i--) {
            if (q[i] - (i + 1) > 2) {
                numberOfBribes = -1;
                break;
            }

            for (int j = Math.max(0, q[i] - 2); j < i; j++) {
                if (q[j] > q[i]) numberOfBribes++;
            }
        }

        System.out.println(numberOfBribes >= 0 ? numberOfBribes : "Too chaotic");

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            int[] q = new int[n];
            for(int q_i = 0; q_i < n; q_i++){
                q[q_i] = in.nextInt();
            }
            minimumBribes(q);
        }
        in.close();
    }
}
