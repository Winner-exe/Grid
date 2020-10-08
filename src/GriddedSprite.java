import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class GriddedSprite extends Sprite
{
    private int x, y;
    private double scale, theta;
    private AffineTransform transform;

    public GriddedSprite(String fileName, int rows, int columns)
    {
        super(fileName, rows, columns);

        setLocation(0, 0);
        setScale(1);
        setTheta(0);

        updateTransform();
    }

    public GriddedSprite(String fileName, int rows, int columns, int tx, int ty, double scale, double theta)
    {
        super(fileName, rows, columns);
        setLocation(tx, ty);
        setScale(scale);
        setTheta(theta);

        updateTransform();
    }

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

    public void setLocation(int tx, int ty)
    {
        this.x = tx;
        this.y = ty;

        updateTransform();
    }

    public void setScale(double scale)
    {
        this.scale = scale;

        updateTransform();
    }

    public void setTheta(double theta)
    {
        this.theta = theta;

        updateTransform();
    }

    public void translate(int tx, int ty)
    {
        this.x += tx;
        this.y += ty;

        updateTransform();
    }

    public void scale(double scale)
    {
        this.scale *= scale;

        updateTransform();
    }

    public void rotate(double deltaTheta)
    {
        this.theta += deltaTheta;

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
