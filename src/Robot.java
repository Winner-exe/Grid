public class Robot extends GriddedSprite
{
	Cell pos;

	public Robot(String fileName, int rows, int columns, Cell startPos)
	{
		super(fileName, rows, columns);

		this.pos = startPos;
	}

	public Robot(String fileName, int rows, int columns, int tx, int ty,
                         double scaleX, double scaleY, double theta, Cell startPos)
	{
		super(fileName, rows, columns, tx, ty, scaleX, scaleY, theta);

		this.pos = startPos;
	}

	public boolean move(Cell destination)
	{
		if (destination.getTag() == 0)
			this.pos = destination;
		return destination.getTag() == 0;
	}
}