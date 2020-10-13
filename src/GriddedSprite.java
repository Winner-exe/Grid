import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

/**
 * This class gives an interface for transforming sprites through the AffineTransform class.
 *
 * @author Winston Lee
 */
public class GriddedSprite extends Sprite
{
    private int x, y;
    private double scale, theta;
    private AffineTransform transform;

    /**
     * Initializes a sprite given a sprite sheet file and the number of rows and columns. No transformation is applied.
     *
	 * @param fileName the name of the image file to be used as a sprite sheet
	 * @param rows the number of rows in the sprite sheet
	 * @param columns the number of columns in the sprite sheet
     */
    public GriddedSprite(String fileName, int rows, int columns)
    {
        super(fileName, rows, columns);

        setLocation(0, 0);
        setScale(1);
        setTheta(0);

        updateTransform();
    }

    /**
     * Initializes a sprite given a sprite sheet file, the number of rows and columns, and a transformation.
     *
	 * @param fileName the name of the image file to be used as a sprite sheet
	 * @param rows the number of rows in the sprite sheet
	 * @param columns the number of columns in the sprite sheet
	 * @param tx the initial x-coordinate of this sprite
	 * @param ty the initial y-coordinate of this sprite
	 * @param scale the dilation factor for this sprite
	 * @param theta the rotation angle for this sprite
     */
    public GriddedSprite(String fileName, int rows, int columns, int tx, int ty, double scale, double theta)
    {
        super(fileName, rows, columns);
        setLocation(tx, ty);
        setScale(scale);
        setTheta(theta);

        updateTransform();
    }

	/**
	 * Updates the transformation on this sprite. This method is called every time a transformation parameter is changed.
	 */
    private void updateTransform()
    {
        if (theta % 90 == 0)
            this.transform = AffineTransform.getQuadrantRotateInstance((int) (theta / 90), x + frameWidth / 2,
                                                                y + frameHeight / 2);
        else
            this.transform = AffineTransform.getRotateInstance(theta, x + frameWidth / 2,
                                                        y + frameHeight / 2);
        transform.translate(x, y);
        transform.scale(scale, scale);
    }

	/**
	 * Sets the absolute location of this sprite.
	 *
	 * @param tx the new x-coordinate for this sprite
	 * @param ty the new y-coordinate for this sprite
	 */
    public void setLocation(int tx, int ty)
    {
        this.x = tx;
        this.y = ty;

        updateTransform();
    }

	/**
	 * Sets the scale factor (based off of the original image size) for this sprite.
	 *
	 * @param scale the new dilation factor for this sprite
	 */
    public void setScale(double scale)
    {
        this.scale = scale;

        updateTransform();
    }

	/**
	 * Sets the absolute rotation angle for this sprite.
	 *
	 * @param theta the new rotation angle for this sprite.
	 */
    public void setTheta(double theta)
    {
        this.theta = theta;

        updateTransform();
    }

	/**
	 * Applies a translation to this sprite in its current state.
	 *
	 * @param tx the distance by which coordinates are translated in the x-axis direction
	 * @param ty the distance by which coordinates are translated in the y-axis direction
	 */
    public void translate(int tx, int ty)
    {
        this.x += tx;
        this.y += ty;

        updateTransform();
    }

	/**
	 * Applies a dilation to this sprite in its current state.
	 *
	 * @param scale the dilation factor
	 */
    public void scale(double scale)
    {
        this.scale *= scale;

        updateTransform();
    }

	/**
	 * Applies a rotation to this sprite in its current state.
	 *
	 * @param deltaTheta the rotation angle
	 */
    public void rotate(double deltaTheta)
    {
        this.theta += deltaTheta;

        updateTransform();
    }

	/**
	 * Draws a frame of this sprite.
	 *
	 * @param g the <code>Graphics2D</code> Graphics2D object to protect
	 * @param obs object to be notified as more of the image is converted
	 */
    public void draw(Graphics2D g, ImageObserver obs)
    {
        if (iter.hasNext())
            g.drawImage(iter.next(), transform, obs);
        else
        {
            iter = iterator();
            g.drawImage(iter.next(), transform, obs);
        }
    }
}
