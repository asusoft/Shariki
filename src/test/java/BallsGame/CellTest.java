package BallsGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    final int STANDART_X_POSITION = 1;
    final int STANDART_Y_POSITION = 1;

    /*---------------------------------Neighbor cell test---------------------------------------------*/
    @Test
    public void getNeighbourTest() {
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));
        Cell northNeighbor = new Cell(new CellPosition(1, 0));

        cell.setNeighbor(northNeighbor, Direction.NORTH);

        Assertions.assertEquals(cell.getNeighbor(Direction.NORTH), northNeighbor);
    }

    @Test
    public void getNeighbourNull() {
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));

        Assertions.assertNull(cell.getNeighbor(Direction.NORTH));
    }

    @Test
    public void getNeighborWithoutDirection() {
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));

        Assertions.assertNull(cell.getNeighbor(null));
    }

    @Test
    public void setNeighbour() {
        Cell northNeighbour = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION - 1));

        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));

        cell.setNeighbor(northNeighbour, Direction.NORTH);

        Assertions.assertSame(cell.getNeighbor(Direction.NORTH), northNeighbour);
    }

    @Test
    public void trySetNeighbourWithEmptyNeighbour() {
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));

        assertThrows(IllegalStateException.class, () -> cell.setNeighbor(null, Direction.NORTH));
    }

    @Test
    public void setNeighborWithoutDirection() {
        Cell neighbour = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));

        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION - 1));

        assertThrows(IllegalStateException.class, () -> cell.setNeighbor(neighbour, null));
    }

    @Test
    public void setExistingNeighborAgain() {
        Cell northNeighbor = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));

        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION - 1));

        cell.setNeighbor(northNeighbor, Direction.NORTH);


        assertThrows(IllegalStateException.class, () -> cell.setNeighbor(northNeighbor, Direction.NORTH));
    }

    /*----------------------------------- Stored Ball Test ----------------------------------------------*/
    @Test
    void setBallTest(){
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));
        Ball ball = new Ball(Color.blue);

        cell.setBall(ball);

        assertEquals(ball, cell.getBall());
        assertEquals(cell, ball.getCurrentCell());
    }

    @Test
    void setNullBall(){
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));

        assertThrows(IllegalArgumentException.class, () -> cell.setBall(null));
        assertNull(cell.getBall());
    }

    @Test
    void setManyBallsToCell(){
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));
        Ball ball = new Ball(Color.red);
        Ball anotherBall = new Ball(Color.blue);

        cell.setBall(ball);

        assertThrows(IllegalArgumentException.class, () -> cell.setBall(anotherBall));
    }

    @Test
    void getBallTest(){
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));
        Ball ball = new Ball(Color.blue);

        cell.setBall(ball);

        assertEquals(ball, cell.getBall());
        assertEquals(cell, ball.getCurrentCell());
    }

    @Test
    void getBallNull(){
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));

        assertNull(cell.getBall());
    }

    @Test
    void removeBallTest(){
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));
        Ball ball = new Ball(Color.blue);

        cell.setBall(ball);

        assertEquals(ball, cell.getBall());
        assertEquals(cell, ball.getCurrentCell());

        cell.removeBall();

        assertNull(cell.getBall());
        assertNull(ball.getCurrentCell());
    }

    @Test
    void removeBallFromEmptyCell(){
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));

        assertThrows(IllegalArgumentException.class, cell::removeBall);

    }

    @Test
    void isEmptyTrue(){
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));

        assertTrue(cell.isEmpty());

    }

    @Test
    void isEmptyFalse(){
        Cell cell = new Cell(new CellPosition(STANDART_X_POSITION, STANDART_Y_POSITION));
        Ball ball = new Ball(Color.blue);

        cell.setBall(ball);

        assertFalse(cell.isEmpty());
    }
}
