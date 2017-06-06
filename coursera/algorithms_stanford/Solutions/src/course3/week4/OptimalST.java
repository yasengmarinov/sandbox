package course3.week4;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by b06514a on 5/26/2017.
 * Finds an Optimal Spanning Tree and prints its total weight
 * input: weights of the elements in the tree
 */
public class OptimalST {

    public static void main(String[] args) {
        double[] weights = {0.2, 0.05, 0.17, 0.1, 0.2, 0.03, 0.25};
        double[][] matrix = new double[weights.length][weights.length];

        for (int s = 0; s < weights.length; s++) {
            for (int i = 0; i + s < weights.length; i++) {
                SortedSet<Double> possibleCosts = new TreeSet<>();
                for (int r = i; r <= i + s; r++) {
                    possibleCosts.add(matrix[i][i + s] = calculateCost(weights, matrix, i, i + s, r));
                }
                matrix[i][i + s] = possibleCosts.first();
            }
        }

        for (int i = weights.length - 1; i >= 0; i--) {
            for (int j = 0; j < weights.length; j++) {
                System.out.print(String.format("%.2f ", matrix[j][i]));
            }
            System.out.print('\n');
        }
    }

    private static double calculateCost(double[] weights, double[][] matrix, int i, int j, int r) {
        double sum = 0;
        for (int k = i; k <= j; k++) {
            sum+= weights[k];
        }
        if (r != i) sum+= matrix[i][r - 1];
        if (r != j) sum+= matrix[r + 1][j];

        return sum;
    }

}
