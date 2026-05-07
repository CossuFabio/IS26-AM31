package it.polimi.ingsw.am31.am31.view.LocalState;

import java.util.HashMap;
import java.util.Map;

public class LocalCardDictionary {

    public static final Map<String, String> descriptions = new HashMap<String, String>();

    static {
        //all buildings with their description
        descriptions.put("bd1","Grants the player 5 food when completing a set of 6 Character cards of different types. The effect does not apply to eventual sets completed before obtaining this card");
        descriptions.put("bd2","Grants the player a discount of 1 food per Farmers in their tribe during Sustainment event");
        descriptions.put("bd3","Grants the player a discount of 1 food per Artist in their tribe during Sustainment event");
        descriptions.put("bd4","Grants the player immunity to malus during Ritual event");
        descriptions.put("bd5","Grants the player 1 additional food when placing totem on slot of OrderCard with a food bonus at the end of their turn");
        descriptions.put("bd6","Grants the player 3 food when obtaining two Inventors with the same icon. The effect does not appply to eventual couples obtained before this card");
        descriptions.put("bd7","Grants the player double prestige points when winning Ritual event without sharing victory with another player");
        descriptions.put("bd8","Grants the player 3 extra stars");
        descriptions.put("bd9","Grants the player a discount of 1 food per Inventor in their tribe during Sustainment event");
        descriptions.put("bd10","Grants the player 1 extra Food and Prestige Points per Hunter in their Tribe during Hunt Events");
        descriptions.put("bd11","Grants the player Double the Prestige Points granted by the Builder cards in their Tribe");
        descriptions.put("bd12","Grants the player 1 Food per Artist in their Tribe during Painting Events");
        descriptions.put("bd13","Grants the player 6 Prestige Points per set of Six Different Role Characters in your Tribe in EndGame");
        descriptions.put("bd14","Grants the player 3 Prestige Points per Hunter in their Tribe in EndGame");
        descriptions.put("bd15","Grants the player 4 Prestige Points per Farmer in their Tribe in EndGame");
        descriptions.put("bd16","Grants the player 4 Prestige Points per Shaman in their Tribe in EndGame");
        descriptions.put("bd17","Grants the player 4 Prestige Points per Builder in their Tribe in EndGame");
        descriptions.put("bd18","Grants the player 4 Prestige Points per Artist in their Tribe in EndGame");
        descriptions.put("bd19","Grants the player 2 Prestige Points per Inventor in their Tribe in EndGame");
        descriptions.put("bd20","Grants the player an extra draw from the UpperRow, at the end of everyone's the action phase");
        descriptions.put("bd21","Grants the player a flat 25 Prestige Points in EndGame");


    }
    public static String getDescription(String id){
        if (id==null || !descriptions.containsKey(id))
            return "";
        return descriptions.get(id);
    }
}
