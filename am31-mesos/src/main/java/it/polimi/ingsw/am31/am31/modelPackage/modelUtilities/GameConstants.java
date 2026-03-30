package it.polimi.ingsw.am31.am31.modelPackage.modelUtilities;

public class GameConstants {

    //First player gets 2, second and third get 3, last two get 4
    public static final int[] STARTING_FOOD = {2, 3, 3, 4, 4};

    //Used to set up board
    public static final int UPPER_LINE_EXTRA_CARDS = 4;
    public static final int LOWER_LINE_EXTRA_CARDS = 1;

    //Round number
    public static final int ROUNDS_NUMBER = 10;

    //Final events stats
    public static final int FINAL_EVENTS_ERA = 3;
    public static final int FINAL_SUSTAIN_MALUS = 3;
    public static final int FINAL_RITUAL_MALUS = 7;
    public static final int FINAL_RITUAL_BONUS = 15;

    public static int getEraOneBuildings(int nPlayers){
        if(nPlayers == 2)
            return 1;
        else
            return 2;
    }

    public static int getEraTwoBuildings(int nPlayers){
        if(nPlayers == 2 || nPlayers == 3)
            return 2;
        else
            return 3;
    }

    public static int getEraThreeBuildings(int nPlayers){
        if(nPlayers == 2)
            return 3;
        else if(nPlayers == 3 || nPlayers == 4)
            return 4;

        else return 5;
    }




}
