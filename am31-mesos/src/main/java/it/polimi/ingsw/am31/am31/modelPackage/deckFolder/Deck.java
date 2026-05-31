package it.polimi.ingsw.am31.am31.modelPackage.deckFolder;


import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.EmptyDeckException;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class representing a deck of cards. Cards are drawn in order, from first to last.
 */
public abstract class Deck{

    protected List<Card> eraDeck;

    /**
     * @return true if no card remains in the deck
     */
    public boolean isEmpty() {
        return eraDeck.isEmpty();
    }

    protected Deck(){
        eraDeck = new ArrayList<Card>();
    }

    /**
     * @return the next card drawn from the deck
     * @throws EmptyDeckException if the deck is empty
     */
    public Card draw() throws EmptyDeckException{
        if (!eraDeck.isEmpty()) {
            Card temp = eraDeck.getFirst();
            eraDeck.removeFirst();
            return temp;
        }
        throw new EmptyDeckException();
    }

    /**
     * @return the number of remaining cards in the deck
     */
    public int getSize () {
        return eraDeck.size();
    }

    /**
     * @return the era of the next card that will be drawn
     * @throws EmptyDeckException if the deck is empty
     */
    public int getNextCardEra() throws EmptyDeckException{
        if(eraDeck.isEmpty()){
            throw new EmptyDeckException();
        }
        return eraDeck.getFirst().getEra();
    }



}
