package it.polimi.ingsw.am31.am31.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.cards.CharacterCard;
import it.polimi.ingsw.am31.am31.cards.IconEnum;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public class Inventor extends CharacterCard {
    private final IconEnum icon;
    @JsonCreator
    public Inventor(@JsonProperty("era")int era,
                    @JsonProperty("minPlayers") int minPlayers,
                    @JsonProperty("icon") IconEnum icon) {
        super(era, minPlayers);
        this.icon = icon;
    }
    @Override
    public IconEnum getIcon() {
        return icon;
    }


    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
    }


    @Override
    public String toString(){
        return "CharacterCard type: Inventor - Era: " + era + " - Minimum Players: " + minPlayers + " - Icon: " + this.icon;
    }
}
