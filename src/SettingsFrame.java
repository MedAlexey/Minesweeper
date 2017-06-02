import javax.swing.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SettingsFrame  extends JFrame {

    private static JTextField usersCellsInHeight;  //поле для ввода ячеек в высоту
    private static JTextField usersCellsInWidth;    //поле для ввода ячеек в ширину
    private static JTextField usersNumberOfMines;   //поля для ввода кол-ва мин
    private static Settings settings;

    public SettingsFrame() {
        super("Settings");
        setBackground(Color.lightGray);

        Box box1 = Box.createHorizontalBox();
        JLabel cellsInWidth = new JLabel("Ячеек в ширину:");
        usersCellsInWidth = new JTextField("" +  Settings.getCellsInWidth(), 15);
        box1.add(cellsInWidth);
        box1.add(Box.createHorizontalStrut(6));
        box1.add(usersCellsInWidth);
// Настраиваем вторую горизонтальную панель
        Box box2 = Box.createHorizontalBox();
        JLabel cellsInHeight = new JLabel("Ячеек в высоту:");
        usersCellsInHeight = new JTextField("" + Settings.getCellsInHeight(), 15);
        box2.add(cellsInHeight);
        box2.add(Box.createHorizontalStrut(6));
        box2.add(usersCellsInHeight);
//Панель с вводом кол-ва мин
        Box box3 = Box.createHorizontalBox();
        JLabel numbOfMines = new JLabel("Колличество мин:");
        usersNumberOfMines = new JTextField("" + Settings.getNumberOfMines(), 15);
        box3.add(numbOfMines);
        box3.add(Box.createHorizontalStrut(6));
        box3.add(usersNumberOfMines);
// Настраиваем четвёртую горизонтальную панель (с кнопками)
        Box box4 = Box.createHorizontalBox();
        JButton ok = new JButton("OK");
        ok.addMouseListener(new SettingsFrame.OkListener());
        JButton cancel = new JButton("Отмена");
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                dispose();
            }
        });
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

        settings = new Settings();
    }


    public static Settings getSettings() { return settings;}

    public static String getUsersCellsInWidth() {
        return usersCellsInWidth.getText();
    }

    public static String getUsersCellsInHeight() {
        return usersCellsInHeight.getText();
    }

    public static String getUsersNumbOfMines() {
        return usersNumberOfMines.getText();
    }

    class OkListener implements MouseListener {                 //листнер кнопки "ок"

        public void mouseClicked(MouseEvent event) {
            settings = new Settings();
            if (settings.getCloseFrame()){
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
}
