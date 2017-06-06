package common.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by b06514a on 6/6/2017.
 */
public class Utils {
    public static double euclideanDistance(City.Coordinates coordinates1, City.Coordinates coordinates2) {
        return Math.sqrt(Math.pow(coordinates1.getX() - coordinates2.getX(), 2) +
                        Math.pow(coordinates1.getY() - coordinates2.getY(), 2));
    }

    public static Integer createCities(List<String> input, List<City> cities) {
        double minX = 1000000F;
        double minY = 1000000F;
        for (int i = 1; i < input.size(); i++) {
            double x = Double.valueOf(input.get(i).split(" ")[0]);
            double y = Double.valueOf(input.get(i).split(" ")[1]);
            if (x < minX) minX = x;
            if (y < minY) minY = y;
        }

        Integer numberOfCities = Integer.valueOf(input.get(0));
        for (int i = 1; i <= numberOfCities; i++) {
            String line = input.get(i);
            cities.add(new City(new City.Coordinates(Double.valueOf(line.split(" ")[0]) - minX,
                    Double.valueOf(line.split(" ")[1]) - minY)));
        }
        return numberOfCities;
    }

    public static void createEdgesFromCities(List<City> cities, List<Edge> edges, int candidateNumber) {
        for (int i = 0; i < cities.size() - 1; i++) {
            List<Edge> candidates = new ArrayList<>(cities.size());

            if (i % 1000 == 0)
                System.out.printf("Created edges for %d cities \n", i);
            for (int j = i + 1; j < cities.size(); j++) {
                candidates.add(new Edge(i, j, euclideanDistance(cities.get(i).getCoordinates(), cities.get(j).getCoordinates())));
            }

            Collections.sort(candidates);
            edges.addAll(candidates.subList(0, Math.min(candidates.size(), candidateNumber)));
        }
    }
}
