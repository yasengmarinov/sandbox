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

        List<List<Integer>> graphList = new ArrayList<>(520000);

        for (Object row: input) {
            String[] vertices = row.toString().split(" ");
            int tail = Integer.valueOf(vertices[0]), head = Integer.valueOf(vertices[1]);
            while (graphList.size() <= tail - 1)
                graphList.add(new LinkedList<Integer>());
            graphList.get(tail - 1).add(head);
        }

        System.out.println("Parsed file");

        SCCFinder sccFinder = new SCCFinder(graphList);
        for (int size: sccFinder.find())
            System.out.println(size);
    }

}
