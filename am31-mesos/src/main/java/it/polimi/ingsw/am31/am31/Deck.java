package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Deck{

    protected List<Card> eraDeck;

    public boolean isEmpty() {
        return eraDeck.isEmpty();
    }

    protected Deck(){
        eraDeck = new ArrayList<Card>();
    }


    public Card draw() {
        if (!eraDeck.isEmpty()) {
            Card temp = eraDeck.getFirst();
            eraDeck.removeFirst();
            return temp;
        }
        //Drawing from an empty deck isn't an option, should it throw an exception?
        throw new IllegalStateException("Empty deck!");
    }
    public int getSize () {
        return eraDeck.size();
    }





}
