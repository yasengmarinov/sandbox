package course3.week2;

/**
 * Created by b06514a on 5/17/2017.
 */
public class UnionFind {

    int[] leaders;
    int[] ranks;
    int clusters;

    public UnionFind(int size) {
        leaders = new int[size + 1];
        ranks = new int[size + 1];
        clusters = size;

        for (int i = 0; i <= size; i++) {
            leaders[i] = i;
        }
    }

    public int find(int node) {
        int currentNode = node;
        while (currentNode != leaders[currentNode]) {
            currentNode = leaders[currentNode];
        }
        compressPath(node, currentNode);
        return currentNode;
    }

    private void compressPath(int node, int leader) {
        int currentNode = node;
        while (leaders[currentNode] != leader) {
            int tmp = leaders[currentNode];
            leaders[currentNode] = leader;
            currentNode = tmp;
        }

    }

    public void union(int node1, int node2) {
        if (connected(node1, node2))
            return;
        if (ranks[node1] <= ranks[node2]) {
            leaders[find(node1)] = find(node2);
            if (ranks[node1] == ranks[node2])
                ranks[node2]++;
        }
        else
            leaders[find(node2)] = find(node1);

        clusters--;
    }

    public boolean connected(int node1, int node2) {
        return find(node1) == find(node2);
    }

    public int clustersNumber() {
        return clusters;
    }
}
