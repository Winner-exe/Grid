import java.util.Objects;

public class RightRobot extends Robot
{
    private enum PreviousMove
    {
        NONE, REALIGN, MOVE_FACING, FOLLOW_WALL_DIAGONAL
    }

    private PreviousMove previousMove;

    public RightRobot(String fileName, int rows, int columns, Cell startPos, Cell[][] grid)
    {
        super(fileName, rows, columns, startPos, null, grid);

        setDirection(Grid.Direction.DOWN);

        previousMove = PreviousMove.NONE;
    }

    @Override
    public void move()
    {
        if (pos.getTag() != 2)
        {
            //Right hand is a wall
            if (rightHand() == null || Objects.requireNonNull(rightHand()).getTag() == 1) {
                //Facing a path -> move forward
                if (facing() != null && Objects.requireNonNull(facing()).getTag() != 1) {
                    super.move(Objects.requireNonNull(facing()));
                    previousMove = PreviousMove.MOVE_FACING;
                }
                //Facing a wall -> turn counterclockwise to have a wall on the right side again
                else {
                    turnCounterClockwise();
                    previousMove = PreviousMove.REALIGN;
                }
            }
            //Right hand is a path, previous move was a move forward -> turn clockwise to follow wall diagonally
            else if (previousMove == PreviousMove.MOVE_FACING) {
                turnClockwise();
                previousMove = PreviousMove.FOLLOW_WALL_DIAGONAL;
            }
            //Right hand is a path, previous move was follow wall diagonally -> move forward to hit a new right-hand wall
            else if (previousMove == PreviousMove.FOLLOW_WALL_DIAGONAL) {
                super.move(Objects.requireNonNull(facing()));
                previousMove = PreviousMove.MOVE_FACING;
            }
            //Right hand is a path, no previous move or previous move was a normal turn -> turn to realign
            else {
                turnCounterClockwise();
                previousMove = PreviousMove.REALIGN;
            }
        }
    }

    private void turnClockwise()
    {
        switch (direction.dirCode)
        {
            case 0:
                setDirection(Grid.Direction.LEFT);
                break;
            case 1:
                setDirection(Grid.Direction.UP);
                break;
            case 2:
                setDirection(Grid.Direction.DOWN);
                break;
            case 3:
                setDirection(Grid.Direction.RIGHT);
                break;
            default:
                break;
        }
    }

    private void turnCounterClockwise()
    {
        switch (direction.dirCode)
        {
            case 0:
                setDirection(Grid.Direction.RIGHT);
                break;
            case 1:
                setDirection(Grid.Direction.DOWN);
                break;
            case 2:
                setDirection(Grid.Direction.UP);
                break;
            case 3:
                setDirection(Grid.Direction.LEFT);
                break;
            default:
                break;
        }
    }

    private Cell rightHand()
    {
        switch (direction.dirCode) {
            case 0:
                if (pos.getCoordinates()[1] - 1 >= 0)
                    return grid[pos.getCoordinates()[0]][pos.getCoordinates()[1] - 1];
                break;
            case 1:
                if (pos.getCoordinates()[0] - 1 >= 0)
                    return grid[pos.getCoordinates()[0] - 1][pos.getCoordinates()[1]];
                break;
            case 2:
                if (pos.getCoordinates()[0] + 1 < grid.length)
                    return grid[pos.getCoordinates()[0] + 1][pos.getCoordinates()[1]];
                break;
            case 3:
                if (pos.getCoordinates()[1] + 1 < grid[0].length)
                    return grid[pos.getCoordinates()[0]][pos.getCoordinates()[1] + 1];
                break;
            default:
                throw new IllegalArgumentException();
        }
        return null;
    }

    private Cell facing()
    {
        switch (direction.dirCode) {
            case 0:
                if (pos.getCoordinates()[0] + 1 < grid.length)
                    return grid[pos.getCoordinates()[0] + 1][pos.getCoordinates()[1]];
                break;
            case 1:
                if (pos.getCoordinates()[1] - 1 >= 0)
                    return grid[pos.getCoordinates()[0]][pos.getCoordinates()[1] - 1];
                break;
            case 2:
                if (pos.getCoordinates()[1] + 1 < grid[0].length)
                    return grid[pos.getCoordinates()[0]][pos.getCoordinates()[1] + 1];
                break;
            case 3:
                if (pos.getCoordinates()[0] - 1 >= 0)
                    return grid[pos.getCoordinates()[0] - 1][pos.getCoordinates()[1]];
                break;
            default:
                throw new IllegalArgumentException();
        }
        return null;
    }
}
