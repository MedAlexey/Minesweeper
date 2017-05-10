import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FailWindow extends JFrame{

    public FailWindow(){
        super("Defeat");
        setBackground(Color.lightGray);

        Box mainBox = Box.createVerticalBox();
        JLabel text = new JLabel("<html><H1>Вы проиграли</H1>");
        mainBox.add(text);
        mainBox.add(Box.createVerticalStrut(25));

        Box box1 = Box.createHorizontalBox();
        JButton ok = new JButton("Ok");
        box1.add(Box.createHorizontalGlue());
        box1.add(ok);
        box1.add(Box.createHorizontalGlue());
        mainBox.add(box1);

        setContentPane(mainBox);
        setPreferredSize(new Dimension(250,150));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Main().newGame();
            }
        });
    }
}
