package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.Card;

import java.util.List;

public class Board {
    private List<OfferCard> offerTrack;
    private CardLine<Card> upperLine;
    private CardLine<Card> underLine;
    private CardLine<Card> upperBLine;
    private CardLine<Card> underBLine;

    public void flushBuilding(){}

    public void moveLower(){}

    public void addUpper(Card card){}

    public void addLower(Card card){}

    public void flushTribe(){}

    public void showBoardState(){}

    public List<OfferCard> getOfferCards(){
        return offerTrack;
    }

    public OfferCard getNextCard() {
        OfferCard nextOfferCard = null;
        return nextOfferCard;
    }

    //public List<Card> pickFromLine(int numberOfDraws){}
}
