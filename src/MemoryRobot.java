import java.util.*;

public class MemoryRobot extends Robot
{
    private final Stack<Cell> cursor;
    private final HashSet<Cell> memory;
    private final Random rng;

    public MemoryRobot(String fileName, int rows, int columns, Cell startPos, Cell[][] grid)
    {
        super(fileName, rows, columns, startPos, null, grid);

        cursor = new Stack<>();
        cursor.push(startPos);

        memory = new HashSet<>();
        memory.add(startPos);

        rng = new Random();
    }

    @Override
    public void move()
    {
        if (pos.getTag() != 2)
        {
            //There is a valid move to an unexplored path -> explore one of such paths
            if (!getValidNewMoves().isEmpty()) {
                int direction = getValidNewMoves().get(rng.nextInt(getValidNewMoves().size()));
                switch (direction) {
                    case 0:
                        super.move(grid[pos.getCoordinates()[0] + 1][pos.getCoordinates()[1]]);
                        setDirection(Grid.Direction.DOWN);
                        break;
                    case 1:
                        super.move(grid[pos.getCoordinates()[0]][pos.getCoordinates()[1] - 1]);
                        setDirection(Grid.Direction.LEFT);
                        break;
                    case 2:
                        super.move(grid[pos.getCoordinates()[0]][pos.getCoordinates()[1] + 1]);
                        setDirection(Grid.Direction.RIGHT);
                        break;
                    case 3:
                        super.move(grid[pos.getCoordinates()[0] - 1][pos.getCoordinates()[1]]);
                        setDirection(Grid.Direction.UP);
                        break;
                    default:
                        break;
                }

                memory.add(pos);
                cursor.push(pos);
            }
            //There are no valid moves to an unexplored path, but there is a valid move to the cursor's backtrack
            // -> backtrack until such a move to an unexplored path exists
            else if (getValidMoveDestinations().contains(cursor.peek()))
            {
                int x0 = pos.getCoordinates()[1];
                int y0 = pos.getCoordinates()[0];
                super.move(cursor.pop());

                switch (pos.getCoordinates()[0] - y0)
                {
                    case 1:
                        setDirection(Grid.Direction.DOWN);
                        break;
                    case -1:
                        setDirection(Grid.Direction.UP);
                        break;
                    default:
                        break;
                }

                switch (pos.getCoordinates()[1] - x0)
                {
                    case 1:
                        setDirection(Grid.Direction.RIGHT);
                        break;
                    case -1:
                        setDirection(Grid.Direction.LEFT);
                        break;
                    default:
                        break;
                }
            }
            //There are no valid moves to an unexplored path, but there is no valid move to the cursor's backtrack
            // -> move in the same direction as previous
            else
            {
                switch (direction.dirCode) {
                    case 0:
                        if (pos.getCoordinates()[0] + 1 < grid.length)
                            super.move(grid[pos.getCoordinates()[0] + 1][pos.getCoordinates()[1]]);
                        break;
                    case 1:
                        if (pos.getCoordinates()[1] - 1 >= 0)
                            super.move(grid[pos.getCoordinates()[0]][pos.getCoordinates()[1] - 1]);
                        break;
                    case 2:
                        if (pos.getCoordinates()[1] + 1 < grid[0].length)
                            super.move(grid[pos.getCoordinates()[0]][pos.getCoordinates()[1] + 1]);
                        break;
                    case 3:
                        if (pos.getCoordinates()[0] - 1 >= 0)
                            super.move(grid[pos.getCoordinates()[0] - 1][pos.getCoordinates()[1]]);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
    }

    private ArrayList<Cell> getValidMoveDestinations()
    {
        ArrayList<Cell> moves = new ArrayList<>();
        moves.add(pos);
        int[] cursor = pos.getCoordinates();
        if (cursor[0] + 1 < grid.length && grid[cursor[0] + 1][cursor[1]].getTag() != 1) moves.add(grid[cursor[0] + 1][cursor[1]]);
        if (cursor[1] - 1 >= 0 && grid[cursor[0]][cursor[1] - 1].getTag() != 1) moves.add(grid[cursor[0]][cursor[1] - 1]);
        if (cursor[1] + 1 < grid[0].length && grid[cursor[0]][cursor[1] + 1].getTag() != 1) moves.add(grid[cursor[0]][cursor[1] + 1]);
        if (cursor[0] - 1 >= 0 && grid[cursor[0] - 1][cursor[1]].getTag() != 1) moves.add(grid[cursor[0] - 1][cursor[1]]);
        return moves;
    }

    private ArrayList<Integer> getValidNewMoves()
    {
        ArrayList<Integer> moves = new ArrayList<>();
        int[] cursor = pos.getCoordinates();
        if (cursor[0] + 1 < grid.length && grid[cursor[0] + 1][cursor[1]].getTag() != 1 &&
                !memory.contains(grid[cursor[0] + 1][cursor[1]])) moves.add(0);
        if (cursor[1] - 1 >= 0 && grid[cursor[0]][cursor[1] - 1].getTag() != 1 &&
                !memory.contains(grid[cursor[0]][cursor[1] - 1])) moves.add(1);
        if (cursor[1] + 1 < grid[0].length && grid[cursor[0]][cursor[1] + 1].getTag() != 1 &&
                !memory.contains(grid[cursor[0]][cursor[1] + 1])) moves.add(2);
        if (cursor[0] - 1 >= 0 && grid[cursor[0] - 1][cursor[1]].getTag() != 1 &&
                !memory.contains(grid[cursor[0] - 1][cursor[1]])) moves.add(3);
        return moves;
    }
}
