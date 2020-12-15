import java.io.*;
import java.nio.file.Path;
import java.util.*;

public final class Maze
{
    private static int[][] maze;

    public static String getMaze(String fileName) throws IOException {
        FileReader reader = new FileReader(Path.of("src", fileName).toAbsolutePath().toFile());
        //noinspection ResultOfMethodCallIgnored
        Scanner scan = new Scanner(reader);
        StringBuilder maze = new StringBuilder();
        while(scan.hasNextLine())
            maze.append(scan.nextLine()).append(" ");
        return maze.toString();
    }

    @SuppressWarnings("unused")
    public static String generateMaze()
    {
        int dimension = (int)(Math.random() * 11 + 15);
        maze = new int[dimension][dimension];
        Random rng = new Random();

        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[0].length; j++)
                maze[i][j] = 1;

        maze[0][0] = 0;
        int[] origin = new int[]{0, 0};
        Stack<int[]> mazeCells = new Stack<>();
        HashSet<int[]> leaves = new HashSet<>();
        boolean isBackTracking = false;
        mazeCells.push(origin);
        while (mazeCells.peek() != origin || getValidMoves(origin) != 0) {
            int moves = getValidMoves(mazeCells.peek());
            if (moves == 0)
            {
                if (!isBackTracking) leaves.add(mazeCells.peek());
                mazeCells.pop();
                isBackTracking = true;
            }
            else
            {
                isBackTracking = false;
                int direction = rng.nextInt(4);
                if ((moves & (int)Math.pow(2, (3-direction))) == (int)Math.pow(2, (3-direction)))
                {
                    int[] cursor = mazeCells.peek();
                    switch (direction)
                    {
                        case 0:
                            maze[cursor[0] + 1][cursor[1]] = 0;
                            maze[cursor[0] + 2][cursor[1]] = 0;
                            mazeCells.push(new int[]{cursor[0] + 2, cursor[1]});
                            break;
                        case 1:
                            maze[cursor[0]][cursor[1] - 1] = 0;
                            maze[cursor[0]][cursor[1] - 2] = 0;
                            mazeCells.push(new int[]{cursor[0], cursor[1] - 2});
                            break;
                        case 2:
                            maze[cursor[0]][cursor[1] + 1] = 0;
                            maze[cursor[0]][cursor[1] + 2] = 0;
                            mazeCells.push(new int[]{cursor[0], cursor[1] + 2});
                            break;
                        case 3:
                            maze[cursor[0] - 1][cursor[1]] = 0;
                            maze[cursor[0] - 2][cursor[1]] = 0;
                            mazeCells.push(new int[]{cursor[0] - 2, cursor[1]});
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        leaves.remove(origin);
        int[][] leafArray = new int[leaves.size()][2];
        leafArray = leaves.toArray(leafArray);
        int[] endpoint = leafArray[rng.nextInt(leafArray.length)];
        maze[endpoint[0]][endpoint[1]] = 2;

        StringBuilder mazeString = new StringBuilder(dimension + " " + dimension + "\n");
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++)
            {
                mazeString.append(maze[i][j]).append(" ");
            }
            mazeString.append("\n");
        }

        mazeString.append("%");

        return mazeString.toString();
    }

    private static int getValidMoves(int[] cursor)
    {
        int moves = 0;
        if (cursor[0] + 2 < maze.length && maze[cursor[0] + 2][cursor[1]] == 1) moves |= 8;
        if (cursor[1] - 2 >= 0 && maze[cursor[0]][cursor[1] - 2] == 1) moves |= 4;
        if (cursor[1] + 2 < maze[0].length && maze[cursor[0]][cursor[1] + 2] == 1) moves |= 2;
        if (cursor[0] - 2 >= 0 && maze[cursor[0] - 2][cursor[1]] == 1) moves |= 1;
        return moves;
    }
}