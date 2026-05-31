package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;

/**
 * Abstract base class for character cards.
 * All type-specific getters (prestige points, discount, icon, etc.)
 * return 0 or a default value unless a concrete subclass overrides them.
 */
public abstract class CharacterCard extends Card implements IPickable {


    protected final int minPlayers;

    protected CharacterCard(String cardID, int era, int minPlayers) {
        super(cardID, era);
        this.minPlayers = minPlayers;
    }

    @Override
    public void onPick(Player player) {}

    @Override
    public int getMinPlayers(){return this.minPlayers; }

    /** @return food discount applied when purchasing a building. Returns 0 by default. */
    public int getBuildingDiscount () {
    return 0;
    }

    /** @return prestige points this card gives at end of game. Returns 0 by default. */
    public int getPrestigePoints () {
        return 0;
    }

    /** @return icon of this card. Returns {@link IconEnum#EMPTY} by default for non-Inventor cards. */
    public IconEnum getIcon () {
        return IconEnum.EMPTY;
    }

    /** @return food discount applied during the sustain event. Returns 0 by default. */
    public int getSustainDiscount (){
        return 0;
    }

    /** @return true if this card is a marked hunter that triggers the food bonus on pick. Returns false by default. */
    public boolean getMark() { return false; }

    /** @return ritual stars given to the player on pick. Returns 0 by default. */
    public int getStars() { return 0; }

    /**
     * Accepts a {@link TribeVisitor}. Overridden by each concrete subclass
     * to call the appropriate {@link TribeVisitor#visit} overload.
     *
     * @param tribeVisitor the visitor to accept
     */
    public void acceptVisit (TribeVisitor tribeVisitor) {}

    @Override
    public void addToPlayer(Player player){
        player.addCard(this);
    }

    @Override
    public boolean canBePicked() {
        return true;
    }
}
