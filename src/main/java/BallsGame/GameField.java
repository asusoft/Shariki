package BallsGame;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class GameField {
    public GameField(){
        createCells();
    }

    /* --------------------- Size --------------------------- */
    private final int height = 20;
    private final int width = 10;
    private int score;

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    //Get width
    public int Width() {
        return width;
    }

    //Get height
    public int Height()
    {
        return height;
    }

    /* ---------------------- Balls -------------------- */

    private ArrayList <Ball> balls = new ArrayList <Ball> (); //all balls on field

    public ArrayList<Ball> balls() {
        return balls;
    }

    /* ---------------------- Cell storage -------------------- */
    private Map<CellPosition, Cell> cells = new HashMap<CellPosition, Cell>();

    public Cell getCell(int column, int row)
    {
        CellPosition pos = new CellPosition(column, row);

        return cells.get(pos);
    }

    //Create cells
    private void createCells()
    {
        for (int row = 0; row < height; ++row)
        {
            for (int column = 0; column < width; ++column)
            {
                CellPosition pos = new CellPosition(column, row);

                Cell cell = new Cell(pos);

                cells.put(cell.CurrentPosition(), cell);

                if (row > 0)
                {
                    Cell neighbour = getCell(column, row - 1);

                    cell.setNeighbor(neighbour, Direction.NORTH);
                }

                if (column > 0)
                {
                    Cell neighbour = getCell(column - 1, row);

                    cell.setNeighbor(neighbour, Direction.WEST);
                }
            }
        }
    }

    public boolean fillCells () {
        for (int row = height-5; row < height; row++) {
            createLine (row);
        }
        return true;
    }

    public boolean createLine (int row) {

        Random random = new Random();
        Color colors[] = { Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.MAGENTA };

        for(int column = 0; column < width; column++) {

            int colorSelector = random.nextInt(colors.length);

            Cell cell = getCell(column, row);

            if(cell.isEmpty()){
                Ball ball = new Ball(colors[colorSelector]);
                cell.setBall(ball);
                balls.add(ball);
            } else {
                return false;
            }
        }

        return true;
    }

    public boolean moveBallsUp(){
        for(Ball ball : balls()){
            Cell northNeighbor = ball.getCurrentCell().getNeighbor(Direction.NORTH);
            if(( northNeighbor != null) && (northNeighbor.isEmpty())) {
                ball.move(Direction.NORTH);
            }
            else {
                return false;
            }
        }
        return true;
    }

    public abstract boolean createNewBottomLine();

    /* --------------------------- Ball set ------------------------------ */

    protected void createBallSet(Ball ball, @NotNull ArrayList <Ball> group){
        Ball neighbor;

        group.add(ball);

        if(ball.getCurrentCell().getNeighbor(Direction.EAST) != null){
            neighbor = ball.getCurrentCell().getNeighbor(Direction.EAST).getBall();
            if (neighbor != null && neighbor.getColor().equals(ball.getColor()) && !group.contains(neighbor))
                createBallSet(neighbor, group);
        }

        if(ball.getCurrentCell().getNeighbor(Direction.WEST) != null){
            neighbor = ball.getCurrentCell().getNeighbor(Direction.WEST).getBall();
            if (neighbor != null && neighbor.getColor().equals(ball.getColor()) && !group.contains(neighbor))
                createBallSet(neighbor, group);
        }

        if(ball.getCurrentCell().getNeighbor(Direction.NORTH) != null){
            neighbor = ball.getCurrentCell().getNeighbor(Direction.NORTH).getBall();
            if (neighbor != null && neighbor.getColor().equals(ball.getColor()) && !group.contains(neighbor))
                createBallSet(neighbor, group);
        }

        if(ball.getCurrentCell().getNeighbor(Direction.SOUTH) != null){
            neighbor = ball.getCurrentCell().getNeighbor(Direction.SOUTH).getBall();
            if (neighbor != null && neighbor.getColor().equals(ball.getColor()) && !group.contains(neighbor))
                createBallSet(neighbor, group);
        }
    }

    public ArrayList <Ball> getBallSet (Ball ball) {
        ArrayList <Ball> set = new ArrayList<>();
        createBallSet(ball, set);

        if (set.size() >= 3) {
            return set;
        }
        return null;
    }

    public boolean deleteBallSet (ArrayList <Ball> set) {
        if (set != null){
            for (Ball ball : set){
                ball.getCurrentCell().removeBall();
            }
            balls.removeAll(set);
            setScore(score + set.size()*3);
            return true;
        }
        return false;
    }

    public void takeBallsDown(){
        for(int Y = Height()-1; Y >= 0; --Y){
            for(int X = 0; X < Width(); ++X){
                Cell cell = getCell(X, Y);
                if(cell.isEmpty()){
                    findBallForCell(cell);
                }
            }
        }
    }

    private boolean findBallForCell(@NotNull Cell cell){
        for(int Y = cell.CurrentPosition().Y(); Y >= 0; --Y){
            Cell otherCell = getCell(cell.CurrentPosition().X(), Y);
            if(!otherCell.isEmpty()){
                Ball ball = otherCell.getBall();
                otherCell.removeBall();
                cell.setBall(ball);
                return true;
            }
        }
        return false;
    }

    public boolean isGameOver(){
        int firstRow = 0;
        for (int column = 0; column < width; column++) {
            Cell cell = getCell(column, firstRow);
            if (!cell.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}