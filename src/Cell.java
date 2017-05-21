import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Point;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Cell {
    private boolean flag;
    private boolean question;
    private boolean mine;
    private boolean isOpen;
    private int minesBeside;
    private Color color;
    private final Color openCell = new Color(0, 0, 0, 0);
    private final Color closeCell = new Color(121, 121, 121, 255);

    private Cell eastBrother;
    private Cell southeastBrother;
    private Cell southwesternBrother;
    private Cell westBrother;
    private Cell northwesternBrother;
    private Cell northeasternBrother;

    private Polygon poly;

    private int[] xpoints;
    private int[] ypoints;


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
        this.color = closeCell;

        this.eastBrother = null;
        this.southeastBrother = null;
        this.southwesternBrother = null;
        this.westBrother = null;
        this.northwesternBrother = null;
        this.northeasternBrother = null;

        this.poly = new Polygon(this.xpoints, this.ypoints, 6);

    }

    public void howMinesBeside() {       //сколько мин вокруг клетки
        if (this.westBrother != null && this.westBrother.getMine()) this.minesBeside++;
        if (this.southwesternBrother != null && this.southwesternBrother.getMine()) this.minesBeside++;
        if (this.southeastBrother != null && this.southeastBrother.getMine()) this.minesBeside++;
        if (this.eastBrother != null && this.eastBrother.getMine()) this.minesBeside++;
        if (this.northeasternBrother != null && this.northeasternBrother.getMine()) this.minesBeside++;
        if (this.northwesternBrother != null && this.northwesternBrother.getMine()) this.minesBeside++;
    }

    public void open() {
        this.isOpen = true;
        this.color = openCell;
    }

    public Cell addEastBrother(Cell cell) {
        Cell brother = new Cell(new Point(cell.xpoints[0] + 40, cell.ypoints[0]));
        brother.westBrother = cell;
        cell.eastBrother = brother;
        return brother;
    }

    /**
     * Геттеры
     *
     * @return
     */
    public ArrayList<Cell> getClosedBrothers() {
        ArrayList<Cell> brothers = new ArrayList<>();
        if (eastBrother != null && !eastBrother.isOpen) brothers.add(eastBrother);
        if (southeastBrother != null && !southeastBrother.isOpen) brothers.add(southeastBrother);
        if (southwesternBrother != null && !southwesternBrother.isOpen) brothers.add(southwesternBrother);
        if (westBrother != null && !westBrother.isOpen) brothers.add(westBrother);
        if (northwesternBrother != null && !northwesternBrother.isOpen) brothers.add(northwesternBrother);
        if (northeasternBrother != null && !northeasternBrother.isOpen) brothers.add(northeasternBrother);
        return brothers;
    }

    public int[] getXpoints() {
        return xpoints;
    }

    public int[] getYpoints() {
        return ypoints;
    }

    public Polygon getPoly() {
        return poly;
    }

    public boolean getFlag() {
        return flag;
    }

    public boolean getQuestion() {
        return question;
    }

    public boolean getMine() {
        return mine;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    public int getMinesBeside() {
        return minesBeside;
    }

    public Color getColor() {
        return color;
    }

    /**
     * сеттеры
     */
    public void setEastBrother(Cell brother) {
        eastBrother = brother;
    }

    public void setMine(boolean condition) {
        mine = condition;
    }

    public void setFlag(boolean condition) {
        flag = condition;
    }

    public void setQuestion(boolean condition) {
        question = condition;
    }

    public void setSoutheastBrother(Cell brother) {
        southeastBrother = brother;
    }

    public void setSouthwesternBrother(Cell brother) {
        southwesternBrother = brother;
    }

    public void setWestBrother(Cell brother) {
        westBrother = brother;
    }

    public void setNorthwesternBrother(Cell brother) {
        northwesternBrother = brother;
    }

    public void setNortheasternBrother(Cell brother) {
        northeasternBrother = brother;
    }
}
