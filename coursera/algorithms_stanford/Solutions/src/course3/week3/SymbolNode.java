package course3.week3;

/**
 * Created by b06514a on 5/23/2017.
 */
public class SymbolNode implements Comparable<SymbolNode> {
    long weight;
    SymbolNode left, right;

    public SymbolNode(long weight) {
        this.weight = weight;
    }

    public SymbolNode(SymbolNode left, SymbolNode right) {
        this.left = left;
        this.right = right;
        this.weight = left.weight + right.weight;
    }

    public SymbolNode getLeft() {
        return left;
    }

    public SymbolNode getRight() {
        return right;
    }

    @Override
    public int compareTo(SymbolNode o) {
        return Long.compare(this.weight, o.weight);
    }
}
