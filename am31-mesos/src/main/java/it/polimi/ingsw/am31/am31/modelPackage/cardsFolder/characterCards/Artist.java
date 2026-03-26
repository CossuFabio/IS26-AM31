package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;

public class Artist extends CharacterCard {

    @JsonCreator
    public Artist(
            @JsonProperty("era")  int era,
            @JsonProperty("minPlayers") int minPlayers) {
        super(era, minPlayers);
    }

    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString(){
        return "CharacterCard type: Artist - Era: " + era + " - Minimum Players: " + minPlayers;
    }

}