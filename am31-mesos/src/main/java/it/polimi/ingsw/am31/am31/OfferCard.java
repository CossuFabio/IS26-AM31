package it.polimi.ingsw.am31.am31;

public class OfferCard {
    private final int food;
    private final int drawFromUpper;
    private final int drawFromUnder;
    private Player player;

    public OfferCard(int food, int drawFromUnder, int drawFromUpper){
        this.food = food;
        this.drawFromUnder = drawFromUnder;
        this.drawFromUpper = drawFromUpper;
        this.player = null;
    }

    public int getDrawFromUnder() {
        return drawFromUnder;
    }

    public int getDrawFromUpper() {
        return drawFromUpper;
    }

    public int getFood() {
        return food;
    }

    public boolean isFree() {
        return this.player == null;
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void free() {
        this.player = null;
    }

}
