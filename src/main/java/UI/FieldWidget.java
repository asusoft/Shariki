package UI;

import BallsGame.Ball;
import BallsGame.Cell;
import BallsGame.Game;
import BallsGame.GameField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FieldWidget extends JPanel{

    private CellWidget cellWidget;
    private Timer timer;
    private GameField gameField;
    private Game game = new Game();
    private static final int TIME_INTERVAL = 3000;
    private int panelWidth;
    private int panelHeight;

    public FieldWidget()
    {
        this.gameField = game.getGameField();
        this.cellWidget = new CellWidget(gameField);
        this.panelWidth = (int) (cellWidget.getCellSize() * gameField.Width() + 5);
        this.panelHeight = (int) (cellWidget.getCellSize() * gameField.Height() + 5);
        add(cellWidget);
        setVisible(true);
        setPreferredSize(new Dimension(panelWidth,panelHeight));
        timer = new Timer(TIME_INTERVAL, new actionTimeOut());
        timer.setRepeats(true);
        timer.start();

        cellWidget.addMouseListener(new mouseClickedAdapter());
    }

    private class mouseClickedAdapter extends MouseAdapter {

        public void mouseClicked (@NotNull MouseEvent event) {
            int column = event.getX()/cellWidget.getCellSize();
            int row = event.getY()/cellWidget.getCellSize();

            Cell cell = gameField.getCell(column, row);
            Ball ball = cell.getBall();

            if (ball != null)
            {
                game.ballIsSelected(ball);
                repaint();
            }
        }
    }

    class actionTimeOut implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameField.isGameOver()){
                game.timeOut();
                cellWidget.repaint();
            }
        }
    }
}
