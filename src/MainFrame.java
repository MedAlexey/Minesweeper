import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;

public class MainFrame extends JFrame {

    private final int TOOLBAR_WIDTH = 17;
    private final int MAINFRAAME_VERTICAL_BORDER_WIDTH = 43;
    private final int CELL_HEIGHT = 35;
    private final int CELL_WIDTH = 40;
    private final int TOOLBAR_HEIGHT = 30;
    private final int MAINFRAME_HORIZONTAL_BORDER_HEIGHT = 70;

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
                JFrame settingsWindow = new SettingsFrame();         // по нажатию на кнопку вызываем окно настроек
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
        JScrollPane scrollPane = new JScrollPane(field);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        //getContentPane().add(field, BorderLayout.CENTER);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(238,238,238,255));
        //setResizable(false);
        setSize(SettingsFrame.getCellsInWidth()*CELL_WIDTH + MAINFRAAME_VERTICAL_BORDER_WIDTH + TOOLBAR_WIDTH,
                (SettingsFrame.getCellsInHeight())*CELL_HEIGHT + TOOLBAR_HEIGHT + MAINFRAME_HORIZONTAL_BORDER_HEIGHT);
        setLocationRelativeTo(null);
    }

}