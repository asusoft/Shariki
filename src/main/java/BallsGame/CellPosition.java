package BallsGame;

public class CellPosition {
    private int x;

    private int y;

    CellPosition(int x, int y)
    {
        if (x < 0 || y < 0)
        {
            throw new IllegalArgumentException();
        }

        this.x = x;
        this.y = y;
    }

    public int X(){
        return x;
    }


    public int Y(){
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CellPosition))
        {
            return false;
        }

        CellPosition pos = (CellPosition)obj;

        return (pos.x == this.x && pos.y == this.y);
    }

    @Override
    public int hashCode() {
        return y * 100 + x;
    }
}
