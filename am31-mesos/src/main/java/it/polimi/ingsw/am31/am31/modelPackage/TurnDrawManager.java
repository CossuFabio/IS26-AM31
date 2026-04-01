package it.polimi.ingsw.am31.am31.modelPackage;

public class TurnDrawManager {

    private int drawFromUpper = 0;
    private int drawFromLower = 0;

    private int drawFromUpperThisTurn = 0;
    private int drawFromLowerThisTurn = 0;

    public TurnDrawManager(){}

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
        return (drawFromUpper == drawFromUpperThisTurn && drawFromLower == drawFromLowerThisTurn);
    }

}
