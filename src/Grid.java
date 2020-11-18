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
	private final HashSet<Integer> keys;
	private final HashSet<Cell> paths;
	private final javax.swing.Timer t;
	private final Robot red;

	/**
	 * Constructs a panel with a grid.
	 */
	public Grid(int rows, int columns)
	{
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new KAdapter());

		int CELL_WIDTH = 50;
		int CELL_HEIGHT = 50;

		grid = new Cell[rows][columns];

		Random rng = new Random();

		keys = new HashSet<Integer>();

		paths = new HashSet<Cell>();

		t = new javax.swing.Timer(50, this);

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

		Iterator<Cell> pathAssigner = paths.iterator();

		if (pathAssigner.hasNext())
			red = new Robot("red-sprite.png", 1, 1, pathAssigner.next());
		else
		{
			red = new Robot("red-sprite.png", 1, 1,
							grid[rng.nextInt(grid.length)][rng.nextInt(grid[0].length)].setTag(0));
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

	public void actionPerformed(ActionEvent e)
	{

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

	public class KAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e) {
			keys.add(e.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			keys.remove(e.getKeyCode());
		}
	}
}
