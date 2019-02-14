package containers_of_balls;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    // Complete the organizingContainers function below.
    static String organizingContainers(int[][] container) {
        int[] containerCapacity = new int[container.length];
        int[] typesAmmount = new int[container.length];

        for (int i = 0; i < container.length; i++) {
            containerCapacity[i] = Arrays.stream(container[i]).sum();
            for (int j = 0; j < container.length; j++) {
                typesAmmount[i] += container[j][i];
            }
        }

        Arrays.sort(containerCapacity);
        Arrays.sort(typesAmmount);

        if (Arrays.equals(containerCapacity, typesAmmount))
            return "Possible";
        else
            return "Impossible";
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[][] container = new int[n][n];

            for (int i = 0; i < n; i++) {
                String[] containerRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < n; j++) {
                    int containerItem = Integer.parseInt(containerRowItems[j]);
                    container[i][j] = containerItem;
                }
            }

            String result = organizingContainers(container);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
