import java.util.Random;

import static coordinate.Coordinate.coordinate;
import static java.lang.System.out;

public class GameOfLife {

    public static void main(String[] args) throws InterruptedException {
        GameGrid gameGrid = generateGrid();
        final Integer step = Integer.parseInt(args[0]);

        while (true) {
            drawGrid(gameGrid);
            gameGrid = gameGrid.nextGeneration();
            Thread.sleep(step);
        }
    }

    private static GameGrid generateGrid() {
        final GameGrid gameGrid = new GameGrid();
        final Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 1000; i++) {
            gameGrid.makeAlive(coordinate(random.nextInt(50), random.nextInt(50)));
        }
        return gameGrid;
    }

    private static void drawGrid(GameGrid grid) {
        for (int x = grid.minimumX(); x <= grid.maximumX(); x++) {
            for (int y = grid.minimumY(); y <= grid.maximumY(); y++) {
                out.print(grid.isAlive(coordinate(x, y)) ? '*' : ' ');
            }
            out.println();
        }
    }
}
