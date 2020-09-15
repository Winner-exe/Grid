import java.awt.*;

import javax.swing.JPanel;

/*
 * The base class representing a 10 x 10 grid.
 * 
 * @author Winston Lee
 */
public class Grid extends JPanel
{
	private static final long serialVersionUID = 2892865424401791072L;
	private final int CELL_WIDTH, CELL_HEIGHT;
	private final Cell[][] grid;
	
	/*
	 * Constructs a panel with a 10 x 10 grid.
	 */
	public Grid()
	{
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		
		CELL_WIDTH = 100;
		CELL_HEIGHT = 100;
		
		grid = new Cell[10][10];
		
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

	/*
	 * Paints the interior of this Cell.
	 * 
	 * @param g the the <code>Graphics</code> object to protect
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//To be implemented.
	}
}
