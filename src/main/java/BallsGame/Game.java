package BallsGame;

import java.util.ArrayList;

public class Game {

    private final GameField gameField;
    public int getScore() {
        return score;
    }
    private int score;
    public GameField getGameField() {
        return gameField;
    }

    public Game(){
        //gameField = new FieldWithoutVerifiedChains();
        gameField = new GameField();
        gameField.fillCells();
    }

    public void ballIsSelected(Ball ball){
        if (ball != null)
        {
            ArrayList<Ball> set = gameField.getBallSet(ball);
            ArrayList<Cell> dlCell = new ArrayList<>();
            if(set != null && set.size() >= 3){

                for(Ball balls:set){
                    Cell ballCell = balls.getCurrentCell();
                    dlCell.add(ballCell);
                }
                gameField.deleteBallSet(set);
                gameField.takeBallsDown();
            }
        }
    }

    public void timeOut(){
        if (!gameField.isGameOver()){
           gameField.createNewBottomLine();
            this.score = gameField.getScore();
        }
    }
}
