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
	private static final HashSet<Integer> keys = new HashSet<>();
	private final int CELL_WIDTH;
	private final int CELL_HEIGHT;
	private final HashSet<Cell> paths;
	private final HashSet<Robot> robots;

	public enum Direction
	{
		DOWN(0), LEFT(1), RIGHT(2), UP(3);

		public final int dirCode;

		Direction(int dirCode)
		{
			this.dirCode = dirCode;
		}
	}

	/**
	 * Constructs a panel with a grid.
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
			red = new Robot("red-sprite.png", 4, 4, pathAssigner.next(), redKeys);
		else
		{
			red = new Robot("red-sprite.png", 4, 4,
							grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), redKeys);
		}
		robots.add(red);

		Robot leaf;
		if (pathAssigner.hasNext())
			leaf = new Robot("leaf-sprite.png", 4, 4, pathAssigner.next(), leafKeys);
		else
		{
			leaf = new Robot("leaf-sprite.png", 4, 4,
					grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), leafKeys);
		}
		robots.add(leaf);

		Robot ash;
		if (pathAssigner.hasNext())
			ash = new Robot("ash-sprite.png", 4, 4, pathAssigner.next(), ashKeys);
		else
		{
			ash = new Robot("ash-sprite.png", 4, 4,
					grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), ashKeys);
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

	public Grid(String mazeFile) throws IOException {
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.requestFocus();

		CELL_WIDTH = 50;
		CELL_HEIGHT = 50;

		String maze = Maze.getMaze(mazeFile);
		Scanner scan = new Scanner(maze);

		paths = new HashSet<>();

		int rows = scan.nextInt();
		int columns = scan.nextInt();
		grid = new Cell[rows][columns];
		convertMaze(maze);

		Random rng = new Random();

		javax.swing.Timer t = new javax.swing.Timer(50, this);

		//Set Keybindings
		int[] redKeys = new int[]{KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP};
		int[] leafKeys = new int[]{KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W};
		int[] ashKeys = new int[]{KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_I};

		//Assign start spaces for Robots
		Iterator<Cell> pathAssigner = paths.iterator();
		robots = new HashSet<>();

		Robot red;
		if (pathAssigner.hasNext())
			red = new Robot("red-sprite.png", 4, 4, pathAssigner.next(), redKeys);
		else
		{
			red = new Robot("red-sprite.png", 4, 4,
					grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), redKeys);
		}
		robots.add(red);

		Robot leaf;
		if (pathAssigner.hasNext())
			leaf = new Robot("leaf-sprite.png", 4, 4, pathAssigner.next(), leafKeys);
		else
		{
			leaf = new Robot("leaf-sprite.png", 4, 4,
					grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), leafKeys);
		}
		robots.add(leaf);

		Robot ash;
		if (pathAssigner.hasNext())
			ash = new Robot("ash-sprite.png", 4, 4, pathAssigner.next(), ashKeys);
		else
		{
			ash = new Robot("ash-sprite.png", 4, 4,
					grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), ashKeys);
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

	public Grid()
	{
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.requestFocus();

		CELL_WIDTH = 30;
		CELL_HEIGHT = 30;

		String maze = Maze.generateMaze();
		Scanner scan = new Scanner(maze);

		paths = new HashSet<>();

		int rows = scan.nextInt();
		int columns = scan.nextInt();
		grid = new Cell[rows][columns];
		convertMaze(maze);

		Random rng = new Random();

		javax.swing.Timer t = new javax.swing.Timer(50, this);

		//Set Keybindings
		int[] redKeys = new int[]{KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP};
		int[] leafKeys = new int[]{KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W};
		int[] ashKeys = new int[]{KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_I};

		robots = new HashSet<>();
		Robot red = new Robot("red-sprite.png", 4, 4, grid[0][0], redKeys);
		Robot leaf = new Robot("leaf-sprite.png", 4, 4, grid[0][0], leafKeys);
		Robot ash = new Robot("ash-sprite.png", 4, 4, grid[0][0], ashKeys);
		robots.add(red);
		robots.add(leaf);
		robots.add(ash);
		/*
		//Assign start spaces for Robots
		Iterator<Cell> pathAssigner = paths.iterator();

		Robot red;
		if (pathAssigner.hasNext())
			red = new Robot("red-sprite.png", 4, 4, pathAssigner.next(), redKeys);
		else
		{
			red = new Robot("red-sprite.png", 4, 4,
					grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), redKeys);
		}
		robots.add(red);

		Robot leaf;
		if (pathAssigner.hasNext())
			leaf = new Robot("leaf-sprite.png", 4, 4, pathAssigner.next(), leafKeys);
		else
		{
			leaf = new Robot("leaf-sprite.png", 4, 4,
					grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), leafKeys);
		}
		robots.add(leaf);

		Robot ash;
		if (pathAssigner.hasNext())
			ash = new Robot("ash-sprite.png", 4, 4, pathAssigner.next(), ashKeys);
		else
		{
			ash = new Robot("ash-sprite.png", 4, 4,
					grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0), ashKeys);
		}
		robots.add(ash);

		 */

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
			ArrayList<File> tracks = new ArrayList<>();
			tracks.add(Path.of("src","halland.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","windfall.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","summertime.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","route10.wav").toAbsolutePath().toFile());
			tracks.add(Path.of("src","hoennelite4.wav").toAbsolutePath().toFile());
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(tracks.get(rng.nextInt(tracks.size())).toURI().toURL());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}

		t.start();
		this.repaint();
	}

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
				if (tag == 0)
					paths.add(grid[i-1][j-1]);
				this.add(grid[i-1][j-1]);
			}
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		for (Robot r : robots)
		{
			if (keys.contains(r.getKeyBinds()[0])) {
				r.setDirection(Direction.DOWN);
				if (r.getRow() < grid.length - 1) {
					r.move(grid[r.getRow() + 1][r.getColumn()]);
					keys.remove(r.getKeyBinds()[0]);
				}
			}

			if (keys.contains(r.getKeyBinds()[1])) {
				r.setDirection(Direction.LEFT);
				if (r.getColumn() > 0)
				{
					r.move(grid[r.getRow()][r.getColumn() - 1]);
					keys.remove(r.getKeyBinds()[1]);
				}
			}

			if (keys.contains(r.getKeyBinds()[2])) {
				r.setDirection(Direction.RIGHT);
				if (r.getColumn() < grid[0].length - 1)
				{
					r.move(grid[r.getRow()][r.getColumn() + 1]);
					keys.remove(r.getKeyBinds()[2]);
				}
			}

			if (keys.contains(r.getKeyBinds()[3])) {
				r.setDirection(Direction.UP);
				if (r.getRow() > 0)
				{
					r.move(grid[r.getRow() - 1][r.getColumn()]);
					keys.remove(r.getKeyBinds()[3]);
				}
			}
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

	public static class KeyPressed extends AbstractAction
	{
		private final int keyCode;

		public KeyPressed(int keyCode)
		{
			super();
			this.keyCode = keyCode;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			keys.add(keyCode);
		}
	}

	public static class KeyReleased extends AbstractAction
	{
		private final int keyCode;

		public KeyReleased(int keyCode)
		{
			super();
			this.keyCode = keyCode;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			keys.remove(keyCode);
		}
	}
}
