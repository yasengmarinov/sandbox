package course4.week2;

import common.Utils;
import common.tsp.City;
import common.tsp.Edge;

import java.util.*;

/**
 * Created by yasen on 6/3/17.
 * * Finds a solution of the Travelling Salesman Problem
 * Optimal way to visit each city exactly once
 * input: list with cities and their location
 */
public class TSPLauncher {

    public static void main(String[] args) {

        List<String> input = Utils.parseFile(new TSPLauncher().getClass(), "tsp.txt");
        List<City> cities = new ArrayList<>();

        Integer numberOfCities = common.tsp.Utils.createCities(input, cities);

        List<Edge> edges = new ArrayList<>(numberOfCities * (numberOfCities - 1) / 2);
        common.tsp.Utils.createEdgesFromCities(cities, edges, cities.size());

        TSPFinder tspFinder = new TSPFinder(numberOfCities, edges);
        System.out.println((int)tspFinder.find());

    }

}
