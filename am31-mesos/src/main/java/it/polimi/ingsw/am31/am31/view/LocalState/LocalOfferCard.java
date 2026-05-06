package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferCardMessage;

public class LocalOfferCard {
    public LocalOfferCard(OfferCard og, OfferCardMessage message) {
        this.offerCardId = og.getOfferCardId();
        if (!message.isFree())
            this.player = message.getTotemPlayerNickname();
        else this.player = "FREE";
        food = og.getFood();
        drawFromUnder = og.getDrawFromUnder();
        drawFromUpper = og.getDrawFromUpper();
    }
    //will add other attributes if necessary
    private final String offerCardId;
    private final String player;
    private final int food;
    private final int drawFromUpper;
    private final int drawFromUnder;

    public String getPlayer() {
        return player;
    }
    public String getOfferCardId() {
        return offerCardId;
    }
    public int getFood(){return food;}
    public int getDrawFromUpper(){return drawFromUpper;}
    public int getDrawFromUnder(){return drawFromUnder;}
    public boolean isFree(){
        return player.equals("FREE");
    }
}
