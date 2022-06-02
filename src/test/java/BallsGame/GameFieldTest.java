package BallsGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameFieldTest {
    final int STANDARD_FIELD_HEIGHT = 20;
    final int STANDARD_FIELD_WIDTH = 10;

    @Test
    void initialization(){
        GameField field = new FieldWithVerifiedChains();

        Assertions.assertNotNull(field.getCell(0, 0));
        Assertions.assertNotNull(field.getCell(7, 16));
        Assertions.assertNotNull(field.getCell(9, 19));

        Assertions.assertNull(field.getCell(11, 21));
    }
    @Test
    void getNeighborTest(){

        GameField field = new FieldWithVerifiedChains();

        Cell centralCell = field.getCell(1, 1);
        Cell eastNeighbor = field.getCell(2, 1);
        Cell westNeighbor = field.getCell(0, 1);
        Cell northNeighbor = field.getCell(1, 0);
        Cell southNeighbor = field.getCell(1, 2);

        Assertions.assertEquals(eastNeighbor, centralCell.getNeighbor(Direction.EAST));
        Assertions.assertEquals(westNeighbor, centralCell.getNeighbor(Direction.WEST));
        Assertions.assertEquals(northNeighbor, centralCell.getNeighbor(Direction.NORTH));
        Assertions.assertEquals(southNeighbor, centralCell.getNeighbor(Direction.SOUTH));

    }

    @Test
    void getNeighborNull(){

        GameField field = new FieldWithVerifiedChains();

        Cell centralCell = field.getCell(0, 0);

        Assertions.assertNull(centralCell.getNeighbor(Direction.NORTH));
    }

    @Test
    void createLineTest(){
        GameField field = new FieldWithVerifiedChains();
        int row = 4;

        Assertions.assertTrue(field.createLine(row));

        for(int x = 0; x < field.Width(); ++x){
            Assertions.assertFalse(field.getCell(x, row).isEmpty());
        }

    }

    @Test
    void createLineNonEmptyRow(){
        GameField field = new FieldWithVerifiedChains();
        field.fillCells();
        int row = field.Height() - 1;

        Assertions.assertFalse(field.createLine(row));
    }

    @Test
    void getBallSetTest(){
        GameField field = new FieldWithVerifiedChains();


        Cell targetCell = field.getCell(6,17);
        Cell newCell1 = targetCell.getNeighbor(Direction.NORTH);
        Cell newCell2 = newCell1.getNeighbor(Direction.WEST);
        Cell newCell3 = targetCell.getNeighbor(Direction.EAST);
        Cell newCell4 = newCell3.getNeighbor(Direction.SOUTH);

        Cell newCell5 = targetCell.getNeighbor(Direction.WEST);

        Ball targetBall = new Ball(Color.blue);
        Ball newBall1 = new Ball(Color.blue);
        Ball newBall2 = new Ball(Color.blue);
        Ball newBall3 = new Ball(Color.blue);
        Ball newBall4 = new Ball(Color.blue);

        Ball newBall5 = new Ball(Color.red);


        targetCell.setBall(targetBall);
        newCell1.setBall(newBall1);
        newCell2.setBall(newBall2);
        newCell3.setBall(newBall3);
        newCell4.setBall(newBall4);
        newCell5.setBall(newBall5);

        ArrayList <Ball> set = field.getBallSet(targetBall);

        Assertions.assertTrue(set.contains(targetBall));
        Assertions.assertTrue(set.contains(newBall1));
        Assertions.assertTrue(set.contains(newBall2));
        Assertions.assertTrue(set.contains(newBall3));
        Assertions.assertTrue(set.contains(newBall4));

        Assertions.assertFalse(set.contains(newBall5));
    }

    @Test
    void getBallSetNull(){
        GameField field = new FieldWithVerifiedChains();

        Cell targetCell = field.getCell(6,17);
        Cell newCell1 = targetCell.getNeighbor(Direction.NORTH);
        Cell newCell2 = newCell1.getNeighbor(Direction.WEST);
        Cell newCell3 = targetCell.getNeighbor(Direction.EAST);
        Cell newCell4 = newCell3.getNeighbor(Direction.SOUTH);

        Cell newCell5 = targetCell.getNeighbor(Direction.WEST);

        Ball targetBall = new Ball(Color.blue);
        Ball newBall1 = new Ball(Color.red);
        Ball newBall2 = new Ball(Color.green);
        Ball newBall3 = new Ball(Color.white);
        Ball newBall4 = new Ball(Color.yellow);
        Ball newBall5 = new Ball(Color.blue);

        targetCell.setBall(targetBall);
        newCell1.setBall(newBall1);
        newCell2.setBall(newBall2);
        newCell3.setBall(newBall3);
        newCell4.setBall(newBall4);
        newCell5.setBall(newBall5);

        ArrayList <Ball> set = field.getBallSet(targetBall);

        Assertions.assertNull(set);
    }

    @Test
    void fillCellsTest(){
        GameField field = new FieldWithVerifiedChains();

        Assertions.assertEquals(0, field.balls().size());

        field.fillCells();

        Assertions.assertEquals(50, field.balls().size());

        for(int row = 15; row < STANDARD_FIELD_HEIGHT; ++row){
            for(int column = 0; column < STANDARD_FIELD_WIDTH; ++column){
                Cell cell = field.getCell(column, row);
                Assertions.assertFalse(cell.isEmpty());
            }
        }
    }

    @Test
    void createNewBottomLineTest(){
        GameField field = new FieldWithVerifiedChains();

        field.fillCells();

        Assertions.assertEquals(50, field.balls().size());

        for(int column = 0; column < STANDARD_FIELD_WIDTH; ++column){
            Cell cell = field.getCell(column, 14);
            Assertions.assertTrue(cell.isEmpty());
        }

        field.moveBallsUp();
        field.createNewBottomLine();

        Assertions.assertEquals(60, field.balls().size());

        for(int column = 0; column < STANDARD_FIELD_WIDTH; ++column){
            Cell cell = field.getCell(column, 14);
            Assertions.assertFalse(cell.isEmpty());
        }
    }

    @Test
    void isGameOverTrue(){
        GameField field = new FieldWithVerifiedChains();
        Random random = new Random();
        Color colors[] = { Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.WHITE };
        int colorSelector = random.nextInt(colors.length);


        for(int row = 0; row < STANDARD_FIELD_HEIGHT; ++row){
            for(int column = 0; column < STANDARD_FIELD_WIDTH; ++column){
                Cell cell = field.getCell(column, row);

                Ball ball = new Ball(colors[colorSelector]);

                cell.setBall(ball);
                field.balls().add(ball);
            }
        }

        Assertions.assertTrue(field.isGameOver());
    }

    @Test
    void isGameOverFalse(){
        GameField field = new FieldWithVerifiedChains();
        field.fillCells();

        Assertions.assertFalse(field.isGameOver());
    }

    @Test
    void deleteBallSetTest(){
        GameField field = new FieldWithVerifiedChains();
        for(int row = 15; row < STANDARD_FIELD_HEIGHT; ++row){
            for(int column = 0; column < STANDARD_FIELD_WIDTH; ++column){
                Cell cell = field.getCell(column, row);
                Ball ball = new Ball(Color.WHITE);
                cell.setBall(ball);
                field.balls().add(ball);
            }
        }

        Cell targetCell = field.getCell(6,17);
        Cell newCell1 = targetCell.getNeighbor(Direction.NORTH);
        Cell newCell2 = newCell1.getNeighbor(Direction.WEST);
        Cell newCell3 = targetCell.getNeighbor(Direction.EAST);
        Cell newCell4 = newCell3.getNeighbor(Direction.SOUTH);

        field.balls().remove(targetCell.getBall());
        field.balls().remove(newCell1.getBall());
        field.balls().remove(newCell2.getBall());
        field.balls().remove(newCell3.getBall());
        field.balls().remove(newCell4.getBall());

        targetCell.removeBall();
        newCell1.removeBall();
        newCell2.removeBall();
        newCell3.removeBall();
        newCell4.removeBall();

        Ball targetBall = new Ball(Color.blue);
        Ball newBall1 = new Ball(Color.blue);
        Ball newBall2 = new Ball(Color.blue);
        Ball newBall3 = new Ball(Color.blue);
        Ball newBall4 = new Ball(Color.blue);

        targetCell.setBall(targetBall);
        newCell1.setBall(newBall1);
        newCell2.setBall(newBall2);
        newCell3.setBall(newBall3);
        newCell4.setBall(newBall4);

        field.balls().add(targetBall);
        field.balls().add(newBall1);
        field.balls().add(newBall2);
        field.balls().add(newBall3);
        field.balls().add(newBall4);

        ArrayList <Ball> set = field.getBallSet(targetBall);

        Assertions.assertEquals(5, set.size());
        Assertions.assertTrue(field.deleteBallSet(set));

        for(Ball ball : set){
            Assertions.assertNull(ball.getCurrentCell());
        }
    }

    @Test
    void takeBallsDownTest(){
        GameField field = new FieldWithVerifiedChains();
        field.fillCells();

        Cell targetCell = field.getCell(6,17);
        Cell newCell1 = targetCell.getNeighbor(Direction.NORTH);
        Cell newCell2 = newCell1.getNeighbor(Direction.WEST);
        Cell newCell3 = targetCell.getNeighbor(Direction.EAST);
        Cell newCell4 = newCell3.getNeighbor(Direction.SOUTH);

        targetCell.removeBall();
        newCell1.removeBall();
        newCell2.removeBall();
        newCell3.removeBall();
        newCell4.removeBall();

        Ball fallingBall1 = field.getCell(5,15).getBall();
        Ball fallingBall2 = field.getCell(6,15).getBall();
        Ball fallingBall3 = field.getCell(7,15).getBall();
        Ball fallingBall4 = field.getCell(7,16).getBall();

        field.takeBallsDown();

        Assertions.assertEquals(field.getCell(5,16), fallingBall1.getCurrentCell());
        Assertions.assertEquals(field.getCell(6,17), fallingBall2.getCurrentCell());
        Assertions.assertEquals(field.getCell(7,17), fallingBall3.getCurrentCell());
        Assertions.assertEquals(field.getCell(7,18), fallingBall4.getCurrentCell());
    }
}
