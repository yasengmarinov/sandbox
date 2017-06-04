package course4.week2;

/**
 * Created by yasen on 6/3/17.
 */
public class City {

    public City(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    private Coordinates coordinates;

    public static class Coordinates {
        double x, y;

        public Coordinates(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

}
