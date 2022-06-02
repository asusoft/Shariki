package BallsGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class FieldWithVerifiedChains extends GameField{
    private final int MAX_BALLS_IN_CHAIN = 5;

    @Override
    public boolean createNewBottomLine(){
        Random random = new Random();
        Color colors[] = { Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.MAGENTA };

        for(int column = 0; column < Width(); column++){
            Cell cell = getCell(column, this.Height() -1);


            int colorSelector = random.nextInt(colors.length);
            int newColorSelector = 0;

            if(cell.isEmpty()){
                Ball ball = new Ball(colors[colorSelector]);

                if (!ballCanBeSetToCell(cell, ball)) {
                    if (colorSelector < 1) {
                        newColorSelector = colorSelector + 1;
                    }
                    if (colorSelector == 4) {
                        newColorSelector = colorSelector - 1;
                    }
                    ball.setColor(colors[newColorSelector]);
                }
                cell.setBall(ball);
                this.balls().add(ball);
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean ballCanBeSetToCell(Cell cell, Ball ball){
        Cell westNeighborCell = cell.getNeighbor(Direction.WEST);
        Cell northNeighborCell = cell.getNeighbor(Direction.NORTH);

        Ball westNeighborBall;
        Ball northNeighborBall;


        ArrayList<Ball> westBallSet = new ArrayList<>();
        ArrayList<Ball> northBallSet = new ArrayList<>();


        if((westNeighborCell != null) && (!westNeighborCell.isEmpty())){
            westNeighborBall = westNeighborCell.getBall();
            if(ball.getColor() == westNeighborBall.getColor())
            {
                this.createBallSet(westNeighborBall, westBallSet);
            }
        }
        if((northNeighborCell != null) && (!northNeighborCell.isEmpty())){
            northNeighborBall = northNeighborCell.getBall();
            if(ball.getColor() == northNeighborBall.getColor())
            {
                this.createBallSet(northNeighborBall, northBallSet);
            }
        }

        if((westBallSet != null && westBallSet.size() >= MAX_BALLS_IN_CHAIN) || (northBallSet != null && northBallSet.size() >= MAX_BALLS_IN_CHAIN)){
            return false;
        }
        return true;
    }
}
