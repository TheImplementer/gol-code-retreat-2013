package coordinate;

import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Callable2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class Coordinate {

    final private int x;
    final private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate coordinate(int x, int y) {
        return new Coordinate(x, y);
    }

    @Override
    public int hashCode() {
        return x + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate == false) {
            return false;
        }
        final Coordinate that = (Coordinate) obj;
        final boolean isXEqual = x == that.x;
        final boolean isYEqual = y == that.y;
        return (isXEqual && isYEqual);
    }

    public Set<Coordinate> getNeighbours() {
        final Set<Coordinate> neighbours = new HashSet<>();
        final List<Integer> horizontalCoords = asList(x - 1, x, x + 1);
        for (final Integer coord : horizontalCoords) {
            generateHorizontalNeighbours(neighbours, coord);
        }
        return neighbours;
    }

    private void generateHorizontalNeighbours(Set<Coordinate> neighbours, int dx) {
        for (int dy = y - 1; dy <= y + 1; dy++) {
            if (!(dx == x && dy == y)) {
                neighbours.add(new Coordinate(dx, dy));
            }
        }
    }

    public static class functions {

        public static Callable1<Coordinate, Integer> x = new Callable1<Coordinate, Integer>() {
            @Override
            public Integer call(Coordinate coordinate) throws Exception {
                return coordinate.x;
            }
        };

        public static Callable1<Coordinate, Integer> y = new Callable1<Coordinate, Integer>() {
            @Override
            public Integer call(Coordinate coordinate) throws Exception {
                return coordinate.y;
            }
        };

        public static Callable2<Integer, Integer, Integer> minimum = new Callable2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer current, Integer minimum) throws Exception {
                return current.intValue() < minimum.intValue() ? current : minimum;
            }
        };

        public static Callable2<Integer, Integer, Integer> maximum = new Callable2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer current, Integer maximum) throws Exception {
                return current.intValue() > maximum.intValue() ? current : maximum;
            }
        };
    }
}
