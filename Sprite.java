import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class Sprite implements Iterable<BufferedImage>
{
	private BufferedImage img;
	private final int frameWidth, frameHeight, rows, columns;
	private Iterator<BufferedImage> iter;

	public Sprite(int frameWidth, int frameHeight, int rows, int columns)
	{
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.rows = rows;
		this.columns = columns;

		try
		{
			img = ImageIO.read(new File("sprite.png"));
		}
		catch(IOException e) {}

		iter = iterator();
	}

	@Override
	public Iterator<BufferedImage> iterator()
	{
		return new SpriteIterator();
	}

	public void draw(Graphics2D g)
	{

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