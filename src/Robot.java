public class Robot extends GriddedSprite
{
	private Cell pos;

	public Robot(String fileName, int rows, int columns, Cell startPos)
	{
		super(fileName, rows, columns);

		if (startPos.getTag() == 1)
			throw new IllegalArgumentException();

		this.pos = startPos;
		startPos.addSprite(this);
	}

	public Robot(String fileName, int rows, int columns, int tx, int ty,
                         double scaleX, double scaleY, double theta, Cell startPos)
	{
		super(fileName, rows, columns, tx, ty, scaleX, scaleY, theta);

		if (startPos.getTag() == 1)
			throw new IllegalArgumentException();

		this.pos = startPos;
		startPos.addSprite(this);
	}

	public boolean move(Cell destination)
	{
		if (destination.getTag() == 0)
		{
			pos.removeSprite(this);
			this.pos = destination;
			destination.addSprite(this);
		}
		return destination.getTag() == 0;
	}
}