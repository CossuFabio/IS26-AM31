package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;

public class LocalOfferCard {
    public LocalOfferCard(OfferCard og) {
        this.offerCardId = og.getOfferCardId();
        if (!og.isFree())
            this.player = new LocalPlayerState(og.getPlayer().getNickname(), og.getPlayer().getColor());
        else this.player = new LocalPlayerState("EMPTY",null);
        food = og.getFood();
        drawFromUnder = og.getDrawFromUnder();
        drawFromUpper = og.getDrawFromUpper();
    }
    //will add other attributes if necessary
    private final String offerCardId;
    private final LocalPlayerState player;
    private final int food;
    private final int drawFromUpper;
    private final int drawFromUnder;

    public LocalPlayerState getPlayer() {
        return player;
    }
    public String getOfferCardId() {
        return offerCardId;
    }
    public int getFood(){return food;}
    public int getDrawFromUpper(){return drawFromUpper;}
    public int getDrawFromUnder(){return drawFromUnder;}
}
