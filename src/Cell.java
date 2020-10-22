import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

/**
 * The base class representing a single cell in a grid.
 * 
 * @author Winston Lee
 */
public class Cell extends JComponent
{
	private static final long serialVersionUID = -3059067480908071499L;
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private GriddedSprite sprite;
	private boolean hasSprite;

	/**
	 * Constructs a new Cell whose upper-left corner is specified as (x,y) and whose width and height are specified by the arguments of the same name.
	 * 
	 * @param x the specified X coordinate
	 * @param y the specified Y coordinate
	 * @param width the width of the Cell
	 * @param height the height of the Cell
	 */
	public Cell(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.hasSprite = false;
		setLayout(null);
		setBounds(x, y, width, height);
		setBackground(new Color(0, 0, 0, 0));
		setBorder(new LineBorder(Color.WHITE));
	}

	/**
	 * Checks whether this Cell "contains" the specified point, where x and y are defined to be relative to the coordinate system of this component.
	 * 
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return true if this component logically contains x,y
	 */
	@Override
	public boolean contains(int x, int y)
	{
		return getBounds().contains(x, y);
	}

	/**
	 * Gets the x-coordinate of this Cell's upper left hand corner.
	 * 
	 * @return the x-coordinate of this Cell's upper left hand corner
	 */
	@Override
	public int getX()
	{
		return x;
	}

	/**
	 * Gets the y-coordinate of this Cell's upper left hand corner.
	 * 
	 * @return the y-coordinate of this Cell's upper left hand corner
	 */
	@Override
	public int getY()
	{
		return y;
	}

	/**
	 * Gets the width of this cell.
	 * 
	 * @return the width of this cell
	 */
	@Override
	public int getWidth()
	{
		return width;
	}

	/**
	 * Gets the height of this cell.
	 * 
	 * @return the height of this cell
	 */
	@Override
	public int getHeight()
	{
		return height;
	}

	public boolean hasSprite()
	{
		return hasSprite;
	}

	public void setSprite(GriddedSprite sprite)
	{
		this.hasSprite = true;
		this.sprite = sprite;
	}

	public void loadSprite(Graphics2D g, ImageObserver obs)
	{
		if (hasSprite())
			sprite.draw(g, obs);
	}
	
	/**
	 * Paints the interior of this Cell.
	 * 
	 * @param g the <code>Graphics</code> object to protect
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
}
