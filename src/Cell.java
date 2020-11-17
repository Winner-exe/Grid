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
	private int tag;
	private ArrayList<GriddedSprite> sprites;

	/**
	 * Constructs a new Cell whose upper-left corner is specified as (x,y) and whose width and height are specified by the arguments of the same name.
	 *
	 * @param x the specified X coordinate
	 * @param y the specified Y coordinate
	 * @param width the width of the Cell
	 * @param height the height of the Cell
	 */
	public Cell(int x, int y, int width, int height, int tag)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.hasSprite = false;
		setTag(tag);
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

	/**
	 * Add a sprite for this cell to draw.
	 *
	 * @param sprite the sprite to draw
	 */
	public void addSprite(GriddedSprite sprite)
	{
		sprites.add(sprite);
	}

	public int getTag()
	{
		return tag;
	}

	public void setTag(int tag)
	{
		this.tag = tag % 2;

		if (tag == 0)
			addSprite(new GriddedSprite("background.png", 1, 1));
		else
			addSprite(new GriddedSprite("path.png", 1, 1));
	}

	/**
	 * Draws the sprite in this cell provided that a sprite has been previously set.
	 *
	 * @param g the <code>Graphics</code> object to protect
	 * @param obs object to be notified as more of the image is converted
	 */
	public void loadSprites(Graphics2D g, ImageObserver obs)
	{
		sprites.forEach(GriddedSprite s -> s.draw(g, obs));
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
