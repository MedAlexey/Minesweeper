import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Settings extends JFrame {

    private static int cellsInWidth = 45;       //клеток в длину
    private static int cellsInHeight = 20;      //клеток в ширину
    private static int numbOfMines = 300;        //кол-во мин
    private JTextField usersCellsInHeight;  //поле для ввода ячеек в высоту
    private JTextField usersCellsInWidth;    //поле для ввода ячеек в ширину
    private JTextField usersNumberOfMines;   //поля для ввода кол-ва мин
    private static int mustBeOpen = cellsInHeight * cellsInWidth - numbOfMines;
    private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height -
            Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;

    public Settings() {
        super("Settings");
        setBackground(Color.lightGray);

        Box box1 = Box.createHorizontalBox();
        JLabel cellsInWidth = new JLabel("Ячеек в ширину:");
        usersCellsInWidth = new JTextField("" + getCellsInWidth(), 15);
        box1.add(cellsInWidth);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(usersCellsInWidth);
// Настраиваем вторую горизонтальную панель
        Box box2 = Box.createHorizontalBox();
        JLabel cellsInHeight = new JLabel("Ячеек в высоту:");
        usersCellsInHeight = new JTextField("" + getCellsInHeight(), 15);
        box2.add(cellsInHeight);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(usersCellsInHeight);
//Панель с вводом кол-ва мин
        Box box3 = Box.createHorizontalBox();
        JLabel numbOfMines = new JLabel("Колличество мин:");
        usersNumberOfMines = new JTextField("" + getNumbOfMines(), 15);
        box3.add(numbOfMines);
        box3.add(Box.createHorizontalStrut(6));
        box3.add(usersNumberOfMines);
// Настраиваем четвёртую горизонтальную панель (с кнопками)
        Box box4 = Box.createHorizontalBox();
        JButton ok = new JButton("OK");
        ok.addMouseListener(new OkListener());
        JButton cancel = new JButton("Отмена");
        cancel.addMouseListener(new СancelListener());
        box4.add(Box.createHorizontalGlue());
        box4.add(ok);
        box4.add(Box.createHorizontalStrut(12));
        box4.add(cancel);
// Размещаем три горизонтальные панели на одной вертикальной
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainBox.add(box1);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box2);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box3);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(box4);
        setContentPane(mainBox);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static int getCellsInWidth() {
        return cellsInWidth;
    }

    public static int getCellsInHeight() {
        return cellsInHeight;
    }

    public static int getNumbOfMines() {
        return numbOfMines;
    }

    public static int getMustBeOpen() {
        return mustBeOpen;
    }


    class OkListener implements MouseListener {                 //листнер кнопки "ок"

        public void mouseClicked(MouseEvent event) {
            int tmpCellsInWidth = new Settings().setElement(usersCellsInWidth.getText());
            int tmpCellsInHeight = new Settings().setElement(usersCellsInHeight.getText());
            int tmpNumbOfMines = new Settings().setElement(usersNumberOfMines.getText());
            if (tmpCellsInHeight == 0 || tmpCellsInWidth == 0 || tmpNumbOfMines == 0)
                ShowErrorMessage();
            else if(tmpNumbOfMines >= tmpCellsInHeight*tmpCellsInWidth -5 || tmpCellsInHeight*35 + 100 > screenHeight || tmpCellsInWidth*40 + 43 > screenWidth)
                ShowInformationMessage();
            else {
                cellsInHeight = tmpCellsInHeight;
                cellsInWidth = tmpCellsInWidth;
                numbOfMines = tmpNumbOfMines;
                mustBeOpen = cellsInHeight * cellsInWidth - numbOfMines;
                new Main().newGame();
                setVisible(false);
                dispose();
            }
        }

        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mousePressed(MouseEvent event) {
        }

        public void mouseReleased(MouseEvent event) {
        }

    }

    class СancelListener implements MouseListener {                 //листнер кнопки "cancel"

        public void mouseClicked(MouseEvent event) {
            setVisible(false);
            dispose();
        }

        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mousePressed(MouseEvent event) {
        }

        public void mouseReleased(MouseEvent event) {
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
            return new Settings().textHandler(line);
        } else {
            return 0;
        }
    }

    private void ShowErrorMessage() {         //диалоговое окно с ошибкой
        JOptionPane.showMessageDialog(null, "Неправильный формат", "Error", JOptionPane.WARNING_MESSAGE);
    }

    private void ShowInformationMessage() {
        JOptionPane.showMessageDialog(null, "Недопустимый размер", "Error", JOptionPane.WARNING_MESSAGE);
    }
}
