package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder;

import com.fasterxml.jackson.annotation.*;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.HuntEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.PaintingEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.RitualEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Builder.class,           name = "Builder"),
        @JsonSubTypes.Type(value = Farmer.class,            name = "Farmer"),
        @JsonSubTypes.Type(value = Hunter.class,            name = "Hunter"),
        @JsonSubTypes.Type(value = Shaman.class,            name = "Shaman"),
        @JsonSubTypes.Type(value = Inventor.class,          name = "Inventor"),
        @JsonSubTypes.Type(value = Artist.class,            name = "Artist"),
        @JsonSubTypes.Type(value = HuntEventCard.class,     name = "HuntEventCard"),
        @JsonSubTypes.Type(value = RitualEventCard.class,   name = "RitualEventCard"),
        @JsonSubTypes.Type(value = PaintingEventCard.class, name = "PaintingEventCard"),
        @JsonSubTypes.Type(value = SustainEventCard.class,  name = "SustainEventCard")
})

/**
 * Abstract base class for all cards in the game (character, building, event).
 * Type-specific getters return 0 or false unless overridden by a concrete subclass.
 */
public abstract class Card {

   @JsonIgnore
    protected final int era;
    protected final String cardId;
    protected Card(String cardId, int era) {
        this.era = era;
        this.cardId = cardId;
    }

    public int getEra () {
        return era;
    }

    /**
     * Classic method of the Visitor design pattern.
     * @param visitor {@link TribeVisitor} that will visit the card
     */
    public abstract void acceptVisit(TribeVisitor visitor);


    /** Minimum number of players required to include this card in the game. Returns 2 by default. */
    public int getMinPlayers() {
        return 2;
    }
    public String getCardId(){return this.cardId; }

    /**
     * card Id-based comparison
     * @param card
     * @return
     */
    public boolean equals(Card card){return this.cardId.equals(card.cardId); }


    public abstract String toString();

    /**
     * Returns false by default. Overridden to true by cards that implement {@link IPickable},
     * avoiding the need for an instanceof check.
     */
    public boolean canBePicked(){return false; }

    /** Food discount applied when purchasing a building. Returns 0 by default. */
    public int getBuildingDiscount() { return 0; }

    /** Returns true if food + discount covers this card's cost. Returns true by default; overridden by {@link BuildingCard}. */
    public boolean canAffordWithFood(int food, int discount) { return true; }


}
