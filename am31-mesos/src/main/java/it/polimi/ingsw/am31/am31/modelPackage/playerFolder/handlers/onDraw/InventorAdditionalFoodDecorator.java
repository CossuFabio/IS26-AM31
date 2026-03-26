package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum;

import java.util.HashSet;



public class InventorAdditionalFoodDecorator extends CardDrawHandlerDecorator{

    private final HashSet<IconEnum> icons;

    public InventorAdditionalFoodDecorator(IDrawHandler wrappedHandler){
        super(wrappedHandler);
        icons = new HashSet<IconEnum>();
    }

    @Override
    //TO-DO: implement this
    public void handleDraw(Player player, Card card) {
        try{
            IconEnum currentIcon = ((CharacterCard) card).getIcon();
            if(currentIcon != IconEnum.EMPTY){
                if(icons.contains(currentIcon)) player.editFood(3);
                else icons.add(currentIcon);
            }
        }catch(Exception e){
            //It wasn't a character card => do nothing
        }
        wrappedHandler.handleDraw(player, card);
    }
}
