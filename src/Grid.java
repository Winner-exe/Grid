import java.awt.*;

import javax.swing.JPanel;

/**
 * The base class representing a grid.
 * 
 * @author Winston Lee
 */
public class Grid extends JPanel
{
	private static final long serialVersionUID = 2892865424401791072L;
	private final int CELL_WIDTH, CELL_HEIGHT;
	private final Cell[][] grid;
	
	/**
	 * Constructs a panel with a grid.
	 */
	public Grid(int rows, int columns)
	{
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		
		CELL_WIDTH = 100;
		CELL_HEIGHT = 100;
		
		grid = new Cell[rows][columns];
		
		for (int i = 1; i <= grid.length; i++)
		{
			for (int j = 1; j <= grid[0].length; j++)
			{
				grid[i-1][j-1] = new Cell(CELL_WIDTH * j, CELL_HEIGHT * i, CELL_WIDTH, CELL_HEIGHT);
				this.add(grid[i-1][j-1]);
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
	}
}
