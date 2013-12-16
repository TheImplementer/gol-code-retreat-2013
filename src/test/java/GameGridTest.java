import coordinate.Coordinate;
import org.junit.Test;

import static coordinate.Coordinate.coordinate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GameGridTest {

    @Test
    public void shouldBeAbleToMakeACellAlive() {
        final GameGrid gameGrid = new GameGrid();
        final Coordinate coordinate = coordinate(0, 0);

        gameGrid.makeAlive(coordinate);

        assertThat(gameGrid.isAlive(coordinate), is(true));
    }

    @Test
    public void shouldRecognizeDeadCells() {
        final GameGrid gameGrid = new GameGrid();

        assertThat(gameGrid.isAlive(coordinate(0, 0)), is(false));
    }

    @Test
    public void aCellShouldDieIfItHasLessThanTwoNeighbours() {
        final GameGrid gameGrid = new GameGrid();
        final Coordinate coordinate = coordinate(0, 0);

        gameGrid.makeAlive(coordinate);
        final GameGrid newGeneration = gameGrid.nextGeneration();

        assertThat(newGeneration.isAlive(coordinate), is(false));
    }

    @Test
    public void aCellShouldRemainAliveIfItHasTwoNeighbours() {
        final GameGrid gameGrid = new GameGrid();
        final Coordinate coordinate = coordinate(0, 0);

        gameGrid.makeAlive(coordinate);
        gameGrid.makeAlive(coordinate(1, 0));
        gameGrid.makeAlive(coordinate(0, 1));
        final GameGrid newGeneration = gameGrid.nextGeneration();

        assertThat(newGeneration.isAlive(coordinate), is(true));
    }

    @Test
    public void aCellShouldRemainAliveIfItHasThreeNeighbours() {
        final GameGrid gameGrid = new GameGrid();
        final Coordinate coordinate = coordinate(0, 0);

        gameGrid.makeAlive(coordinate);
        gameGrid.makeAlive(coordinate(1, 0));
        gameGrid.makeAlive(coordinate(0, 1));
        gameGrid.makeAlive(coordinate(0, -1));
        final GameGrid newGeneration = gameGrid.nextGeneration();

        assertThat(newGeneration.isAlive(coordinate), is(true));
    }

    @Test
    public void aCellShouldDieIfItHasMoreThanThreeNeighbours() {
        final GameGrid gameGrid = new GameGrid();
        final Coordinate coordinate = coordinate(0, 0);

        gameGrid.makeAlive(coordinate);
        gameGrid.makeAlive(coordinate(1, 0));
        gameGrid.makeAlive(coordinate(0, 1));
        gameGrid.makeAlive(coordinate(0, -1));
        gameGrid.makeAlive(coordinate(-1, -1));
        final GameGrid newGeneration = gameGrid.nextGeneration();

        assertThat(newGeneration.isAlive(coordinate), is(false));
    }

    @Test
    public void aCellShouldSpringToLifeIfItHasThreeNeighbours() {
        final GameGrid gameGrid = new GameGrid();
        final Coordinate coordinate = coordinate(0, 0);

        gameGrid.makeAlive(coordinate(0, 1));
        gameGrid.makeAlive(coordinate(0, -1));
        gameGrid.makeAlive(coordinate(-1, -1));
        final GameGrid newGeneration = gameGrid.nextGeneration();

        assertThat(newGeneration.isAlive(coordinate), is(true));
    }

    @Test
    public void shouldReturnMinimumX() {
        final GameGrid gameGrid = new GameGrid();

        gameGrid.makeAlive(coordinate(-4, 0));

        assertThat(gameGrid.minimumX(), is(-4));
    }

    @Test
    public void shouldReturnMinimumY() {
        final GameGrid gameGrid = new GameGrid();

        gameGrid.makeAlive(coordinate(0, -4));

        assertThat(gameGrid.minimumY(), is(-4));
    }

    @Test
    public void shouldReturnMaximumX() {
        final GameGrid gameGrid = new GameGrid();

        gameGrid.makeAlive(coordinate(4, 0));

        assertThat(gameGrid.maximumX(), is(4));
    }

    @Test
    public void shouldReturnMaximumY() {
        final GameGrid gameGrid = new GameGrid();

        gameGrid.makeAlive(coordinate(0, 4));

        assertThat(gameGrid.maximumY(), is(4));
    }
}
