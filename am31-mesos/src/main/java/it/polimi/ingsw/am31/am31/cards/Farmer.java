package it.polimi.ingsw.am31.am31.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public class Farmer extends CharacterCard {
    private final int discount;
    @JsonCreator
    public Farmer(
            @JsonProperty("era")int era,
            @JsonProperty("minPlayers")int minPlayers,
            @JsonProperty("discount") int discount){
        super(era,minPlayers);
        this.discount = discount;
    }

    @Override
    public int getSustainDiscount() {
        return discount;
    }



    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString(){
        return "CharacterCard type: Farmer - Era: " + era + " - Minimum Players: " + minPlayers + " - Sustain discount: " + discount;
    }
}
