package acm_icpc_team;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        short[][] matrix = new short[n][m];
        for (int i = 0; i < n; i++) {
            char[] line = scanner.next().toCharArray();
            for (int j = 0; j < m; j++) {
                matrix[i][j] = Short.valueOf(String.valueOf(line[j]));
            }
        }

        int[][] results = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
             results[i][j] = getNumberOfTopics(matrix, i, j);
            }
        }

        int maxNumTopics = 0;
        int maxPairs = 0;

        for (int i = 0; i < results.length; i++) {
            for (int j = i + 1; j < results[0].length; j++) {
                if (results[i][j] > maxNumTopics)
                    maxNumTopics = results[i][j];
            }
        }

        for (int i = 0; i < results.length; i++) {
            for (int j = i + 1; j < results[0].length; j++) {
                if (results[i][j] == maxNumTopics)
                    maxPairs++;
            }
        }

        System.out.println(maxNumTopics);
        System.out.println(maxPairs);
    }

    private static int getNumberOfTopics(short[][] matrix, int i, int j) {
        int total = 0;
        for (int k = 0; k < matrix[0].length; k++) {
            total += matrix[i][k] | matrix[j][k];
        }
        return total;
    }
}

