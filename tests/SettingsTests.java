import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SettingsTests {
    Settings settings = new Settings();

    @Test
    public void okClickedTest(){
        settings.okClicked("7","5","25");
        Assert.assertEquals(5, settings.getCellsInHeight());
        Assert.assertEquals(7, settings.getCellsInWidth());
        Assert.assertEquals(25, settings.getNumbOfMines());
        Assert.assertEquals(10,settings.getMustBeOpen());
    }
}
