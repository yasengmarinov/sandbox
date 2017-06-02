package course4.week1;

/**
 * Created by b06514a on 6/1/2017.
 */
public class AppPairsShortestPathFinder {

    DirectedWeightedGraph graph;
    int[][][] pathMatrix;

    public AppPairsShortestPathFinder(DirectedWeightedGraph graph) {
        this.graph = graph;
        int numberOfVertices = graph.getVertices().size();
        pathMatrix = new int[numberOfVertices][numberOfVertices][2];
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                for (int k = 0; k < 2; k++) {
                    pathMatrix[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }

    }

    public Integer find() {
        int numberOfVertices = graph.getVertices().size() - 1;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 1; i <= numberOfVertices; i++) {
            pathMatrix[i][i][0] = 0;
        }
        for (DirectedWeightedGraph.Edge edge : graph.getEdges()) {
            pathMatrix[edge.getTail().getId()][edge.getHead().getId()][0] = edge.getWeight();
        }

        for (int k = 1; k <= numberOfVertices; k++) {
            for (int i = 1; i <= numberOfVertices; i++) {
                for (int j = 1; j <= numberOfVertices; j++) {
                    pathMatrix[i][j][1] = (int)Math.min((long)pathMatrix[i][j][0], (long)pathMatrix[i][k][0] + pathMatrix[k][j][0]);
                    if (pathMatrix[i][j][1] < minDistance)
                        minDistance = pathMatrix[i][j][1];
                }
            }
            reverseMatrix();
        }
        for (int i = 1; i <= numberOfVertices; i++) {
            if (pathMatrix[i][i][0] != 0) {
                return null;
            }
        }
        return minDistance;
    }

    private void reverseMatrix() {
        for (int i = 0; i < pathMatrix.length; i++) {
            for (int j = 0; j < pathMatrix[0].length; j++) {
                pathMatrix[i][j][0] = pathMatrix[i][j][1];
                pathMatrix[i][j][1] = Integer.MAX_VALUE;
            }
        }
    }

}
