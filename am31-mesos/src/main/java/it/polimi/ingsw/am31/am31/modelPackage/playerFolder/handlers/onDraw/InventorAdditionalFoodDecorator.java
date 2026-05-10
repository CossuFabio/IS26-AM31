package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum;

import java.util.HashMap;
import java.util.HashSet;



public class InventorAdditionalFoodDecorator extends CardDrawHandlerDecorator{

    private final HashSet<IconEnum> icons;
    public static final int FOOD_BONUS = 3;

    //Need to load the tribe for the first time.
    private boolean isFirstDraw = true;


    public InventorAdditionalFoodDecorator(IDrawHandler wrappedHandler){
        super(wrappedHandler);
        icons = new HashSet<IconEnum>();
    }

    @Override
    public void handleDraw(Player player, Card card) {

        //if is first draw must add all the spare pairs, ignore the others
        if(isFirstDraw){

            HashMap<IconEnum, Integer> countersMap = new HashMap<>();
            player.getTribe().forEach(currentCard -> {

                if(currentCard.getIcon() == null || currentCard.getIcon() == IconEnum.EMPTY) return;

                if(countersMap.containsKey(currentCard.getIcon())){
                    Integer newValue = countersMap.get(currentCard.getIcon()) + 1;
                    countersMap.put(currentCard.getIcon(), newValue);
                }else{
                    countersMap.put(currentCard.getIcon(), 1);
                }
            });
            countersMap.forEach((icon, count) -> {
                if(count % 2 == 1) icons.add(icon);
            });

            isFirstDraw = false;
        }


        try{
            IconEnum currentIcon = ((CharacterCard) card).getIcon();
            if(currentIcon != IconEnum.EMPTY){
                if(icons.contains(currentIcon)) {
                    icons.remove(currentIcon);
                    player.editFood(FOOD_BONUS);
                }
                else icons.add(currentIcon);
            }
        }catch(Exception e){
            //It wasn't a character card => do nothing
        }
        wrappedHandler.handleDraw(player, card);
    }
}
