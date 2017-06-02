import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Settings {

    private static int cellsInWidth = 45;
    private static int cellsInHeight = 20;
    private static int numberOfMines = 300;
    private static boolean closeFrame;


    public Settings() {
        closeFrame = true;
        int tmpCellsInWidth = setElement(SettingsFrame.getUsersCellsInWidth());
        int tmpCellsInHeight = setElement(SettingsFrame.getUsersCellsInHeight());
        int tmpNumberOfMines = setElement(SettingsFrame.getUsersNumbOfMines());
        if(tmpCellsInHeight == 0 || tmpCellsInWidth == 0 || tmpNumberOfMines == 0){
            showErrorMessage();
            closeFrame = false;
        }
        else if(tmpNumberOfMines >= tmpCellsInHeight*tmpCellsInWidth -5 || tmpCellsInWidth < 5 || tmpCellsInHeight < 2){
            closeFrame = false;
            showInformationMessage();
        }
        else{
            cellsInWidth = tmpCellsInWidth;
            cellsInHeight = tmpCellsInHeight;
            numberOfMines = tmpNumberOfMines;
        }
    }

    public static int getCellsInWidth() {
        return cellsInWidth;
    }

    public static int getCellsInHeight() {
        return cellsInHeight;
    }

    public static int getNumberOfMines() {
        return numberOfMines;
    }

    public static boolean getCloseFrame(){
        return closeFrame;
    }

    public static int getMustBeOpen() {
        return cellsInHeight * cellsInWidth - numberOfMines;
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

        int result;
        if(!line.equals("")){
            result = textHandler(line);
        } else result = 0;

        return result;
    }

    private void showErrorMessage() {         //диалоговое окно с ошибкой
        JOptionPane.showMessageDialog(null, "Неправильный формат", "Error", JOptionPane.WARNING_MESSAGE);
    }

    private void showInformationMessage() {
        JOptionPane.showMessageDialog(null, "Недопустимый размер", "Error", JOptionPane.WARNING_MESSAGE);
    }
}
