import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Predicate;
import com.googlecode.totallylazy.Sequence;
import coordinate.Coordinate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.predicates.Not.not;
import static coordinate.Coordinate.functions.*;
import static java.lang.Integer.valueOf;
import static java.util.Map.Entry;

public class GameGrid {

    private Set<Coordinate> cells;

    public GameGrid() {
        cells = new HashSet<>();
    }

    public GameGrid(Set<Coordinate> newGeneration) {
        cells = newGeneration;
    }

    public void makeAlive(Coordinate coord) {
        cells.add(coord);
    }

    public boolean isAlive(Coordinate coord) {
        return cells.contains(coord);
    }

    public GameGrid nextGeneration() {
        final Sequence<Coordinate> cellsStillAlive = sequence(cells).filter(shouldStayAlive());
        final HashMap<Coordinate, Integer> deadCellsAndCount = sequence(cells).
                flatMap(deadNeighbours()).
                fold(new HashMap<Coordinate, Integer>(), counted());
        final Sequence<Coordinate> deadCellToBeRevived = sequence(deadCellsAndCount.entrySet()).
                filter(hasThreeAliveNeighbours()).
                map(toCoordinate());
        return new GameGrid(cellsStillAlive.join(deadCellToBeRevived).toSet());
    }

    private Callable2<HashMap<Coordinate, Integer>, Coordinate, HashMap<Coordinate, Integer>> counted() {
        return new Callable2<HashMap<Coordinate, Integer>, Coordinate, HashMap<Coordinate, Integer>>() {
            @Override
            public HashMap<Coordinate, Integer> call(HashMap<Coordinate, Integer> accumulator, Coordinate coordinate) throws Exception {
                final int aliveNeighbours = accumulator.containsKey(coordinate) ? accumulator.get(coordinate) : 0;
                accumulator.put(coordinate, aliveNeighbours + 1);
                return accumulator;
            }
        };
    }

    private Callable1<Coordinate, Iterable<Coordinate>> deadNeighbours() {
        return new Callable1<Coordinate, Iterable<Coordinate>>() {
            @Override
            public Iterable<Coordinate> call(Coordinate coordinate) throws Exception {
                return sequence(coordinate.getNeighbours()).filter(not(isCoordinateAlive()));
            }
        };
    }

    private Predicate<Coordinate> shouldStayAlive() {
        return new Predicate<Coordinate>() {
            @Override
            public boolean matches(Coordinate cell) {
                final Sequence<Coordinate> aliveNeighbours = sequence(cell.getNeighbours()).filter(isCoordinateAlive());
                final int aliveNeighboursCount = aliveNeighbours.size();
                return aliveNeighboursCount == 2 || aliveNeighboursCount == 3;
            }
        };
    }

    private Predicate<Coordinate> isCoordinateAlive() {
        return new Predicate<Coordinate>() {
            @Override
            public boolean matches(Coordinate coordinate) {
                return isAlive(coordinate);
            }
        };
    }

    private Callable1<Entry<Coordinate, Integer>, Coordinate> toCoordinate() {
        return new Callable1<Entry<Coordinate, Integer>, Coordinate>() {
            @Override
            public Coordinate call(Entry<Coordinate, Integer> deadCellEntry) throws Exception {
                return deadCellEntry.getKey();
            }
        };
    }

    private Predicate<Entry<Coordinate, Integer>> hasThreeAliveNeighbours() {
        return new Predicate<Entry<Coordinate, Integer>>() {
            @Override
            public boolean matches(Entry<Coordinate, Integer> deadCellEntry) {
                final Integer aliveNeighbours = deadCellEntry.getValue();
                return aliveNeighbours.equals(3);
            }
        };
    }

    public int minimumX() {
        return bound(x, minimum);
    }

    public int minimumY() {
        return bound(y, minimum);
    }

    public int maximumX() {
        return bound(x, maximum);
    }

    public int maximumY() {
        return bound(y, maximum);
    }

    private int bound(Callable1<Coordinate, Integer> axis, Callable2<Integer, Integer, Integer> way) {
        final Sequence<Integer> ys = sequence(cells).map(axis);
        final int seed = ys.isEmpty() ? 0 : ys.first();
        return ys.fold(valueOf(seed), way);
    }
}
