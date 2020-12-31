import javax.swing.*;

public class Robot extends GriddedSprite
{
	protected Cell pos;
	private final InputMap inputMap;
	private final ActionMap actionMap;
	private int[] keybinds;
	protected final Cell[][] grid;

	public Robot(String fileName, int rows, int columns, Cell startPos, int[] keybinds, Cell[][] grid)
	{
		super(fileName, rows, columns);

		if (startPos.getTag() == 1)
			throw new IllegalArgumentException();

		this.pos = startPos;
		setDirection(Grid.Direction.DOWN);
		startPos.addSprite(this);

		inputMap = new InputMap();
		actionMap = new ActionMap();

		try
		{
			if (keybinds.length != 4)
				throw new IllegalArgumentException();
			this.keybinds = keybinds;

			inputMap.put(KeyStroke.getKeyStroke(keybinds[0], 0), fileName + "down");
			actionMap.put(fileName + "down", new Grid.KeyPressed(keybinds[0]));
			inputMap.put(KeyStroke.getKeyStroke(keybinds[0], 0, true), fileName + "released down");
			actionMap.put(fileName + "released down", new Grid.KeyReleased(keybinds[0]));

			inputMap.put(KeyStroke.getKeyStroke(keybinds[1], 0), fileName + "left");
			actionMap.put(fileName + "left", new Grid.KeyPressed(keybinds[1]));
			inputMap.put(KeyStroke.getKeyStroke(keybinds[1], 0, true), fileName + "released left");
			actionMap.put(fileName + "released left", new Grid.KeyReleased(keybinds[1]));

			inputMap.put(KeyStroke.getKeyStroke(keybinds[2], 0), fileName + "right");
			actionMap.put(fileName + "right", new Grid.KeyPressed(keybinds[2]));
			inputMap.put(KeyStroke.getKeyStroke(keybinds[2], 0, true), fileName + "released right");
			actionMap.put(fileName + "released right", new Grid.KeyReleased(keybinds[2]));

			inputMap.put(KeyStroke.getKeyStroke(keybinds[3], 0), fileName + "up");
			actionMap.put(fileName + "up", new Grid.KeyPressed(keybinds[3]));
			inputMap.put(KeyStroke.getKeyStroke(keybinds[3], 0, true), fileName + "released up");
			actionMap.put(fileName + "released up", new Grid.KeyReleased(keybinds[3]));
		}
		catch (NullPointerException ignored) {}

		this.grid = grid;
	}

	@SuppressWarnings("unused")
	public int getRow()
	{
		return pos.getY() / pos.getHeight() - 1;
	}

	@SuppressWarnings("unused")
	public int getColumn()
	{
		return pos.getX() / pos.getWidth() - 1;
	}

	@SuppressWarnings("unused")
	public int[] getKeyBinds()
	{
		return keybinds;
	}

	public InputMap getInputMap()
	{
		return inputMap;
	}

	public ActionMap getActionMap()
	{
		return actionMap;
	}

	public void move()
	{

	}

	public void move(Cell destination)
	{
		if ((destination.getTag() == 0 || destination.getTag() >= 2) && pos.getTag() != 2)
		{
			pos.removeSprite(this);
			this.pos = destination;
			destination.addSprite(this);
		}
	}
}