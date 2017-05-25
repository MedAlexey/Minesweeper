import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.awt.*;


public class CellTests {
    Cell cell;
    Cell brother;
    Cell eastBrother;
    Cell southeastBrother;
    Cell southwesternBrother;
    Cell westBrother;
    Cell northwesternBrother;
    Cell northeasternBrother;

    @Before
    public void createCells(){
        cell = new Cell(new Point(20,20));
        brother = cell.addEastBrother(cell);
        eastBrother = new Cell(new Point(60,20));
        eastBrother.setMine(true);
        southeastBrother = new Cell(new Point(40,55));
        southeastBrother.setMine(true);
        southwesternBrother = new Cell(new Point(0,55));
        westBrother = new Cell(new Point(-10, 20));
        northwesternBrother = new Cell(new Point(0,-15));
        northwesternBrother.setMine(true);
        northeasternBrother = new Cell(new Point(40, -15));

        cell.setEastBrother(eastBrother);
        cell.setSoutheastBrother(southeastBrother);
        cell.setSouthwesternBrother(southwesternBrother);
        cell.setWestBrother(westBrother);
        cell.setNorthwesternBrother(northwesternBrother);
        cell.setNortheasternBrother(northeasternBrother);

        cell.setIsOpen(true);
    }

    @Test
    public void addEastBrothrTest(){
        int[] brotherXPoints = {60, 60, 80, 100, 100, 80};
        int[] brotherYPoints = {20, 40, 55, 40, 20, 5};

        Assert.assertArrayEquals(brotherXPoints ,brother.getXpoints());
        Assert.assertArrayEquals(brotherYPoints, brother.getYpoints());
        Assert.assertFalse(brother.getFlag());
        Assert.assertFalse(brother.getIsOpen());
        Assert.assertFalse(brother.getQuestion());
        Assert.assertFalse(brother.getMine());
    }

    @Test
    public void getMinesBesideTest(){
        Assert.assertEquals(3,cell.getMinesBeside());
    }

    @Test
    public void getColorTest(){
        Assert.assertEquals(new Color(0, 0, 0, 0), cell.getColor());
        Assert.assertEquals(new Color(121, 121, 121, 255), southeastBrother.getColor());
    }


}
