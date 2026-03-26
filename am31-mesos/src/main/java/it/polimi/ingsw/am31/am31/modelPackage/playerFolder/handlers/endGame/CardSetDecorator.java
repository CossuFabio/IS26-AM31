package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

import java.util.Arrays;
import java.util.List;

public class CardSetDecorator extends EndGameHandlerDecorator {
    public CardSetDecorator(IEndGameHandler handler) { super(handler);}

    @Override
    public void handleEndGame(Player player) {

        //The number of completed set is given by the minimum number of cards of the same type in the tribe
        //Example: (2 Shaman, 3 Inventor, 6 Builders, 1 Farmer, 10 Hunters, 4 Artist) there is only one completed set given by the one farmer

        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        List<Integer> populationForType = Arrays.asList(visitor.getArtists()
                , visitor.getBuilders()
                , visitor.getFarmers(), visitor.getShamans(), visitor.getHunters(), visitor.getInventors());
        int minimum = populationForType.getFirst();
        for(int current : populationForType){
            if(current < minimum) minimum = current;
        }

        if(minimum != 0){ //Unnecessary but makes more readable
            player.editPrestigePoints(6 * minimum);
        }

        wrappedHandler.handleEndGame(player);
    }
}
