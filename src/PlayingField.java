import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class PlayingField extends JPanel {

    private ArrayList<Cell> cells = startGame();   //массив клеток
    private boolean gameFailed = false;
    private boolean firstDepression = true;    //открывание первой клетки
    private int totalOpenCells = 0;
    private ArrayDeque<Cell> queue = new ArrayDeque<>();

    private Image flagImg;
    private Image questionImg;
    private Image mineImg;
    private Image oneImg;
    private Image twoImg;
    private Image threeImg;
    private Image fourImg;
    private Image fiveImg;
    private Image sixImg;
    private final int IMG_WIDTH = 20;
    private final int IMG_HEIGHT = 20;

    private final int IMAGE_DISPLACEMENT = 12;
    private final int X_DISPLACEMENT = 20;
    private final int Y_DISPLACEMENT = 35;


    public PlayingField() {

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    button1Pressed(e.getX(), e.getY());
                }

                if (e.getButton() == MouseEvent.BUTTON3) {
                    button3Pressed(e.getX(), e.getY());
                }
            }
        });

        images();
    }

    private void images() {               //загружаем изображения
        try {
            flagImg = ImageIO.read(new File("flag.png"));
            questionImg = ImageIO.read(new File("question.png"));
            mineImg = ImageIO.read(new File("mine.png"));
            oneImg = ImageIO.read(new File("numb1.png"));
            twoImg = ImageIO.read(new File("numb2.png"));
            threeImg = ImageIO.read(new File("numb3.png"));
            fourImg = ImageIO.read(new File("numb4.png"));
            fiveImg = ImageIO.read(new File("numb5.png"));
            sixImg = ImageIO.read(new File("numb6.png"));
        } catch (IOException e) {

        }
    }

    private ArrayList<Cell> startGame() {           // заполняем массив клеток, определяя их

        ArrayList<Cell> result = new ArrayList<>();
        Point startPoint = new Point(22, -18);            //точка начала заполнения строки
        Cell cell;
        int totalHeight = 1;          // высота поля в конкретный момент
        while (totalHeight <= SettingsFrame.getCellsInHeight()) {
            if (totalHeight % 2 != 0) {                      //нечётный ярус
                startPoint.x -= X_DISPLACEMENT;
                startPoint.y += Y_DISPLACEMENT;
                cell = new Cell(startPoint);
                if (totalHeight != 1) {
                    cell.setNortheasternBrother(result.get(result.size() - SettingsFrame.getCellsInWidth()));
                    result.get(result.size() - 1 - SettingsFrame.getCellsInWidth() + 1).setSouthwesternBrother(cell);
                }
                result.add(cell);
                for (int j = 1; j < SettingsFrame.getCellsInWidth(); j++) {
                    cell = cell.addEastBrother(cell);
                    result.get(result.size() - 1).setEastBrother(cell);
                    cell.setWestBrother(result.get(result.size() - 1));
                    if (totalHeight != 1) {
                        cell.setNorthwesternBrother(result.get(result.size() - 1 - SettingsFrame.getCellsInWidth()));
                        result.get(result.size() - 1 - SettingsFrame.getCellsInWidth()).setSoutheastBrother(cell);
                        cell.setNortheasternBrother(result.get(result.size() - SettingsFrame.getCellsInWidth()));
                        result.get(result.size() - SettingsFrame.getCellsInWidth()).setSouthwesternBrother(cell);
                    }
                    result.add(cell);
                }
            } else {                           //чётный ярус клеток
                startPoint.x += X_DISPLACEMENT;
                startPoint.y += Y_DISPLACEMENT;
                cell = new Cell(startPoint);
                cell.setNorthwesternBrother(result.get(result.size() - SettingsFrame.getCellsInWidth()));
                result.get(result.size() - SettingsFrame.getCellsInWidth()).setSoutheastBrother(cell);
                cell.setNortheasternBrother(result.get(result.size() - SettingsFrame.getCellsInWidth() + 1));
                result.get(result.size() - SettingsFrame.getCellsInWidth() + 1).setSouthwesternBrother(cell);
                result.add(cell);
                for (int j = 1; j < SettingsFrame.getCellsInWidth(); j++) {
                    cell = cell.addEastBrother(cell);
                    result.get(result.size() - 1).setEastBrother(cell);
                    cell.setWestBrother(result.get(result.size() - 1));
                    cell.setNorthwesternBrother(result.get(result.size() - SettingsFrame.getCellsInWidth()));
                    result.get(result.size() - SettingsFrame.getCellsInWidth()).setSoutheastBrother(cell);
                    if (j != SettingsFrame.getCellsInWidth() - 1) {
                        cell.setNortheasternBrother(result.get(result.size() - SettingsFrame.getCellsInWidth() + 1));
                        result.get(result.size() - SettingsFrame.getCellsInWidth() + 1).setSouthwesternBrother(cell);
                    }
                    result.add(cell);
                }
            }
            totalHeight++;
        }

        return result;
    }

    private void mining() {         //минирование поля
        int totalMines = 0;
        while (totalMines < SettingsFrame.getNumbOfMines()) {
            Random random = new Random();
            int num = random.nextInt(SettingsFrame.getCellsInHeight() * SettingsFrame.getCellsInWidth() - 1);
            if (!cells.get(num).getMine() && !cells.get(num).getIsOpen()) {
                cells.get(num).setMine(true);
                totalMines++;
            }
        }
    }

    private void dialogs(){
        if (gameFailed) {
            JOptionPane.showMessageDialog(null, "<html><H2>Вы проиграли</H2>", "Defeat", JOptionPane.PLAIN_MESSAGE);
            if (JOptionPane.OK_OPTION == 0) new Main().newGame();
        }

        if (totalOpenCells == SettingsFrame.getMustBeOpen()) {
            JOptionPane.showMessageDialog(null, "<html><H2>Вы победили</H2>", "Greeting",JOptionPane.PLAIN_MESSAGE);
            if (JOptionPane.OK_OPTION == 0) new Main().newGame();
        }
    }

    private void button3Pressed(int x, int y) {     //правая кнопка нажата
        for (Cell cell : cells) {
            if (cell.getPoly().contains(x, y)) {
                if (!cell.getFlag() && !cell.getQuestion()) cell.setFlag(true);
                else if (cell.getFlag() && !cell.getQuestion()) {
                    cell.setFlag(false);
                    cell.setQuestion(true);
                } else if (!cell.getFlag() && cell.getQuestion()) {
                    cell.setQuestion(false);
                }
            }
        }
        repaint();
    }


    private void button1Pressed(int x, int y) {           //левая кнопка нажата
        for (Cell cell : cells) {
            if (cell.getPoly().contains(x, y) && !cell.getIsOpen()) {
                totalOpenCells++;
                cell.setIsOpen(true);
                if (firstDepression) {     //если первое нажатие
                    firstDepression = false;
                    mining();
                    repaint();
                }
                if (cell.getMinesBeside() == 0) {
                    openBrothers(cell);
                }
                if (cell.getMine()) {                        // открытие всех мин при проигрыше
                    for (int i = 0; i < cells.size() - 1; i++) {
                        if (cells.get(i).getMine()) {
                            cells.get(i).setIsOpen(true);
                        }
                    }
                    gameFailed = true;
                }
            }
        }
        repaint();
        dialogs();
    }

    private void openBrothers(Cell cell){
        queue.add(cell);
       while (!queue.isEmpty()){
           Cell newCell = queue.removeFirst();
           if (!newCell.getIsOpen()) {
               newCell.setIsOpen(true);
               totalOpenCells++;
           }
           if(newCell.getMinesBeside() == 0){
               for (Cell brother: newCell.getClosedBrothers()){
                   queue.addFirst(brother);
               }
           }
       }
    }

    @Override
    public Dimension getSize(){
        return new Dimension(SettingsFrame.getCellsInWidth()*40,(SettingsFrame.getCellsInHeight())*35 + 15);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Cell cell : cells) {
            g.setColor(cell.getColor());
            g.fillPolygon(cell.getPoly());
            g.setColor(Color.BLACK);
            g.drawPolygon(cell.getPoly());

            if (cell.getIsOpen() && !cell.getMine()) {
                if (cell.getMinesBeside() == 1) g.drawImage(oneImg, cell.getXpoints()[0] + IMAGE_DISPLACEMENT, cell.getYpoints()[0], IMG_WIDTH, IMG_HEIGHT, null);
                else if (cell.getMinesBeside() == 2)
                    g.drawImage(twoImg, cell.getXpoints()[0] + IMAGE_DISPLACEMENT, cell.getYpoints()[0], IMG_WIDTH, IMG_HEIGHT, null);
                else if (cell.getMinesBeside() == 3)
                    g.drawImage(threeImg, cell.getXpoints()[0] + IMAGE_DISPLACEMENT, cell.getYpoints()[0], IMG_WIDTH, IMG_HEIGHT, null);
                else if (cell.getMinesBeside() == 4)
                    g.drawImage(fourImg, cell.getXpoints()[0] + IMAGE_DISPLACEMENT, cell.getYpoints()[0], IMG_WIDTH, IMG_HEIGHT, null);
                else if (cell.getMinesBeside() == 5)
                    g.drawImage(fiveImg, cell.getXpoints()[0] + IMAGE_DISPLACEMENT, cell.getYpoints()[0], IMG_WIDTH, IMG_HEIGHT, null);
                else if (cell.getMinesBeside() == 6)
                    g.drawImage(sixImg, cell.getXpoints()[0] + IMAGE_DISPLACEMENT, cell.getYpoints()[0], IMG_WIDTH, IMG_HEIGHT, null);
            }

            if (cell.getMine() && cell.getIsOpen()) g.drawImage(mineImg, cell.getXpoints()[0] + IMAGE_DISPLACEMENT,
                    cell.getYpoints()[0], IMG_WIDTH, IMG_HEIGHT, null);

            if (cell.getFlag() && !cell.getIsOpen()) g.drawImage(flagImg, cell.getXpoints()[0] + IMAGE_DISPLACEMENT,
                    cell.getYpoints()[0], IMG_WIDTH, IMG_HEIGHT, null);
            else if (cell.getQuestion() && !cell.getIsOpen())
                g.drawImage(questionImg, cell.getXpoints()[0] + IMAGE_DISPLACEMENT, cell.getYpoints()[0], IMG_WIDTH, IMG_HEIGHT, null);

        }
    }
}