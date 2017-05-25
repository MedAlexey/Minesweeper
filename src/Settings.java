import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Settings extends JFrame {

    private final int CELL_HEIGHT = 35;
    private final int CELL_WIDTH = 40;
    private final int TOOLBAR_HEIGHT = 30;
    private final int MAINFRAME_HORIZONTAL_BORDER_HEIGHT = 70;
    private final int MAINFRAAME_VERTICAL_BORDER_WIDTH = 43;

    private int newCellsInWidth;
    private int newCellsInHeight;
    private int newNumberOfMines;
    private int newMustBeOpen;
    private boolean closeFrame = false;

    private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height -
            Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;

    public Settings() {
        newCellsInHeight = SettingsFrame.getCellsInHeight();
        newCellsInWidth = SettingsFrame.getCellsInWidth();
        newNumberOfMines = SettingsFrame.getNumbOfMines();
        newMustBeOpen = SettingsFrame.getMustBeOpen();

    }

    public int getCellsInWidth() {
        return newCellsInWidth;
    }

    public int getCellsInHeight() {
        return newCellsInHeight;
    }

    public int getNumbOfMines() {
        return newNumberOfMines;
    }

    public boolean getCloseFrame(){
        return closeFrame;
    }

    public int getMustBeOpen() {
        return newMustBeOpen;
    }

    public void okClicked(String cellsInWidth,String cellsInHeight,String numberOfMines){
        int tmpCellsInWidth = setElement(cellsInWidth);
        int tmpCellsInHeight = setElement(cellsInHeight);
        int tmpNumberOfMines = setElement(numberOfMines);

        if (tmpCellsInHeight == 0 || tmpCellsInWidth == 0 || tmpNumberOfMines == 0)
            showErrorMessage();
        else if(tmpNumberOfMines >= tmpCellsInHeight*tmpCellsInWidth -5
                || tmpCellsInHeight*CELL_HEIGHT + TOOLBAR_HEIGHT + MAINFRAME_HORIZONTAL_BORDER_HEIGHT > screenHeight
                || tmpCellsInWidth*CELL_WIDTH + MAINFRAAME_VERTICAL_BORDER_WIDTH > screenWidth)
            showInformationMessage();
        else {
            newCellsInHeight = tmpCellsInHeight;
            newCellsInWidth = tmpCellsInWidth;
            newNumberOfMines = tmpNumberOfMines;
            newMustBeOpen = newCellsInHeight * newCellsInWidth - newNumberOfMines;
            closeFrame = true;
        }
    }

    private int textHandler(String line) {
        String[] parts = line.split("\\s+");
        try {
            if (parts.length == 1) return Integer.parseInt(parts[0]);
            else return 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int setElement(String line) {

        if (!line.equals("")) {
            return textHandler(line);
        } else {
            return 0;
        }
    }

    private void showErrorMessage() {         //диалоговое окно с ошибкой
        JOptionPane.showMessageDialog(null, "Неправильный формат", "Error", JOptionPane.WARNING_MESSAGE);
    }

    private void showInformationMessage() {
        JOptionPane.showMessageDialog(null, "Недопустимый размер", "Error", JOptionPane.WARNING_MESSAGE);
    }
}
