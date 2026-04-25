package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;

import java.util.List;

public class CardMapper {
    public static Card getCard(String id, List<Card> cards){
        for(Card c : cards)
            if(c.getCardId().equals(id))
                return c;
    return null; //exception?
    }
    public static OfferCard getOfferCard(String id, List<OfferCard> cards){
        for(OfferCard c : cards)
            if(c.getOfferCardId().equals(id))
                return c;
        return null;
    }
}
