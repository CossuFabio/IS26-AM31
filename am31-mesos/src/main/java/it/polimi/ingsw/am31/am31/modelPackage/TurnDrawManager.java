package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;

/**
 * Tracks how many cards the active player has drawn from each card line during the action or bonus draw phase.
 * Enforces the draw limits set by the player's chosen offer card.
 */
public class TurnDrawManager {

    private int drawFromUpper = 0;
    private int drawFromLower = 0;

    private int drawFromUpperThisTurn = 0;
    private int drawFromLowerThisTurn = 0;

    private final Board gameBoard;

    /**
     * @param board is used to check the status of card lines to avoid stale situations of empty card lines
     */
    public TurnDrawManager(Board board){
        this.gameBoard = board;
    }

    /**
     * Method called when the active player changes during the ACTION_PHASE or BONUS_DRAW_PHASE
     * @param drawFromUpper how many cards the player must draw from upper line
     * @param drawFromLower how many cards the player must draw from lower line
     */
    public void setUp(int drawFromUpper, int drawFromLower){

        this.drawFromUpper = drawFromUpper;
        this.drawFromLower = drawFromLower;

        drawFromUpperThisTurn = 0;
        drawFromLowerThisTurn = 0;

    }

    /**
     * Called when the player draws from upper line.
     * Updates the internal counter of draws from upper.
     */
    public void drawUpper(){
        drawFromUpperThisTurn++;
    }

    /**
     * Called when the player draws from lower line.
     * Updates the internal counter of draws from lower.
     */
    public void drawLower(){
        drawFromLowerThisTurn++;
    }

    /**
     * @return true if the player can draw from the upper line this turn
     */
    public boolean canDrawFromUpper(){
        return drawFromUpperThisTurn < drawFromUpper;
    }

    /**
     * @return true if the player can draw from the lower line this turn
     */
    public boolean canDrawFromLower(){
        return drawFromLowerThisTurn < drawFromLower;
    }

    /**
     * @return true if the player has no more mandatory draws or if there are no pickable cards.
     */
    public boolean hasFinishedDrawing(){
        boolean hasFinishedUpper = (drawFromUpperThisTurn >= drawFromUpper) || !gameBoard.upperLineHasPickable();
        boolean hasFinishedLower = (drawFromLowerThisTurn >= drawFromLower) || !gameBoard.underLineHasPickable();

        return hasFinishedUpper && hasFinishedLower;
    }


    /**
     * Skips all remaining draws from the upper line for the active player this turn. This method does not provide any security
     * check on the regularity of the skip request, this job is handled by the object that processes the skip request.
     */
    public void skipUpper() { drawFromUpperThisTurn = drawFromUpper; }

    /**
     * Skips all remaining draws from the lower line for the active player this turn. This method does not provide any security
     * check on the regularity of the skip request, this job is handled by the object that processes the skip request.
     */
    public void skipLower() { drawFromLowerThisTurn = drawFromLower; }


}
