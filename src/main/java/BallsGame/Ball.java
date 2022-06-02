package BallsGame;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Ball {

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private int height = 20;
    private int width = 20;

    private Color ballColor;

    public Color getColor() {
        return this.ballColor;
    }

    public void setColor (Color color) {
        ballColor = color;
    }

    public void move(@NotNull Direction direction){
        if(!this.currentCell.getNeighbor(direction).isEmpty()){
            throw new IllegalArgumentException("Cannot move to non empty cell");
        }
        if(this.currentCell.getNeighbor(direction) == null){
            throw new NullPointerException("Cannot move to non existing cell");
        }

        Cell newCell = currentCell.getNeighbor(direction);
        this.removeFromCell();
        this.putInCell(newCell);
    }

    Ball(Color color) {
        setColor(color);
    }

    private void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    /* --------------------- Cell ------------------------------- */
    private Cell currentCell = null;

    // Get current cell
    public Cell getCurrentCell() {return currentCell;}

    //Set ball cell
    void putInCell(Cell targetCell)
    {
        if (targetCell == null || (targetCell.getBall() != null && targetCell.getBall() != this) || this.currentCell != null) {
            throw new IllegalArgumentException("Illegal current cell!");
        }

        setCurrentCell(targetCell);

        if (targetCell.getBall() == null) {
            targetCell.setBall(this);
        }

    }

    //Remove ball from cell
    void removeFromCell()
    {
        if(getCurrentCell() == null){
            throw new IllegalArgumentException("There is no cell to remove ball from");
        }

        Cell cell = currentCell;

        currentCell = null;

        if (cell.getBall() != null) {
            cell.removeBall();
        }
    }
}
