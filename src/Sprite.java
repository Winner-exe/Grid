import java.util.*;
import java.io.*;
import java.awt.Graphics2D;
import java.awt.image.*;
import javax.imageio.*;

public class Sprite implements Iterable<BufferedImage>
{
	private BufferedImage img;
	private final int frameWidth, frameHeight, rows, columns;
	private final Iterator<BufferedImage> iter;

	public Sprite(String fileName, int rows, int columns)
	{
		try
		{
			img = ImageIO.read(new File(fileName));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		this.frameWidth = img.getWidth() / columns;
		this.frameHeight = img.getHeight() / rows;
		this.rows = rows;
		this.columns = columns;



		iter = iterator();
	}

	@Override
	public Iterator<BufferedImage> iterator()
	{
		return new SpriteIterator();
	}

	public void draw(Graphics2D g, int x, int y, ImageObserver obs)
	{
		if (iter.hasNext())
			g.drawImage(iter.next(), x, y, obs);
	}

	protected class SpriteIterator implements Iterator<BufferedImage>
	{
		int cursorRow = 0, cursorColumn = 0;

		@Override
		public boolean hasNext()
		{
			if (cursorRow < rows && cursorColumn < columns)
				return true;
			return false;
		}

		@Override
		public BufferedImage next()
		{
			BufferedImage frame = img.getSubimage(cursorColumn * frameWidth, cursorRow * frameHeight,
												  frameWidth, frameHeight);
			cursorColumn = (cursorColumn + 1) % columns;
			cursorRow = (cursorRow + 1) % rows;
			return frame;
		}
	}
}