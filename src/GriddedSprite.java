import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class GriddedSprite extends Sprite
{
    private int x, y, scale, theta;
    private AffineTransform transform;

    public GriddedSprite(String fileName, int rows, int columns)
    {
        super(fileName, rows, columns);

        this.transform = new AffineTransform();
    }

    public GriddedSprite(String fileName, int rows, int columns, int tx, int ty, int scale, int theta)
    {
        super(fileName, rows, columns);
        this.x = tx;
        this.y = ty;
        this.scale = scale;
        this.theta = theta;

        updateTransform();
    }

    private void updateTransform()
    {
        this.transform = AffineTransform.getTranslateInstance(x, y);
        transform.scale(scale, scale);
        if (theta % 90 == 0)
            transform.quadrantRotate(theta / 90);
        else
            transform.rotate(theta);
    }

    public void setTranslate(int tx, int ty)
    {
        this.x = tx;
        this.y = ty;

        updateTransform();
    }

    public void setScale(int scale)
    {
        this.scale = scale;

        updateTransform();
    }

    public void setTheta(int theta)
    {
        this.theta = theta;

        updateTransform();
    }

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
