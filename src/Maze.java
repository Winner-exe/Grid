import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * The <code>Maze</code> class contains methods to feed an encoded maze to a <code>Grid</code> object.
 *
 * This is accomplished by randomly generating a maze encoded as a <code>String</code> or by reading a maze from a file.
 */
public final class Maze
{
    private static int[][] maze;

    /**
     * Reads a file with one or many mazes into a <code>String</code>
     *
     * @param fileName the name of the file containing the maze(s)
     * @return the string encoding of one of the given mazes
     * @throws IOException If a problem occurs when reading the maze file
     */
    public static String getMaze(String fileName) throws IOException {
        FileReader reader = new FileReader(Path.of("src", fileName).toAbsolutePath().toFile());
        //noinspection ResultOfMethodCallIgnored
        Scanner scan = new Scanner(reader).useDelimiter("%");
        ArrayList<String> mazes = new ArrayList<>();
        Random rng = new Random();
        while(scan.hasNext())
            mazes.add(scan.next());
        Scanner scanMaze = new Scanner(mazes.get(rng.nextInt(mazes.size())));
        StringBuilder maze = new StringBuilder();
        while(scanMaze.hasNextLine())
            maze.append(scanMaze.nextLine()).append(" ");
        return maze.toString();
    }

    /**
     * Randomly generates a string encoding of a valid maze and writes the encoding to a .txt file
     *
     * @return the string encoding of the random maze
     * @throws IOException If a problem occurs when writing to the maze file
     */
    @SuppressWarnings("unused")
    public static String generateMaze() throws IOException {
        //Establish maze dimensions
        int dimensionX = (int)(Math.random() * 11 + 15);
        int dimensionY = (int)(Math.random() * 11 + 15);
        maze = new int[dimensionX][dimensionY];
        Random rng = new Random();

        //Fill the grid with walls
        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[0].length; j++)
                maze[i][j] = 1;

        //Use a backtracking algorithm to create the paths of the maze
        maze[0][0] = 0;
        int[] origin = new int[]{0, 0};
        Stack<int[]> mazeCells = new Stack<>();
        ArrayList<int[]> leaves = new ArrayList<>();
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

        //Assign endpoint of the maze
        leaves.remove(origin);
        int[] endpoint = leaves.get(rng.nextInt(leaves.size()));
        leaves.remove(endpoint);
        maze[endpoint[0]][endpoint[1]] = 2;

        leaves.add(origin);

        //Assign start pos for RandomRobot
        int[] randomStartPos = leaves.get(rng.nextInt(leaves.size()));
        leaves.remove(randomStartPos);
        maze[randomStartPos[0]][randomStartPos[1]] = 3;

        //Assign start pos for RightHandRobot
        int[] rightStartPos = leaves.get(rng.nextInt(leaves.size()));
        leaves.remove(rightStartPos);
        maze[rightStartPos[0]][rightStartPos[1]] = 4;

        //Assign start pos for MemoryRobot
        int[] memoryStartPos = leaves.get(rng.nextInt(leaves.size()));
        leaves.remove(memoryStartPos);
        maze[memoryStartPos[0]][memoryStartPos[1]] = 5;

        //Encode the maze into a String
        StringBuilder mazeString = new StringBuilder(dimensionX + " " + dimensionY + "\n");
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++)
            {
                mazeString.append(maze[i][j]).append(" ");
            }
            mazeString.append("\n");
        }

        mazeString.append("%");

        //Write the string encoding to a maze file
        FileWriter w = new FileWriter(Path.of("src", "Maze.txt").toAbsolutePath().toFile());
        w.append(mazeString.toString());
        w.close();

        return mazeString.toString();
    }

    /**
     * Finds the possible directions to move in when creating a path
     *
     * @param cursor the current position
     * @return a 4-bit integer with a 1 in every position corresponding to the integer encoding for the valid direction (encoding specified in <code>Grid.Direction</code>)
     */
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
