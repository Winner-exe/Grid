import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * The base class representing a grid.
 *
 * @author Winston Lee
 */
public class Grid extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 2892865424401791072L;
	private final Cell[][] grid;
	private Cell randomStartPos, rightStartPos, memoryStartPos;
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private static final HashSet<Integer> keys = new HashSet<>();
	private final int CELL_WIDTH;
	private final int CELL_HEIGHT;
	private final HashSet<Cell> paths;
	private final HashSet<Robot> robots;

	/**
	 * A list of the four possible movement directions with their integer encodings.
	 */
	public enum Direction
	{
		DOWN(0), LEFT(1), RIGHT(2), UP(3);

		public final int dirCode;

		/**
		 * Returns a movement direction given its integer code.
		 *
		 * @param dirCode the integer code for the direction; must be an integer from 0 to 3
		 */
		Direction(int dirCode)
		{
			this.dirCode = dirCode;
		}
	}

	/**
	 * Constructs a panel with a grid.
	 *
	 * @param rows the number of rows in the grid
	 * @param columns the number of columns in the grid
	 */
	public Grid(int rows, int columns)
	{
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.requestFocus();

		CELL_WIDTH = 50;
		CELL_HEIGHT = 50;

		grid = new Cell[rows][columns];

		Random rng = new Random();

		paths = new HashSet<>();

		javax.swing.Timer t = new javax.swing.Timer(50, this);

		for (int i = 1; i <= grid.length; i++)
		{
			for (int j = 1; j <= grid[0].length; j++)
			{
				int isPath = rng.nextInt(2);
				grid[i-1][j-1] = new Cell(CELL_WIDTH * j, CELL_HEIGHT * i, CELL_WIDTH, CELL_HEIGHT,
											isPath);
				if (isPath == 0)
					paths.add(grid[i-1][j-1]);
				this.add(grid[i-1][j-1]);
			}
		}

		//Set Keybindings
		int[] redKeys = new int[]{KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP};
		int[] leafKeys = new int[]{KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W};
		int[] ashKeys = new int[]{KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_I};

		//Assign start spaces for Robots
		Iterator<Cell> pathAssigner = paths.iterator();
		robots = new HashSet<>();

		Robot red;
		if (pathAssigner.hasNext())
			red = new Robot("red-sprite.png", 4, 4, pathAssigner.next(), redKeys, grid);
		else
		{
			red = new Robot("red-sprite.png", 4, 4,
							grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), redKeys, grid);
		}
		robots.add(red);

		Robot leaf;
		if (pathAssigner.hasNext())
			leaf = new Robot("leaf-sprite.png", 4, 4, pathAssigner.next(), leafKeys, grid);
		else
		{
			leaf = new Robot("leaf-sprite.png", 4, 4,
					grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), leafKeys, grid);
		}
		robots.add(leaf);

		Robot ash;
		if (pathAssigner.hasNext())
			ash = new Robot("ash-sprite.png", 4, 4, pathAssigner.next(), ashKeys, grid);
		else
		{
			ash = new Robot("ash-sprite.png", 4, 4,
					grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), ashKeys, grid);
		}
		robots.add(ash);

		InputMap input = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap action = this.getActionMap();

		for (Robot r : robots)
		{
			for (KeyStroke key : r.getInputMap().keys())
			{
				input.put(key, r.getInputMap().get(key));
				action.put(r.getInputMap().get(key), r.getActionMap().get(r.getInputMap().get(key)));
			}
		}

		try
		{
			File music = Path.of("src","halland.wav").toAbsolutePath().toFile();
			if (rng.nextBoolean())
				music = Path.of("src","windfall.wav").toAbsolutePath().toFile();
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(music.toURI().toURL());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}

		t.start();
		this.repaint();
	}


	/**
	 * Constructs a panel with a grid based off of a given maze.
	 *
	 * @param mazeFile the file containing the maze encoding
	 * @throws IOException If a problem occurs when reading the maze file
	 */
	public Grid(String mazeFile) throws IOException {
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.requestFocus();

		CELL_WIDTH = 40;
		CELL_HEIGHT = 40;

		String maze = Maze.getMaze(mazeFile);
		Scanner scan = new Scanner(maze);

		paths = new HashSet<>();

		int rows = scan.nextInt();
		int columns = scan.nextInt();
		grid = new Cell[rows][columns];
		convertMaze(maze);

		Random rng = new Random();

		javax.swing.Timer t = new javax.swing.Timer(25, this);

		//Set Keybindings

		robots = new HashSet<>();
		RandomRobot red = new RandomRobot("red-sprite.png", 4, 4, randomStartPos, grid);
		RightRobot leaf = new RightRobot("leaf-sprite.png", 4, 4, rightStartPos, grid);
		MemoryRobot ash = new MemoryRobot("ash-sprite.png", 4, 4, memoryStartPos, grid);
		robots.add(red);
		robots.add(leaf);
		robots.add(ash);

		try
		{
			ArrayList<File> tracks = new ArrayList<>();
			tracks.add(Path.of("src","halland.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","windfall.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","summertime.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","route10.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","hoennelite4.wav").toAbsolutePath().toFile());
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(tracks.get(rng.nextInt(tracks.size())).toURI().toURL());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-10);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}

		t.start();
		this.repaint();
	}

	/**
	 * Constructs a panel with a grid based off of a randomly generated maze.
	 *
	 * @throws IOException If a problem occurs when writing the contents of the maze to a .txt file
	 */
	public Grid() throws IOException {
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.requestFocus();

		CELL_WIDTH = 40;
		CELL_HEIGHT = 40;

		String maze = Maze.generateMaze();
		Scanner scan = new Scanner(maze);

		paths = new HashSet<>();

		int rows = scan.nextInt();
		int columns = scan.nextInt();
		grid = new Cell[rows][columns];
		convertMaze(maze);

		Random rng = new Random();

		javax.swing.Timer t = new javax.swing.Timer(250, this);

		//Set Keybindings

		robots = new HashSet<>();
		RandomRobot red = new RandomRobot("red-sprite.png", 4, 4, randomStartPos, grid);
		RightRobot leaf = new RightRobot("leaf-sprite.png", 4, 4, rightStartPos, grid);
		MemoryRobot ash = new MemoryRobot("ash-sprite.png", 4, 4, memoryStartPos, grid);
		robots.add(red);
		robots.add(leaf);
		robots.add(ash);

		try
		{
			ArrayList<File> tracks = new ArrayList<>();
			tracks.add(Path.of("src","halland.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","windfall.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","summertime.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","route10.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","hoennelite4.wav").toAbsolutePath().toFile());
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(tracks.get(rng.nextInt(tracks.size())).toURI().toURL());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-10);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}

		t.start();
		this.repaint();
	}

	/**
	 * Decodes a maze into this grid.
	 *
	 * @param maze the String representing the maze
	 */
	private void convertMaze(String maze)
	{
		Scanner scan = new Scanner(maze);
		scan.nextInt();
		scan.nextInt();

		for (int i = 1; i <= grid.length; i++)
		{
			for (int j = 1; j <= grid[0].length; j++)
			{
				int tag = scan.nextInt();
				grid[i-1][j-1] = new Cell(CELL_WIDTH * j, CELL_HEIGHT * i, CELL_WIDTH, CELL_HEIGHT,
						tag);
				grid[i-1][j-1].setCoordinates(new int[]{i-1, j-1});
				switch (tag)
				{
					case 0:
						paths.add(grid[i-1][j-1]);
						break;
					case 3:
						randomStartPos = grid[i-1][j-1];
						break;
					case 4:
						rightStartPos = grid[i-1][j-1];
						break;
					case 5:
						memoryStartPos = grid[i-1][j-1];
						break;
					default:
						break;
				}

				this.add(grid[i-1][j-1]);
			}
		}
	}

	/**
	 * Invoked when an action occurs.
	 *
	 * @param e the event to be processed
	 */
	public void actionPerformed(ActionEvent e)
	{
		for (Robot r : robots)
		{
			r.move();
		}

		this.repaint();
	}

	/**
	 * Paints the grid.
	 *
	 * @param g the <code>Graphics</code> object to protect
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[0].length; j++)
				grid[i][j].loadSprites((Graphics2D) g, this);
	}

	/**
	 * Represents the action of pressing a key.
	 */
	public static class KeyPressed extends AbstractAction
	{
		private final int keyCode;

		/**
		 * Creates the event of pressing a given key.
		 *
		 * @param keyCode the key's integer code as specified by the <code>KeyEvent</code> class.
		 */
		public KeyPressed(int keyCode)
		{
			super();
			this.keyCode = keyCode;
		}

		/**
		 * Invoked when an action occurs.
		 *
		 * @param e the event to be processed
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			keys.add(keyCode);
		}
	}

	/**
	 * Represents the action of releasing a key.
	 */
	public static class KeyReleased extends AbstractAction
	{
		private final int keyCode;

		/**
		 * Creates the event of releasing a given key.
		 *
		 * @param keyCode the key's integer code as specified by the <code>KeyEvent</code> class.
		 */
		public KeyReleased(int keyCode)
		{
			super();
			this.keyCode = keyCode;
		}

		/**
		 * Invoked when an action occurs.
		 *
		 * @param e the event to be processed
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			keys.remove(keyCode);
		}
	}
}
