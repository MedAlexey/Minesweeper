import java.awt.*;
import java.awt.Point;
import java.awt.Color;


public class Cell {
    boolean flag;
    boolean question;
    boolean mine;
    boolean isOpen;
    int minesBeside;
    Color color = isOpen ? new Color(121, 121, 121, 0) : new Color(121, 121, 121, 255);

    Cell eastBrother;
    Cell southeastBrother;
    Cell southwesternBrother;
    Cell westBrother;
    Cell northwesternBrother;
    Cell northeasternBrother;

    Polygon poly;

    int[] xpoints;
    int[] ypoints;


    public Cell(Point point) {
        int[] xPoints = {point.x, point.x, point.x + 20, point.x + 40, point.x + 40, point.x + 20};
        int[] yPoints = {point.y, point.y + 20, point.y + 35, point.y + 20, point.y, point.y - 15};
        this.xpoints = xPoints;
        this.ypoints = yPoints;
        this.flag = false;
        this.question = false;
        this.mine = false;
        this.minesBeside = 0;
        this.isOpen = false;

        this.eastBrother = null;
        this.southeastBrother = null;
        this.southwesternBrother = null;
        this.westBrother = null;
        this.northwesternBrother = null;
        this.northeasternBrother = null;

        this.poly = new Polygon(this.xpoints, this.ypoints, 6);

    }


    public Cell addEastBrother(Cell cell) {
        Cell brother = new Cell(new Point(cell.xpoints[0] + 40, cell.ypoints[0]));
        brother.westBrother = cell;
        cell.eastBrother = brother;
        return brother;
    }

}
