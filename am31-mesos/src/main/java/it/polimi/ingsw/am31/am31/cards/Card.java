package it.polimi.ingsw.am31.am31.cards;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

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
    protected int era;
    protected int minPlayers;

    protected Card(int era) {
     this.era = era;
    }

    protected Card() {
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getEra () {
        return era;
    }
    public void acceptVisit(TribeVisitor visitor){

    }
}
