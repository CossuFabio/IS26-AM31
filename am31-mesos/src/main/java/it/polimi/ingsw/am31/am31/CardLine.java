package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class CardLine <T extends Card>{
    private ArrayList<T> cards;

    public CardLine(int nPlayers){
    //To-Do List: Create constructor
        // needs to get Cards from TribeDeck
    }


    //pickCard selects wanted card from CardLine and returns it by removing it from the rack
    public T pickCard(int index) {
        T selectedCard;
        selectedCard = this.cards.remove(index);
        return selectedCard;
    }
}
