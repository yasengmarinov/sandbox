package surface_area;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Solution {

    // Complete the surfaceArea function below.
    static int surfaceArea(int[][] A) {
        int surface = 0;
        int h = A.length;
        int w = A[0].length;

        surface+= h * w * 2;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                for (int k = 1; k <= A[i][j]; k++) {
                    if (i == 0 || A[i - 1][j] < k) surface++;
                    if (i == h - 1 || A[i + 1][j] < k) surface++;
                    if (j == 0 || A[i][j - 1] < k) surface ++;
                    if (j == w - 1 || A[i][j + 1] < k) surface++;
                }
            }
        }
        return surface;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] HW = scanner.nextLine().split(" ");

        int H = Integer.parseInt(HW[0]);

        int W = Integer.parseInt(HW[1]);

        int[][] A = new int[H][W];

        for (int i = 0; i < H; i++) {
            String[] ARowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < W; j++) {
                int AItem = Integer.parseInt(ARowItems[j]);
                A[i][j] = AItem;
            }
        }

        int result = surfaceArea(A);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
