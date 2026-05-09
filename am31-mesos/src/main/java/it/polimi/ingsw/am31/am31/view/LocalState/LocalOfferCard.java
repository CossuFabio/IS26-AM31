package it.polimi.ingsw.am31.am31.view.LocalState;

public class LocalOfferCard {

    //will add other attributes if necessary
    private final String offerCardId;
    private final String player;
    private final int food;
    private final int drawFromUpper;
    private final int drawFromUnder;
    private final boolean isFree;

    public LocalOfferCard(String offerCardId, String player, boolean isFree, int food, int drawFromUpper, int drawFromUnder) {
        this.offerCardId = offerCardId;
        this.isFree = isFree;
        this.player = isFree ? null : player;
        this.food = food;
        this.drawFromUnder = drawFromUnder;
        this.drawFromUpper = drawFromUpper;
    }


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
        return isFree;
    }
}
