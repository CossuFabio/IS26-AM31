package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public abstract class Card {
    protected int era;
    protected Card(int era) {
     this.era = era;
    }

    protected Card() {
    }
    public int getEra () {
        return era;
    }
    //TODO TESTING
    public void acceptVisit(TribeVisitor visitor){

        return;
    }
}
