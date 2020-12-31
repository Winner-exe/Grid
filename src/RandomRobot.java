import java.util.*;

public class RandomRobot extends Robot
{
    private final Random rng;

    public RandomRobot(String fileName, int rows, int columns, Cell startPos, Cell[][] grid) {
        super(fileName, rows, columns, startPos, null, grid);

        rng = new Random();
    }

    @Override
    public void move()
    {
        if (!getValidMoves().isEmpty() && pos.getTag() != 2)
        {
            int direction = getValidMoves().get(rng.nextInt(getValidMoves().size()));
            switch (direction)
            {
                case 0:
                    super.move(grid[pos.getCoordinates()[0]+1][pos.getCoordinates()[1]]);
                    setDirection(Grid.Direction.DOWN);
                    break;
                case 1:
                    super.move(grid[pos.getCoordinates()[0]][pos.getCoordinates()[1]-1]);
                    setDirection(Grid.Direction.LEFT);
                    break;
                case 2:
                    super.move(grid[pos.getCoordinates()[0]][pos.getCoordinates()[1]+1]);
                    setDirection(Grid.Direction.RIGHT);
                    break;
                case 3:
                    super.move(grid[pos.getCoordinates()[0]-1][pos.getCoordinates()[1]]);
                    setDirection(Grid.Direction.UP);
                    break;
                default:
                    break;
            }
        }
    }

    private ArrayList<Integer> getValidMoves()
    {
        ArrayList<Integer> moves = new ArrayList<>();
        int[] cursor = pos.getCoordinates();
        if (cursor[0] + 1 < grid.length && grid[cursor[0] + 1][cursor[1]].getTag() != 1) moves.add(0);
        if (cursor[1] - 1 >= 0 && grid[cursor[0]][cursor[1] - 1].getTag() != 1) moves.add(1);
        if (cursor[1] + 1 < grid[0].length && grid[cursor[0]][cursor[1] + 1].getTag() != 1) moves.add(2);
        if (cursor[0] - 1 >= 0 && grid[cursor[0] - 1][cursor[1]].getTag() != 1) moves.add(3);
        return moves;
    }
}
