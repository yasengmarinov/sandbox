package course2.week1;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by b06514a on 4/27/2017.
 */
public class SCCFinderLauncher {

    public static void main(String[] args) {

        Path inputFile = null;
        try {
            inputFile = Paths.get(new SCCFinderLauncher().getClass().getResource("SCC.txt").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List input = null;
        try {
            input = Files.readAllLines(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Integer, List<Integer>> graphList = new HashMap();
        Set<Integer> verticesSet = new HashSet<>();
        for (Object row: input) {
            String[] vertices = row.toString().split(" ");
            int tail = Integer.valueOf(vertices[0]);
            int head = Integer.valueOf(vertices[1]);
            verticesSet.add(tail);
            verticesSet.add(head);
            if (!graphList.containsKey(tail)) {
                graphList.put(tail, new LinkedList<>());
            }
            graphList.get(tail).add(head);
        }

        System.out.println("Parsed file");

        SCCFinder sccFinder = new SCCFinder(graphList, verticesSet);
        for (int size: sccFinder.find())
            System.out.println(size);
    }

}
