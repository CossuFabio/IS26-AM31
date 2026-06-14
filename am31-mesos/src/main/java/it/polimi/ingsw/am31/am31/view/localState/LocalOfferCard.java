package it.polimi.ingsw.am31.am31.view.localState;

/**
 * Represents a local offer card on the game board.
 */
public class LocalOfferCard {

    //will add other attributes if necessary
    private final String offerCardId;
    /**
     * The nickname of the player who claimed this offer card, or null if it's free.
     */
    private final String player;
    private final int food;
    private final int drawFromUpper;
    private final int drawFromUnder;
    /**
     * True if the offer card is free (unclaimed), false otherwise.
     */
    private final boolean isFree;

    /**
     * Constructs a new LocalOfferCard.
     * @param offerCardId The unique identifier of the offer card.
     * @param player The nickname of the player who claimed this offer card.
     * @param isFree True if the offer card is free, false otherwise.
     * @param food The amount of food provided by this offer card.
     * @param drawFromUpper The number of cards to draw from the upper line.
     * @param drawFromUnder The number of cards to draw from the under line.
     */
    public LocalOfferCard(String offerCardId, String player, boolean isFree, int food, int drawFromUpper, int drawFromUnder) {
        this.offerCardId = offerCardId;
        this.isFree = isFree;
        this.player = isFree ? null : player;
        this.food = food;
        this.drawFromUnder = drawFromUnder;
        this.drawFromUpper = drawFromUpper;
    }

    /**
     * Returns the nickname of the player who claimed this offer card.
     * @return The player's nickname, or null if the card is free.
     */
    public String getPlayer() {
        return player;
    }
    /**
     * @return The offer card's ID.
     */
    public String getOfferCardId() {
        return offerCardId;
    }
    /**
     * @return The food value.
     */
    public int getFood(){return food;}
    /**
     * @return The number of cards to draw from the upper line.
     */
    public int getDrawFromUpper(){return drawFromUpper;}
    /**
     * @return The number of cards to draw from the under line.
     */
    public int getDrawFromUnder(){return drawFromUnder;}
    /**
     * Checks if the offer card is free (unclaimed).
     * @return True if the card is free, false otherwise.
     */
    public boolean isFree(){
        return isFree;
    }
}
