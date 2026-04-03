package it.polimi.ingsw.am31.am31.controller;


import it.polimi.ingsw.am31.am31.exceptions.CardNotFoundException;
import it.polimi.ingsw.am31.am31.exceptions.InvalidResourceException;
import it.polimi.ingsw.am31.am31.exceptions.OfferCardNotFoundException;
import it.polimi.ingsw.am31.am31.exceptions.PlayerNotFoundException;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.HashMap;
import java.util.Map;

//This class is used to recover the reference to the object from the message received from the network
public class ResourceFinder {

    //To optimize searching time, this class uses HashMaps for O(1) search.
    //Since players list changes over the course of the game and since the list is small (at most 5 players)
    //it's preferred to look inside game and get the list of players
    private final Game game;

    //We use hashmaps here since the resources of the game never change.
    //Cards and offer track not used during the game are saved, but the model will throw error when trying to access non valid resource
    private final Map<String, OfferCard> offerCardsRegistry;
    private final Map<String, Card> cardsRegistry;

    public ResourceFinder(Game game){
        this.game = game;
        this.offerCardsRegistry = new HashMap<String, OfferCard>();

        for(OfferCard offerCard : game.getGameResources().getOfferCards()){
            offerCardsRegistry.put(offerCard.getOfferCardId(), offerCard);
        }

        this.cardsRegistry = new HashMap<String, Card>();

        for(Card card : game.getGameResources().getTribeCards()){
            cardsRegistry.put(card.getCardId(), card);
        }

        for(BuildingCard buildingCard : game.getGameResources().getBuildingCards()){
            cardsRegistry.put(buildingCard.getCardId(), buildingCard);
        }

    }

    public Player getPlayerFromNickname(String nickname) throws PlayerNotFoundException {

        for(Player p : game.getPlayersList()){
            if(p.getNickname().equals(nickname))
                return p;
        }
        throw new PlayerNotFoundException(nickname);
    }

    public Card getCardFromId(String cardId) throws CardNotFoundException, InvalidResourceException {

        Card card = cardsRegistry.get(cardId);

        if(card == null) throw new CardNotFoundException();
        if(card.getMinPlayers() < game.getNumPlayers()) throw new InvalidResourceException("Card");


        return card;


    }

    public OfferCard getOfferCard(String offerCardId){

        OfferCard offerCard = offerCardsRegistry.get(offerCardId);

        if(offerCard == null) throw new OfferCardNotFoundException();
        if(offerCard.getMinPlayers() < game.getNumPlayers()) throw new InvalidResourceException("OfferCard");

        return offerCard;
    }

}
