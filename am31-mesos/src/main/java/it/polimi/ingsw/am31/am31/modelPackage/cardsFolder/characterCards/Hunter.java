package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;


/**
 * Represents an instance of a Hunter card
 */
public class Hunter extends CharacterCard {
    private final boolean mark;

    @JsonCreator
    public Hunter(
            @JsonProperty("cardId") String cardId,
            @JsonProperty("era") int era,
            @JsonProperty("minPlayers") int minPlayers,
            @JsonProperty("mark") boolean mark){
        super(cardId, era, minPlayers);
        this.mark = mark;
    }

    /** @return true if this hunter is marked */
    @Override
    public boolean getMark() { return mark; }

    /**
     * If this hunter is marked, awards food equal to the number of hunters
     * already in the player's tribe, not counting this card.
     *
     * @param player the player drawing this card
     */
    @Override
    public void onPick(Player player) {
        if(!this.mark) return;
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        player.editFood(visitor.getHunters());
    }

    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
   }

    @Override
    public String toString(){
        String markedString = mark ? " Marked " : "Unmarked";
        return "CharacterCard type: Hunter - Era: " + era + " - Minimum Players: " + minPlayers + " - " + markedString;
    }

}
