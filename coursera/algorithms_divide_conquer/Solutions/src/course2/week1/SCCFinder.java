package course2.week1;

import java.util.*;

/**
 * Created by b06514a on 4/27/2017.
 */
public class SCCFinder {

    private final List<List<Integer>> graphList;
    private List<List<Integer>> workingGraph;
    private final int verticesCount;
    private boolean[] visited;
    private int[] t, parents;
    private int tCounter;

    public SCCFinder(List<List<Integer>> graphList) {
        this.graphList = graphList;
        workingGraph = reverseList(graphList);
        verticesCount = graphList.size();

        visited = new boolean[verticesCount];
        t = new int[verticesCount];
        parents = new int[verticesCount];
        tCounter = verticesCount;
    }

    public int[] find() {

        // Perform first run
        for (int i = verticesCount; i > 0; i--) {
            if (!isVertexVisited(i)) {
                exploreVertex(i, 0);
            }
        }

        // Revert graph
        workingGraph = graphList;
        visited = new boolean[verticesCount];

        // Perform second run
        for (int i = 0; i < t.length; i++) {
            if (!isVertexVisited(t[i])) {
                exploreVertex(t[i], t[i]);
            }
        }

        // Collect the SCC sizes
        int[] sccSizes = new int[verticesCount];
        for (int parent: parents)
            sccSizes[parent - 1]++;

        // Get the 5 largest SCCs
        Arrays.sort(sccSizes);
        int[] result = new int[5];
        for (int i = 0; i < 5; i++) {
            result [i] = sccSizes[sccSizes.length - 1 - i];
        }
        return result;
    }

    private void exploreVertex(int i, int parent) {
        if (isVertexVisited(i))
            return;

        visited[i - 1] = true;
        List connected = workingGraph.get(i - 1);
        for (Object vertex : connected) {
            exploreVertex(Integer.valueOf(vertex.toString()), parent);
        }

        // Parent == 0 in the first run only.
        if (parent == 0) setT(i);
        else setParent(i, parent);

    }

    private List<List<Integer>> reverseList(List<List<Integer>> graphList) {
        List<List<Integer>> copy = new ArrayList<>(graphList.size());
        for (int i = 0; i < graphList.size(); i++) {
            copy.add(new LinkedList<>());
        }
        for (int i = 0; i < graphList.size(); i++) {
            for (Object vertex : graphList.get(i)) {
                copy.get(Integer.valueOf(vertex.toString()) - 1).add(i + 1);
            }
        }
        return copy;
    }

    private void setParent(int vertex, int parent) {
        parents[vertex - 1] = parent;
    }

    private void setT(int i) {
        t[--tCounter] = i;
    }

    private boolean isVertexVisited(int i) {
        return visited[i - 1] == true;
    }
}
