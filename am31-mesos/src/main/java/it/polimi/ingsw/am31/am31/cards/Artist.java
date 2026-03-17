package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public class Artist extends CharacterCard {
    public Artist(int era) {
        super(era);
    }

    public void acceptVisit (TribeVisitor tribeVisitor) {
        //return tribeVisitor.visit(this);
    }

}
