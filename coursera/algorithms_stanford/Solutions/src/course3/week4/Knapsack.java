package course3.week4;

import common.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b06514a on 5/26/2017.
 * Finds a solution to the Knapsack problem
 * How to fit maximum value with set weight
 * input: items + knapsack weight
 */
public class Knapsack {

    public static void main(String[] args) {
        List<String> input = Utils.parseFile(new Knapsack().getClass(), "knapsack1.txt");
        findOptimalValue(input);

        input = Utils.parseFile(new Knapsack().getClass(), "knapsack_big.txt");
        findOptimalValue(input);
    }

    private static void findOptimalValue(List<String> input) {
        int capacity = Integer.valueOf(input.get(0).split(" ")[0]);
        int numberOfElements = Integer.valueOf(input.get(0).split(" ")[1]);

        List<Item> items = new ArrayList<>(numberOfElements);
        items.add(new Item(0, 0));
        for (int i = 1; i < input.size(); i++) {
            int value = Integer.valueOf(input.get(i).split(" ")[0]);
            int weight = Integer.valueOf(input.get(i).split(" ")[1]);
            items.add(new Item(value, weight));
        }

        long[][] matrix = new long[2][capacity + 1];
        for (int k = 1; k <= numberOfElements; k++) {
            for (int j = 1; j <= capacity; j++) {
                matrix[1][j] = getBestValue(items, matrix, j, k);
            }
            swap(matrix);
        }

        long optValue = matrix[0][matrix[0].length - 1];
        System.out.println(String.format("Optimal value: %d", optValue));
    }

    private static void swap(long[][] matrix) {
        long[] tmp = new long[matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] = matrix[1][i];
            matrix[1][i] = 0;
        }

    }

    private static long getBestValue(List<Item> items, long[][] matrix, int weight, int item) {
        int currentItemWeight = items.get(item).getWeight();
        if (currentItemWeight > weight) return matrix[0][weight];
        long valueWithItemIncluded = items.get(item).getValue() +
                ((weight - currentItemWeight >= 0) ? matrix[0][weight - currentItemWeight] : 0);
        return Math.max(valueWithItemIncluded, matrix[0][weight]);
    }

    public static class Item {
        int value, weight;

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        public int getValue() {
            return value;
        }

        public int getWeight() {
            return weight;
        }
    }

}
