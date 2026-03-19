package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private ArrayList<OfferCard> offerTrack;
    private CardLine<Card> upperLine;
    private CardLine<Card> underLine;
    private CardLine<Card> upperBLine;
    private CardLine<Card> underBLine;
    private int offerTrackSize;

    public Board(int numPlayers) {

    }

    public void flushBuilding(){}

    public void moveLower(){}

    public void addUpper(Card card){}

    public void addLower(Card card){}

    public void flushTribe(){}

    public void showBoardState(){}

    public List<OfferCard> getOfferCards(){
        return offerTrack;
    }

    public OfferCard getNextCard(int currentCard) {
        OfferCard nextOfferCard = null;
        if (currentCard < getOfferTrackSize()){
            nextOfferCard = offerTrack.get(currentCard+1);
        }
        return nextOfferCard;
    }

    public int getOfferTrackSize(){return offerTrackSize;}
    //public List<Card> pickFromLine(int numberOfDraws){}

    public static class TribeDeck {
    }
}
