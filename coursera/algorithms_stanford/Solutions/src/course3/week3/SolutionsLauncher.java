package course3.week3;

import Common.Heap;
import Common.Utils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by b06514a on 5/23/2017.
 */
public class SolutionsLauncher {

    public static void main(String[] args) {

        huffmanCoding();
        maximumWeightedIndependentSet();

    }

    private static void maximumWeightedIndependentSet() {
        List<String> input = Utils.parseFile(new SolutionsLauncher().getClass(), "mwis.txt");
        int numberOfVertices = Integer.valueOf(input.get(0));
        int[] vertices = new int[numberOfVertices + 1];
        for (int i = 1; i < input.size(); i++) {
            vertices[i] = Integer.valueOf(input.get(i));
        }
        int[] maximumWeights = new int[numberOfVertices + 1];
        maximumWeights[0] = 0;
        maximumWeights[1] = vertices[1];
        for (int i = 2; i < maximumWeights.length; i++) {
            if (maximumWeights[i - 2] + vertices[i] > maximumWeights[i - 1])
                maximumWeights[i] = maximumWeights[i - 2] + vertices[i];
            else maximumWeights[i] = maximumWeights[i - 1];
        }


        Set<Integer> containedInMax = new HashSet<>();

        int i = maximumWeights.length - 1;
        while (i > 0) {
            if (maximumWeights[i] == maximumWeights[i - 1]) {
                i--;
            } else {
                containedInMax.add(i);
                i-= 2;
            }
        }

        System.out.println("Vertices contained: ");
        String checkIfExist = "1, 2, 3, 4, 17, 117, 517, 997";
        for (String vertex : checkIfExist.split(", ")) {
            if (containedInMax.contains(Integer.valueOf(vertex))) {
                System.out.print(1);
            } else System.out.print(0);
        }
    }

    private static void huffmanCoding() {
        List<String> input = Utils.parseFile(new SolutionsLauncher().getClass(), "huffman.txt");
        int numberOfSymbols = Integer.valueOf(input.get(0));
        Heap<SymbolNode> symbols = new Heap<SymbolNode>(Heap.getMinComparator());
        Heap<SymbolNode> combineNodes = new Heap<SymbolNode>(Heap.getMinComparator());

        for (int i = 1; i < input.size(); i++) {
            symbols.put(new SymbolNode(Integer.valueOf(input.get(i))));
        }

        while (symbols.size() > 0 || combineNodes.size() > 1) {
            SymbolNode node1 = getMinNode(symbols, combineNodes);
            SymbolNode node2 = getMinNode(symbols, combineNodes);
            combineNodes.put(new SymbolNode(node1, node2));
        }

        List<SymbolNode> list1 = new LinkedList<>();
        List<SymbolNode> list2 = new LinkedList<>();
        int minLegth = 0;
        list1.add(combineNodes.getFirst());

        int currentIndex = 0;
        while (!(list1.isEmpty() && list2.isEmpty())) {
            for (SymbolNode node : list1) {
                if (node.getLeft() != null) {
                    list2.add(node.getLeft());
                    list2.add(node.getRight());
                } else if (minLegth == 0) minLegth = currentIndex;
            }
            list1.clear();
            list1.addAll(list2);
            list2.clear();

            currentIndex++;
        }

        System.out.println("Max length: " + --currentIndex);
        System.out.println("Min length: " + minLegth);
    }

    private static SymbolNode getMinNode(Heap<SymbolNode> heap1, Heap<SymbolNode> heap2) {
        if (heap1.size() == 0)
            return heap2.removeFirst();
        if (heap2.size() == 0)
            return heap1.removeFirst();
        if (heap1.getFirst().compareTo(heap2.getFirst()) <= 0)  return heap1.removeFirst();
        else                                                    return heap2.removeFirst();
    }
}
