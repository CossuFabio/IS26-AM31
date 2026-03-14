package it.polimi.ingsw.am31.am31.handlers.onDraw;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.cards.Card;

public class InventorAdditionalFoodDecorator extends CardDrawHandlerDecorator{

    public InventorAdditionalFoodDecorator(IDrawHandler wrappedHandler){ super(wrappedHandler); }

    @Override
    //TO-DO: implement this
    public void handleDraw(Player player, Card card) {
        // Idea:
        // IconEnum icon = card.getIcon();
        // if(icon == EMPTY) return;
        // if(player.getIcons().contains(icon) player.setFood(player.getFood() + bonus));
        // Must decide how to handle the getIcons()
        wrappedHandler.handleDraw(player, card);
    }
}
