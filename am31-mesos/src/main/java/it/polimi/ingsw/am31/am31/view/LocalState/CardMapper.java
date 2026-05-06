package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonTribeCardsSupplier;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferCardMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardMapper {
    private final List<Card> cards;
    private final List<OfferCard> offercards;

    public CardMapper() throws IOException {
        cards = new ArrayList<Card>();
        offercards = new ArrayList<OfferCard>();
        GameResources gameResources = new GameResources(new JsonTribeCardsSupplier(), new JsonBuildingCardsSupplier(), new JsonOfferSupplier());
        cards.addAll(gameResources.getTribeCards());
        cards.addAll(gameResources.getBuildingCards());
        offercards.addAll(gameResources.getOfferCards());
    }

    public  Card getCard(String id){
        for(Card c : cards)
            if(c.getCardId().equals(id))
                return c;
    return null; //maybe add an exception
    }

    public LocalOfferCard getOfferCard(OfferCardMessage msg){
        for(OfferCard c : offercards)
            if(c.getOfferCardId().equals(msg.getCardId()))
            {
                //once it finds the og offercard c
                //creates new localOfferCard, with the player inside if needed
                return new LocalOfferCard(c, msg);
            }
        //card not found? exception?
        return null;
    }
    public List<Card> getCards (List<String> ids) {
        List<Card> res = new ArrayList<Card>();
        for(String  s: ids)
            res.add(getCard(s));
        return res;
    }
    public ArrayList<LocalOfferCard> getOfferCards (List<OfferCardMessage> msg) {
        ArrayList<LocalOfferCard> res = new ArrayList<LocalOfferCard>();
        for(OfferCardMessage  o: msg)
            res.add(getOfferCard(o));
        return res;
    }


}
