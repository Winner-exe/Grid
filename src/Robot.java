import javax.swing.*;

/**
 * Represents a robot solving a maze. This robot may either be used as is with keyboard controls or extended to have AI controls.
 */
public class Robot extends GriddedSprite
{
	protected Cell pos;
	private final InputMap inputMap;
	private final ActionMap actionMap;
	private int[] keybinds;
	protected final Cell[][] grid;

	/**
	 * Constructs a robot.
	 *
	 * @param fileName the name of the image file to be used as a sprite sheet
	 * @param rows the number of rows in the sprite sheet
	 * @param columns the number of columns in the sprite sheet
	 * @param startPos the <code>Cell</code> initially containing this robot
	 * @param keybinds the array of key codes to be assigned to the four movement directions
	 * @param grid the 2D array representing the grid containing this robot
	 */
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

	/**
	 * Moves the robot in a specified manner.
	 */
	public void move()
	{
		//Blank implementation to be overridden
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