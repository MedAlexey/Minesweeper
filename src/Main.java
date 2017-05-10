import javax.swing.*;

public class Main {

    private static JFrame mainFrame;
    public static void main(String[] args) {
        mainFrame = new MainFrame();   //создаём окно, в нём рисуем,  потом показываем
        mainFrame.setVisible(true);
    }

    public void newGame(){
       mainFrame.dispose();
       mainFrame = new MainFrame();
       mainFrame.setVisible(true);
    }
}
