package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

public class GeneralAdditionalFoodDecorator extends CardDrawHandlerDecorator{

    private int farmersSet;
    private int buildersSet;
    private int inventorsSet;
    private int artistsSet;
    private int shamansSet;
    private int huntersSet;



    public GeneralAdditionalFoodDecorator(IDrawHandler wrappedHandler){

        super(wrappedHandler);
        farmersSet = 0;
        buildersSet = 0;
        inventorsSet = 0;
        artistsSet = 0;
        shamansSet = 0;
        huntersSet = 0;

    }

    @Override
    //Idea: keep counter for all types, increment by one the type using
    //visitor pattern to increment the correct type
    //check if all are zeroes: if so, give bonus food then decrement all counter by one (or to zero), else do nothing

    public void handleDraw(Player player, Card card) {
        CountVisitor visitor = new CountVisitor();
        try{
            ((CharacterCard)card).acceptVisit(visitor);
        }catch(Exception e){
            //Do nothing
        }

        //Only the corresponding type will be increased by one, the others will keep the same value
        farmersSet += visitor.getFarmers();
        buildersSet += visitor.getBuilders();
        inventorsSet += visitor.getInventors();
        artistsSet += visitor.getArtists();
        shamansSet += visitor.getShamans();
        huntersSet += visitor.getHunters();

        //A set has been completed => bonus food reward
        if(farmersSet > 0 && buildersSet >0 && inventorsSet >0 && artistsSet >0 && shamansSet > 0 && huntersSet >0){
            player.editFood(5);
            farmersSet--;
            buildersSet--;
            inventorsSet--;
            artistsSet--;
            shamansSet--;
            huntersSet--;
        }



        wrappedHandler.handleDraw(player, card);
    }
}
