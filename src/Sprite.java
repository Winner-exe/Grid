import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.awt.image.*;
import java.util.stream.IntStream;
import javax.imageio.*;

/**
 * The base class representing a sprite.
 *
 * @author Winston Lee
 */
public class Sprite implements Iterable<BufferedImage>
{
	protected BufferedImage img;
	protected final int frameWidth, frameHeight, rows, columns;
	protected final HashMap<Point, BufferedImage> frames;
	protected Grid.Direction direction;
	protected Iterator<BufferedImage> iter;
	protected PrimitiveIterator.OfInt columnIter;

	/**
	 * Initializes a sprite object given a sprite sheet file and the rows and columns of the sheet.
	 *
	 * @param fileName the name of the image file to be used as a sprite sheet
	 * @param rows the number of rows in the sprite sheet
	 * @param columns the number of columns in the sprite sheet
	 */
	public Sprite(String fileName, int rows, int columns)
	{
		try
		{
			File f = Path.of("src", fileName).toAbsolutePath().toFile();
			img = ImageIO.read(f);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		this.frameWidth = img.getWidth() / columns;
		this.frameHeight = img.getHeight() / rows;
		this.rows = rows;
		this.columns = columns;
		direction = null;

		frames = new HashMap<>();
		int rowCursor = 0;
		int columnCursor = 0;

		iter = iterator();
		while (iter.hasNext())
		{
			frames.put(new Point(rowCursor, columnCursor), iter.next());
			columnCursor = (columnCursor + 1) % columns;
			if (columnCursor == 0)
				rowCursor++;
		}
		iter = iterator();

		columnIter = IntStream.range(0, columns).iterator();
	}

	public int getFrameWidth()
	{
		return frameWidth;
	}

	public int getFrameHeight()
	{
		return frameHeight;
	}

	@SuppressWarnings("unused")
	public Grid.Direction getDirection()
	{
		return direction;
	}

	public void setDirection(Grid.Direction direction)
	{
		this.direction = direction;
	}

	/**
	 * Creates an iterator over the frames of the sprite sheet.
	 *
	 * @return an iterator over the frames of the sprite sheet
	 */
	@Override
	public @NotNull Iterator<BufferedImage> iterator()
	{
		return new SpriteIterator();
	}

	/**
	 * Draws a frame of this sprite.
	 *  @param g the <code>Graphics</code> Graphics object to protect
	 * @param x the x-coordinate where this frame should be drawn
	 * @param y the y-coordinate where this frame should be drawn
	 * @param obs object to be notified as more of the image is converted
	 */
	public void draw(Graphics2D g, int x, int y, ImageObserver obs)
	{
		if (direction != null)
		{
			for (int i = 0; i < columns; i++)
			{
				if (columnIter.hasNext())
					g.drawImage(frames.get(new Point(direction.dirCode, columnIter.next())), x, y, obs);
				else {
					columnIter = IntStream.range(0, columns).iterator();
					g.drawImage(frames.get(new Point(direction.dirCode, columnIter.next())), x, y, obs);
				}
			}
		}
		else
		{
			if (iter.hasNext())
				g.drawImage(iter.next(), x, y, obs);
			else {
				iter = iterator();
				g.drawImage(iter.next(), x, y, obs);
			}
		}
	}

	/**
	 * Iterator class over the frames of the sprite sheet.
	 */
	protected class SpriteIterator implements Iterator<BufferedImage>
	{
		int cursorRow = 0, cursorColumn = 0;

		/**
		 * Determines if there is a frame available to draw.
		 *
		 * @return true if there is a frame available to draw, false otherwise
		 */
		@Override
		public boolean hasNext()
		{
			return cursorRow < rows && cursorColumn < columns;
		}

		/**
		 * Returns the next frame of the sprite sheet.
		 *
		 * @return the next frame of the sprite sheet
		 */
		@Override
		public BufferedImage next()
		{
			BufferedImage frame = img.getSubimage(cursorColumn * frameWidth, cursorRow * frameHeight,
												  frameWidth, frameHeight);
			cursorColumn = (cursorColumn + 1) % columns;
			if (cursorColumn == 0)
				cursorRow++;
			return frame;
		}
	}
}