package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;

import java.util.List;

/**
 * Abstract base class for event cards resolved at the end of each round.
 */
public abstract class EventCard extends Card {

    protected int prestigePointsBonus;
    protected int prestigePointsMalus;

    protected EventCard(String cardID, int era) {
        super(cardID, era);
    }

    /**
     * Applies this event's effect to all players.
     *
     * @param players the list of all players in the game
     */
    public abstract void resolve(List<Player> players);

    public int getPrestigePointsBonus() {
        return prestigePointsBonus;
    }

    public int getPrestigePointsMalus() {
        return prestigePointsMalus;
    }

}
