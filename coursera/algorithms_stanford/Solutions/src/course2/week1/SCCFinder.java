package course2.week1;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by b06514a on 4/27/2017.
 */
public class SCCFinder {

    private final Map<Integer, List<Integer>> graphList;
    private final Set<Integer> vertices;
    private final int numberOfVertices;

    private Map<Integer, List<Integer>> workingGraph;
    private Map<Integer, Boolean> visited;
    private Map<Integer, Integer> parents;
    private List<Integer> t;
    Boolean solvable = true;

    public SCCFinder(Map<Integer, List<Integer>> graphList, Set<Integer> vertices) {
        this.graphList = graphList;
        this.numberOfVertices = vertices.size();
        this.vertices = vertices;

        workingGraph = reverseList(graphList);

        visited = new HashMap(numberOfVertices);
        t = new ArrayList<>(numberOfVertices);
        parents = new HashMap(numberOfVertices);
    }

    public int[] find() {

        // Perform first run
        for (int i : vertices) {
            if (!isVertexVisited(i)) {
                exploreVertex(i, 0);
            }
        }

        // Revert graph
        workingGraph = graphList;
        visited.clear();

        // Perform second run
        for (int i = t.size() - 1; i >= 0; i--) {
            if (!isVertexVisited(t.get(i))) {
                exploreVertex(t.get(i), t.get(i));
            }
        }

        // Collect the SCC sizes
        List<Long> sccSizes = new ArrayList<>();
        sccSizes.addAll(parents.values().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values());

        // Get the 5 largest SCCs
        Collections.sort(sccSizes);
        Collections.reverse(sccSizes);

        int[] result = new int[Math.min(5, sccSizes.size())];
        for (int i = 0; i < result.length; i++) {
            result [i] = Math.toIntExact(sccSizes.get(i));
        }
        return result;
    }

    public boolean is2SATProblemSolvable() {

        // Perform first run
        for (int i : vertices) {
            if (!isVertexVisited(i)) {
                exploreVertex(i, 0);
            }
        }

        // Revert graph
        workingGraph = graphList;
        visited.clear();

        // Perform second run
        for (int i = t.size() - 1; i >= 0; i--) {
            if (!isVertexVisited(t.get(i))) {
                Set<Integer> connected = new HashSet<>();
                exploreVertex(t.get(i), connected);
            }
        }

        return solvable;

    }

    private void exploreVertex(int i, Set<Integer> verticesInScc) {
        if (isVertexVisited(i))
            return;

        verticesInScc.add(i);
        if (verticesInScc.contains(-i))
            solvable = false;

        visited.put(i, true);
        List connected = workingGraph.get(i);
        if (connected != null) {
            for (Object vertex : connected) {
                exploreVertex(Integer.valueOf(vertex.toString()), verticesInScc);
            }
        }



    }


    private void exploreVertex(int i, int parent) {
        if (isVertexVisited(i))
            return;

        visited.put(i, true);
        List connected = workingGraph.get(i);
        if (connected != null) {
            for (Object vertex : connected) {
                exploreVertex(Integer.valueOf(vertex.toString()), parent);
            }
        }
        // Parent == 0 in the first run only.
        if (parent == 0) setT(i);
        else setParent(i, parent);

    }

    private Map<Integer, List<Integer>> reverseList(Map<Integer, List<Integer>> graphList) {
        Map<Integer, List<Integer>> copy = new HashMap<>(graphList.size());
        
        for (Integer tail : graphList.keySet()) {
            for (Integer head : graphList.get(tail)) {
                if (!copy.containsKey(head)) {
                    copy.put(head, new LinkedList<>());
                }
                copy.get(head).add(tail);
            }
            
        }
        return copy;
    }

    private void setParent(int vertex, int parent) {
        parents.put(vertex, parent);
    }

    private void setT(int i) {
        t.add(i);
    }

    private boolean isVertexVisited(int i) {
        return visited.containsKey(i);
    }
}
