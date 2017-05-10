import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class PlayingField extends JPanel {

    private ArrayList<Cell> cells = startGame();   //массив клеток
    private boolean gameFailed = false;
    private boolean firstDepression = true;    //открывание первой клетки

    private Image flagImg;
    private Image questionImg;
    private Image mineImg;
    private Image oneImg;
    private Image twoImg;
    private Image threeImg;
    private Image fourImg;
    private Image fiveImg;
    private Image sixImg;


    public PlayingField() {

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Button1Pressed(e.getX(), e.getY());
                }

                if (e.getButton() == MouseEvent.BUTTON3) {
                    Button3Pressed(e.getX(), e.getY());
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
        while (totalHeight <= Settings.getCellsInHeight()) {
            if (totalHeight % 2 != 0) {                      //нечётный ярус
                startPoint.x = startPoint.x - 20;
                startPoint.y = startPoint.y + 35;
                cell = new Cell(startPoint);
                if (totalHeight != 1) {
                    cell.northeasternBrother = result.get(result.size() - 1 - Settings.getCellsInWidth() + 1);
                    result.get(result.size() - 1 - Settings.getCellsInWidth() + 1).southwesternBrother = cell;
                }
                result.add(cell);
                for (int j = 1; j < Settings.getCellsInWidth(); j++) {
                    cell = cell.addEastBrother(cell);
                    result.get(result.size() - 1).eastBrother = cell;
                    cell.westBrother = result.get(result.size() - 1);
                    if (totalHeight != 1) {
                        cell.northwesternBrother = result.get(result.size() - 1 - Settings.getCellsInWidth());
                        result.get(result.size() - 1 - Settings.getCellsInWidth()).southeastBrother = cell;
                        cell.northeasternBrother = result.get(result.size() - 1 - Settings.getCellsInWidth() + 1);
                        result.get(result.size() - 1 - Settings.getCellsInWidth() + 1).southwesternBrother = cell;
                    }
                    result.add(cell);
                }
            } else {                           //чётный ярус клеток
                startPoint.x += 20;
                startPoint.y += 35;
                cell = new Cell(startPoint);
                cell.northwesternBrother = result.get(result.size() - 1 + 1 - Settings.getCellsInWidth());
                result.get(result.size() - 1 - Settings.getCellsInWidth() + 1).southeastBrother = cell;
                cell.northeasternBrother = result.get(result.size() - 1 - Settings.getCellsInWidth() + 2);
                result.get(result.size() - 1 - Settings.getCellsInWidth() + 2).southwesternBrother = cell;
                result.add(cell);
                for (int j = 1; j < Settings.getCellsInWidth(); j++) {
                    cell = cell.addEastBrother(cell);
                    result.get(result.size() - 1).eastBrother = cell;
                    cell.westBrother = result.get(result.size() - 1);
                    cell.northwesternBrother = result.get(result.size() - 1 - Settings.getCellsInWidth() + 1);
                    result.get(result.size() - 1 - Settings.getCellsInWidth() + 1).southeastBrother = cell;
                    if (j != Settings.getCellsInWidth() - 1) {
                        cell.northeasternBrother = result.get(result.size() - 1 - Settings.getCellsInWidth() + 2);
                        result.get(result.size() - 1 - Settings.getCellsInWidth() + 2).southwesternBrother = cell;
                    }
                    result.add(cell);
                }
            }
            totalHeight++;
        }

        return result;
    }

    private void howMinesBeside() {       //сколько мин вокруг клетки
        for (Cell cell : cells) {
            int result = 0;
            if (cell.westBrother != null && cell.westBrother.mine) result++;
            if (cell.southwesternBrother != null && cell.southwesternBrother.mine) result++;
            if (cell.southeastBrother != null && cell.southeastBrother.mine) result++;
            if (cell.eastBrother != null && cell.eastBrother.mine) result++;
            if (cell.northeasternBrother != null && cell.northeasternBrother.mine) result++;
            if (cell.northwesternBrother != null && cell.northwesternBrother.mine) result++;
            cell.minesBeside = result;
        }
    }

    private void mining() {         //минирование поля
        int totalMines = 0;
        while (totalMines < Settings.getNumbOfMines()) {
            Random random = new Random();
            int num = random.nextInt(Settings.getCellsInHeight() * Settings.getCellsInWidth() - 1);
            if (!cells.get(num).mine && !cells.get(num).isOpen) {
                cells.get(num).mine = true;
                totalMines++;
            }
        }
    }

    private void Button3Pressed(int x, int y) {     //правая кнопка нажата
        for (Cell cell : cells) {
            if (cell.poly.contains(x, y)) {
                if (!cell.flag && !cell.question) cell.flag = true;
                else if (cell.flag && !cell.question) {
                    cell.flag = false;
                    cell.question = true;
                } else if (!cell.flag && cell.question) {
                    cell.question = false;
                }
            }
        }
        repaint();
    }

    private void Button1Pressed(int x, int y) {           //левая кнопка нажата
        for (Cell cell : cells) {
            if (cell.poly.contains(x, y) && !cell.isOpen) {
                cell.color = new Color(0, 0, 0, 0);
                cell.isOpen = true;
                if (firstDepression) {     //если первое нажатие
                    firstDepression = false;
                    mining();
                    howMinesBeside();
                    repaint();
                }
                if (cell.minesBeside == 0) openBrothers(cell);
                if (cell.mine) {                        // открытие всех мин при проигрыше
                    for (int i = 0; i < cells.size() - 1; i++) {
                        if (cells.get(i).mine) {
                            cells.get(i).color = new Color(0, 0, 0, 0);
                            cells.get(i).isOpen = true;
                        }
                    }
                    gameFailed = true;
                }
            }
        }
        repaint();
    }

    private void openBrothers(Cell cell) {             //открытие соседних клеток, если данная клетка пустая
        if (cell.eastBrother != null && cell.eastBrother.minesBeside == 0 && !cell.eastBrother.isOpen) {
            cell.eastBrother.isOpen = true;
            cell.eastBrother.color = new Color(0, 0, 0, 0);
            openBrothers(cell.eastBrother);
        }
        if (cell.eastBrother != null && !cell.eastBrother.isOpen && !cell.eastBrother.mine) {
            cell.eastBrother.isOpen = true;
            cell.eastBrother.color = new Color(0, 0, 0, 0);
        }
        if (cell.southeastBrother != null && cell.southeastBrother.minesBeside == 0 && !cell.southeastBrother.isOpen) {
            cell.southeastBrother.isOpen = true;
            cell.southeastBrother.color = new Color(0, 0, 0, 0);
            openBrothers(cell.southeastBrother);
        }
        if (cell.southeastBrother != null && !cell.southeastBrother.mine && !cell.southeastBrother.isOpen) {
            cell.southeastBrother.isOpen = true;
            cell.southeastBrother.color = new Color(0, 0, 0, 0);
        }
        if (cell.southwesternBrother != null && cell.southwesternBrother.minesBeside == 0 && !cell.southwesternBrother.isOpen) {
            cell.southwesternBrother.isOpen = true;
            cell.southwesternBrother.color = new Color(0, 0, 0, 0);
            openBrothers(cell.southwesternBrother);
        }
        if (cell.southwesternBrother != null && !cell.southwesternBrother.mine && !cell.southwesternBrother.isOpen) {
            cell.southwesternBrother.isOpen = true;
            cell.southwesternBrother.color = new Color(0, 0, 0, 0);
        }
        if (cell.westBrother != null && cell.westBrother.minesBeside == 0 && !cell.westBrother.isOpen) {
            cell.westBrother.isOpen = true;
            cell.westBrother.color = new Color(0, 0, 0, 0);
            openBrothers(cell.westBrother);
        }
        if (cell.westBrother != null && !cell.westBrother.mine && !cell.westBrother.isOpen) {
            cell.westBrother.isOpen = true;
            cell.westBrother.color = new Color(0, 0, 0, 0);
        }
        if (cell.northwesternBrother != null && cell.northwesternBrother.minesBeside == 0 && !cell.northwesternBrother.isOpen) {
            cell.northwesternBrother.isOpen = true;
            cell.northwesternBrother.color = new Color(0, 0, 0, 0);
            openBrothers(cell.northwesternBrother);
        }
        if (cell.northwesternBrother != null && !cell.northwesternBrother.mine && !cell.northwesternBrother.isOpen) {
            cell.northwesternBrother.isOpen = true;
            cell.northwesternBrother.color = new Color(0, 0, 0, 0);
        }
        if (cell.northeasternBrother != null && cell.northeasternBrother.minesBeside == 0 && !cell.northeasternBrother.isOpen) {
            cell.northeasternBrother.isOpen = true;
            cell.northeasternBrother.color = new Color(0, 0, 0, 0);
            openBrothers(cell.northeasternBrother);
        }
        if (cell.northeasternBrother != null && !cell.northeasternBrother.mine && !cell.northeasternBrother.isOpen) {
            cell.northeasternBrother.isOpen = true;
            cell.northeasternBrother.color = new Color(0, 0, 0, 0);
        }

    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int totalOpenCells = 0;     // открытые клетки

        for (Cell cell : cells) {
            g.setColor(cell.color);
            g.fillPolygon(cell.poly);
            g.setColor(Color.BLACK);
            g.drawPolygon(cell.poly);

            if (cell.isOpen) totalOpenCells++;

            if (cell.isOpen && !cell.mine) {
                if (cell.minesBeside == 1) g.drawImage(oneImg, cell.xpoints[0] + 12, cell.ypoints[0], 20, 20, null);
                else if (cell.minesBeside == 2)
                    g.drawImage(twoImg, cell.xpoints[0] + 12, cell.ypoints[0], 20, 20, null);
                else if (cell.minesBeside == 3)
                    g.drawImage(threeImg, cell.xpoints[0] + 12, cell.ypoints[0], 20, 20, null);
                else if (cell.minesBeside == 4)
                    g.drawImage(fourImg, cell.xpoints[0] + 12, cell.ypoints[0], 20, 20, null);
                else if (cell.minesBeside == 5)
                    g.drawImage(fiveImg, cell.xpoints[0] + 12, cell.ypoints[0], 20, 20, null);
                else if (cell.minesBeside == 6)
                    g.drawImage(sixImg, cell.xpoints[0] + 12, cell.ypoints[0], 20, 20, null);
            }

            if (cell.mine && cell.isOpen) g.drawImage(mineImg, cell.xpoints[0] + 12, cell.ypoints[0], 20, 20, null);

            if (cell.flag && !cell.isOpen) g.drawImage(flagImg, cell.xpoints[0] + 12, cell.ypoints[0], 20, 20, null);
            else if (cell.question && !cell.isOpen)
                g.drawImage(questionImg, cell.xpoints[0] + 12, cell.ypoints[0], 20, 20, null);

        }

        if (gameFailed) {
            JFrame fail = new FailWindow();
            fail.setVisible(true);
        }

        if (totalOpenCells == Settings.getMustBeOpen()) {
            JFrame greetingWindow = new GreetingWindow();
            greetingWindow.setVisible(true);
        }

    }
}