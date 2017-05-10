import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * описание линии
 */
public class Line extends Line2D{

    private Point p1;
    private Point p2;

    public  Line(){
        p1 = new Point();
        p2 = new Point();
    }

    public Line(double x1, double y1, double x2, double y2){
        p1 = new Point(x1, y1);
        p2 = new Point(x2, y2);
    }

    @Override
    public double getX1() {
        return p1.getX();
    }

    @Override
    public double getY1() {
        return p1.getY();
    }

    @Override
    public Point2D getP1() {
        return p1;
    }

    @Override
    public double getX2() {
        return p2.getX();
    }

    @Override
    public double getY2() {
        return p2.getY();
    }

    @Override
    public Point2D getP2() {
        return p2;
    }

    @Override
    public void setLine(double x1, double y1, double x2, double y2) {
      p1.setLocation(x1,y1);
      p2.setLocation(x2,y2);
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }
}
