import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.JPanel;

/**
 * The base class representing a grid.
 * 
 * @author Winston Lee
 */
public class Grid extends JPanel
{
	private static final long serialVersionUID = 2892865424401791072L;
	private final Cell[][] grid;

	/**
	 * Constructs a panel with a grid.
	 */
	public Grid(int rows, int columns)
	{
		this.setLayout(null);
		this.setBackground(Color.BLACK);

		int CELL_WIDTH = 50;
		int CELL_HEIGHT = 50;
		
		grid = new Cell[rows][columns];

		Random rng = new Random();
		
		for (int i = 1; i <= grid.length; i++)
		{
			for (int j = 1; j <= grid[0].length; j++)
			{
				grid[i-1][j-1] = new Cell(CELL_WIDTH * j, CELL_HEIGHT * i, CELL_WIDTH, CELL_HEIGHT);

				GriddedSprite sprite = new GriddedSprite("background.png", 1, 1);
				if (rng.nextBoolean())
					sprite = new GriddedSprite("path.png", 1, 1);
				sprite.setLocation(grid[i-1][j-1].getX(), grid[i-1][j-1].getY());
				sprite.setScale((double)(grid[i-1][j-1].getWidth()) / sprite.getFrameWidth(),
						(double)(grid[i-1][j-1].getHeight()) / sprite.getFrameHeight());

				grid[i-1][j-1].setSprite(sprite);
				this.add(grid[i-1][j-1]);
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
				grid[i][j].loadSprite((Graphics2D) g, this);
	}
}
