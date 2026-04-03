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
    public void acceptVisit(TribeVisitor visitor){}


    public int getMinPlayers() {
        return 2;
    }
    public String getCardId(){return this.cardId; }

    public boolean equals(Card card){return this.cardId.equals(card.cardId);  }


    public abstract String toString();



}
