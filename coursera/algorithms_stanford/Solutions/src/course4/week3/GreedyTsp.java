package course4.week3;

import common.Utils;
import common.tsp.City;
import common.tsp.DirectedEdge;

import java.util.*;

import static common.tsp.Utils.createCities;
import static common.tsp.Utils.euclideanDistance;

/**
 * Created by b06514a on 6/6/2017.
 * Greedy heuristic for solving the Travelling Salesman Problem
 * input: List with cities with location
 */
public class GreedyTsp {

    public static void main(String[] args) {

        List<String> input = Utils.parseFile(new GreedyTsp().getClass(), "nn.txt");

        List<City> cities = new ArrayList<>();
        createCities(input, cities);
        Set<Integer> visited = new HashSet<>(cities.size());

        double distance = 0F;
        int currentCity = 0;
        while (visited.size() != cities.size()) {
            if (visited.size() % 1000 == 0)
                System.out.printf("Visiting city number %d. Current distance: %f \n", visited.size(), distance);
            visited.add(currentCity);
            List<DirectedEdge> edges = getEdgesForCity(cities, currentCity);
            for (DirectedEdge edge : edges) {
                int nextCity = edge.getHead();
                if (!visited.contains(nextCity)) {
                    distance += edge.getDistance();
                    currentCity = nextCity;
                    break;
                }
            }
        }
        System.out.println("All cities visited");

        List<DirectedEdge> edges = getEdgesForCity(cities, currentCity);
        for (DirectedEdge edge : edges) {
            if (edge.getHead() == 0) {
                distance += edge.getDistance();
                break;
            }
        }

        System.out.println((int)distance);
    }

    private static List<DirectedEdge> getEdgesForCity(List<City> cities, int cityNumber) {
        List<DirectedEdge> edges = new ArrayList<>(cities.size());

        for (int i = 0; i < cities.size(); i++) {
            edges.add(new DirectedEdge(i, euclideanDistance(cities.get(i).getCoordinates(), cities.get(cityNumber).getCoordinates())));
        }
        Collections.sort(edges, DirectedEdge.distanceAndCityWise());
        return edges;
    }

}
