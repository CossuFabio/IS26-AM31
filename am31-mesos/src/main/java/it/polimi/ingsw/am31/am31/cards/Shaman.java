package it.polimi.ingsw.am31.am31.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public class Shaman extends CharacterCard {
    private final int stars;

    @JsonCreator
    public Shaman(
            @JsonProperty("era")int era,
            @JsonProperty("minPlayers")int minPlayers,
            @JsonProperty("stars")int stars) {
        super(era, minPlayers);
        this.stars = stars;
    }

    @Override
    public int getStars() {return stars;}

    @Override
    public void onPick(Player player){
        player.increaseStars(this.stars);
    }
    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString(){
        return "CharacterCard type: Shaman - Era: " + era + " - Minimum Players: " + minPlayers + " - Ritual stars given: " + stars;
    }
}