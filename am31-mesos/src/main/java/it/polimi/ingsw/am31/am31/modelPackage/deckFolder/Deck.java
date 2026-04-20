package it.polimi.ingsw.am31.am31.modelPackage.deckFolder;


import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.EmptyDeckException;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Deck{

    protected List<Card> eraDeck;

    public boolean isEmpty() {
        return eraDeck.isEmpty();
    }

    protected Deck(){
        eraDeck = new ArrayList<Card>();
    }


    public Card draw() throws EmptyDeckException{
        if (!eraDeck.isEmpty()) {
            Card temp = eraDeck.getFirst();
            eraDeck.removeFirst();
            return temp;
        }
        //Drawing from an empty deck isn't an option, should it throw an exception?
        throw new EmptyDeckException();
    }
    public int getSize () {
        return eraDeck.size();
    }

    public int getNextCardEra() throws EmptyDeckException{
        if(eraDeck.isEmpty()){
            throw new EmptyDeckException();
        }
        return eraDeck.getFirst().getEra();
    }



}
