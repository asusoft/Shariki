package UI;

import BallsGame.Ball;
import BallsGame.Cell;
import BallsGame.CellPosition;
import BallsGame.GameField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;


public class CellWidget extends JPanel {
    private static final int CELL_SIZE = 30;
    private static final int FONT_HEIGHT = 15;
    private int panelWidth;
    private int panelHeight;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color GRID_COLOR = Color.LIGHT_GRAY;

    private GameField field;

    public int getCellSize(){return CELL_SIZE;}

    public CellWidget(GameField field){

        this.field = field;

        this.panelWidth = (int) (CELL_SIZE * field.Width());
        this.panelHeight = (int) (CELL_SIZE * field.Height());
        
        setFocusable(true);
        setVisible(true);
        setPreferredSize(new Dimension(panelWidth,panelHeight));
        setBackground (BACKGROUND_COLOR);
    }

    private void drawGrid (@NotNull Graphics g) {

        g.setColor(GRID_COLOR);

        g.drawLine(0,0,panelWidth,0);
        g.drawLine(0, 0, 0,panelHeight);
        g.drawLine(panelWidth,0,panelWidth,panelHeight);
        g.drawLine(0,panelHeight,panelWidth,panelHeight);
        g.drawLine(panelWidth,0,panelWidth,panelHeight);

        for(int x = 1; x < this.field.Width(); x++){
            int i = CELL_SIZE * x;
            g.drawLine(i, 0, i, panelHeight);
        }

        for(int y = 1; y < field.Height(); y++){
            int i = CELL_SIZE * y;
            g.drawLine(0, i, panelWidth, i);
        }
    }

    private void drawBall (@NotNull Graphics g, @NotNull Ball ball, @NotNull Point lefTop) {
        g.setColor(ball.getColor());

        g.drawOval(lefTop.x + CELL_SIZE/8 + 2, lefTop.y + CELL_SIZE/4 + 2 * FONT_HEIGHT - 2, ball.getWidth(), ball.getHeight());
        g.fillOval(lefTop.x + CELL_SIZE/8 + 2, lefTop.y + CELL_SIZE/4 + 2 * FONT_HEIGHT - 2, ball.getWidth(), ball.getHeight());

        g.setColor(Color.BLACK);
    }

    private @NotNull Point leftTopCell (@NotNull CellPosition pos) {
        int left =CELL_SIZE * (pos.X());
        int top =CELL_SIZE * (pos.Y()-1);

        return new Point(left, top);
    }

    public void fillCellColor(@NotNull Graphics g, Cell cell, @NotNull Point lefTop){
        Color color = cell.getBackgroundColor();
        if(color == null){
            color = Color.WHITE;
        }
        g.setColor(color);
        g.drawRect(lefTop.x + CELL_SIZE/8, lefTop.y + CELL_SIZE/8 + 2 * FONT_HEIGHT - 2, CELL_SIZE -5, CELL_SIZE-3);
        g.fillRect(lefTop.x + CELL_SIZE/8, lefTop.y + CELL_SIZE/8 + 2 * FONT_HEIGHT - 2, CELL_SIZE -5, CELL_SIZE-3);

        g.setColor(Color.BLACK);

    }

    public void paintComponent (@NotNull Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        Point lefTop;

        for(int row = 0; row < field.Height(); row++){
            for(int column = 0; column < field.Width(); column++){
                Cell cell = field.getCell(column, row);
                CellPosition pos = cell.CurrentPosition();
                Ball ball = cell.getBall();
                lefTop = leftTopCell(pos);
                fillCellColor(g, cell, lefTop);
                if(ball != null){
                    drawBall(g, ball, lefTop);
                }
            }
        }

        if (field.isGameOver()) {
            for(Ball ball: field.balls()){
                ball.setColor(Color.LIGHT_GRAY);
            }
            repaint();
            Font font = new Font ("Showcard Gothic", Font.BOLD, 90);
            g.setFont(font);
            g.setColor(Color.RED);
            g.translate(0, 200);
            g.drawString("GAME", 28, 100);
            g.drawString("OVER!", 15, 180);

            Font font1 = new Font ("DialogInput", Font.BOLD, 25);
            g.setFont(font1);
            g.setColor(Color.blue);
            g.drawString("Your Score:", 5, 300);
            g.drawString(Integer.toString(field.getScore()), 180, 300);
        }
    }

}
