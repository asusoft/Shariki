package BallsGame;

public class FieldWithoutVerifiedChains extends GameField{

    @Override
    public boolean createNewBottomLine(){
        this.createLine(Height() -1);
        return true;
    }
}
