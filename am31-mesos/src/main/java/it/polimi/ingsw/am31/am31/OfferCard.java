package it.polimi.ingsw.am31.am31;




public class OfferCard {
    private int food;
    private int drawFromUpper;
    private int drawFromUnder;
    private Player player;

    public void OfferCard(int food, int drawFromUnder, int drawFromUpper){
        this.food = food;
        this.drawFromUnder = drawFromUnder;
        this.drawFromUpper = drawFromUpper;
        //this.player = NULL;
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

    public boolean isFree() {return true;}

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void free() {}

}
