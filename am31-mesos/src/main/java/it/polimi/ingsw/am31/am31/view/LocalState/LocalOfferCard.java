package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;

public class LocalOfferCard {
    public LocalOfferCard(OfferCard og) {
        this.offerCardId = og.getOfferCardId();
        if (!og.isFree())
            this.player = new LocalPlayerState(og.getPlayer().getNickname(), og.getPlayer().getColor());
        else this.player = null;
        //as of now, this class contains a clone of an existing player
        //but not an actual reference to that local player
        //maybe add in the future? if needed?
    }
    //will add other attributes if necessary
    private String offerCardId;
    private LocalPlayerState player;

    public LocalPlayerState getPlayer() {
        return player;
    }
    public String getOfferCardId() {
        return offerCardId;
    }
}
