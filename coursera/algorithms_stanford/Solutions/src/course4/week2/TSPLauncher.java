package course4.week2;

import Common.Utils;

import java.util.*;

/**
 * Created by yasen on 6/3/17.
 */
public class TSPLauncher {

    public static void main(String[] args) {

        List<String> input = Utils.parseFile(new TSPLauncher().getClass(), "tsp.txt");
        Integer numberOfCities = Integer.valueOf(input.get(0));

        City[] cities = new City[numberOfCities];

        for (int i = 1; i <= numberOfCities; i++) {
            String line = input.get(i);
            cities[i - 1] = new City(new City.Coordinates(Double.valueOf(line.split(" ")[0]),
                    Float.valueOf(line.split(" ")[1])));
        }

        List<Edge> edges = new ArrayList<>(numberOfCities * (numberOfCities - 1) / 2);
        for (int i = 0; i < cities.length - 1; i++) {
            for (int j = i + 1; j < cities.length; j++) {
                edges.add(new Edge(i, j, euclideanDistance(cities[i].getCoordinates(), cities[j].getCoordinates())));
            }
        }

        Collections.sort(edges);

        TSPFinder tspFinder = new TSPFinder(numberOfCities, edges);
        System.out.println(tspFinder.find());

    }

    private static float euclideanDistance(City.Coordinates coordinates1, City.Coordinates coordinates2) {
        return (float)Math.sqrt(Math.pow(coordinates1.getX() - coordinates2.getX(), 2) +
                        Math.pow(coordinates1.getY() - coordinates2.getY(), 2));
    }

}
