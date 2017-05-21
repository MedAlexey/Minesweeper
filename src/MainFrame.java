import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;

public class MainFrame extends JFrame {


    public MainFrame() {
        super("Minesweeper");

        JButton newPlay = new JButton("<html><i>Новая игра</i>");        //кнопка "новая игра игра"
        newPlay.setBorder(new EmptyBorder(10,10,10,10));
        newPlay.setMaximumSize(new Dimension(90,30));
        newPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Main().newGame();
            }
        });

        JButton settings = new JButton("<HTML><i>Настройки</i>");           //кнопка "настройки"
        settings.setBorder(new EmptyBorder(10,10,10,10));
        settings.setMaximumSize(new Dimension(90,30));
        settings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame settingsWindow = new Settings();         // по нажатию на кнопку вызываем окно настроек
                settingsWindow.setVisible(true);
            }
        });

        JToolBar toolBar = new JToolBar();                                  //верхняя панелька
        toolBar.setPreferredSize(new Dimension(50,30));
        toolBar.add(newPlay);
        toolBar.add(settings);
        toolBar.setFloatable(false);                                       //изменение размера
        getContentPane().add(toolBar, BorderLayout.NORTH);

        JPanel field = new PlayingField();           //игровое поле
        field.setBorder(new BevelBorder(BevelBorder.LOWERED));
        getContentPane().add(field, BorderLayout.CENTER);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(238,238,238,255));
        setSize(Settings.getCellsInWidth()*40 + 43,(Settings.getCellsInHeight())*35 + 100);
        setLocationRelativeTo(null);
    }

}