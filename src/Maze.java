import java.io.*;
import java.nio.file.Path;
import java.util.*;

public final class Maze
{
    private static int[][] maze;

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

    @SuppressWarnings("unused")
    public static String generateMaze() throws IOException {
        int dimensionX = (int)(Math.random() * 11 + 15);
        int dimensionY = (int)(Math.random() * 11 + 15);
        maze = new int[dimensionX][dimensionY];
        Random rng = new Random();

        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[0].length; j++)
                maze[i][j] = 1;

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

        //Assign endpoint
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

        FileWriter w = new FileWriter(Path.of("src", "Maze.txt").toAbsolutePath().toFile());
        w.append(mazeString.toString());
        w.close();

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
