import java.awt.*;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

public class Cell extends JComponent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3059067480908071499L;
	private int x, y, width, height;

	public Cell(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setLayout(null);
		setBounds(x, y, width, height);
		setBackground(new Color(0, 0, 0, 0));
		setBorder(new LineBorder(Color.WHITE));
	}

	//Returns true iff the given point is in this Box's bounding rectangle
	public boolean contains(int x, int y)
	{
		return getBounds().contains(x, y);
	}

	//Returns the x-coordinate.
	public int getX()
	{
		return x;
	}

	//Returns the y-coordinate.
	public int getY()
	{
		return y;
	}

	//Returns the width.
	public int getWidth()
	{
		return width;
	}

	//Returns the height.
	public int getHeight()
	{
		return height;
	}
	
	//Draws the box interior.
	public void draw(Graphics g)
	{
		//To be implemented.
	}
	
}
