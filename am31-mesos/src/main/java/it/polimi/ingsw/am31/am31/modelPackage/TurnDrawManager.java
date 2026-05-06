package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;

public class TurnDrawManager {

    private int drawFromUpper = 0;
    private int drawFromLower = 0;

    private int drawFromUpperThisTurn = 0;
    private int drawFromLowerThisTurn = 0;

    private final Board gameBoard;

    public TurnDrawManager(Board board){
        this.gameBoard = board;
    }

    public void setUp(int drawFromUpper, int drawFromLower){

        this.drawFromUpper = drawFromUpper;
        this.drawFromLower = drawFromLower;

        drawFromUpperThisTurn = 0;
        drawFromLowerThisTurn = 0;

    }

    public void drawUpper(){
        drawFromUpperThisTurn++;
    }


    public void drawLower(){
        drawFromLowerThisTurn++;
    }

    public boolean canDrawFromUpper(){
        return drawFromUpperThisTurn < drawFromUpper;
    }


    public boolean canDrawFromLower(){
        return drawFromLowerThisTurn < drawFromLower;
    }

    public boolean hasFinishedDrawing(){
        boolean hasFinishedUpper = (drawFromUpperThisTurn >= drawFromUpper) || !gameBoard.upperLineHasPickable();
        boolean hasFinishedLower = (drawFromLowerThisTurn >= drawFromLower) || !gameBoard.underLineHasPickable();

        return hasFinishedUpper && hasFinishedLower;
    }

    //Methods invoked (indirectly) from the Controller that allow the Player to skip its draw phase.
    //Security checks are performed controller-side
    public void skipUpper() { drawFromUpperThisTurn = drawFromUpper; }
    public void skipLower() { drawFromLowerThisTurn = drawFromLower; }


}
