import javax.swing.*;
import java.awt.event.KeyEvent;

public class Robot extends GriddedSprite
{
	private Cell pos;
	private final InputMap inputMap;
	private final ActionMap actionMap;
	private int[] keybinds;

	public Robot(String fileName, int rows, int columns, Cell startPos, int[] keybinds)
	{
		super(fileName, rows, columns);

		if (startPos.getTag() == 1)
			throw new IllegalArgumentException();

		if (keybinds.length != 4)
			throw new IllegalArgumentException();

		this.pos = startPos;
		this.keybinds = keybinds;
		setDirection(Grid.Direction.DOWN);
		startPos.addSprite(this);

		inputMap = new InputMap();
		actionMap = new ActionMap();

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

	@SuppressWarnings("unused")
	public Robot(String fileName, int rows, int columns, int tx, int ty,
				 double scaleX, double scaleY, double theta, Cell startPos, int[] keybinds)
	{
		super(fileName, rows, columns, tx, ty, scaleX, scaleY, theta);

		if (startPos.getTag() == 1)
			throw new IllegalArgumentException();

		if (keybinds.length != 4)
			throw new IllegalArgumentException();

		this.pos = startPos;
		setDirection(Grid.Direction.DOWN);
		startPos.addSprite(this);

		inputMap = new InputMap();
		actionMap = new ActionMap();

		inputMap.put(KeyStroke.getKeyStroke(keybinds[0], 0), fileName + " down");
		actionMap.put(fileName + "down", new Grid.KeyPressed(KeyEvent.VK_DOWN));
		inputMap.put(KeyStroke.getKeyStroke(keybinds[0], 0, true), fileName + "released down");
		actionMap.put(fileName + "released down", new Grid.KeyReleased(KeyEvent.VK_DOWN));

		inputMap.put(KeyStroke.getKeyStroke(keybinds[1], 0), fileName + "left");
		actionMap.put(fileName + "left", new Grid.KeyPressed(KeyEvent.VK_LEFT));
		inputMap.put(KeyStroke.getKeyStroke(keybinds[1], 0, true), fileName + "released left");
		actionMap.put(fileName + "released left", new Grid.KeyReleased(KeyEvent.VK_LEFT));

		inputMap.put(KeyStroke.getKeyStroke(keybinds[2], 0), fileName + "right");
		actionMap.put(fileName + "right", new Grid.KeyPressed(KeyEvent.VK_RIGHT));
		inputMap.put(KeyStroke.getKeyStroke(keybinds[2], 0, true), fileName + "released right");
		actionMap.put(fileName + "released right", new Grid.KeyReleased(KeyEvent.VK_RIGHT));

		inputMap.put(KeyStroke.getKeyStroke(keybinds[3], 0), fileName + "up");
		actionMap.put(fileName + "up", new Grid.KeyPressed(KeyEvent.VK_UP));
		inputMap.put(KeyStroke.getKeyStroke(keybinds[3], 0, true), fileName + "released up");
		actionMap.put(fileName + "released up", new Grid.KeyReleased(KeyEvent.VK_UP));
	}

	public int getRow()
	{
		return pos.getY() / pos.getHeight() - 1;
	}

	public int getColumn()
	{
		return pos.getX() / pos.getWidth() - 1;
	}

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

	public void move(Cell destination)
	{
		if (destination.getTag() % 2 == 0)
		{
			pos.removeSprite(this);
			this.pos = destination;
			destination.addSprite(this);
		}
	}
}