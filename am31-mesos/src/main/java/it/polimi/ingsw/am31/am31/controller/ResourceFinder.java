package it.polimi.ingsw.am31.am31.controller;


import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.*;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.PlayerNotFoundException;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.HashMap;
import java.util.Map;

/** Resolves string identifiers from network requests to domain-level game objects for a single game of Mesos. */
public class ResourceFinder {

    //To optimize searching time, this class uses HashMaps for O(1) search.
    //Since players list changes over the course of the game and since the list is small (at most 5 players)
    //it's preferred to look inside game and get the list of players
    private final Game game;

    //We use hashmaps here since the resources of the game never change.
    //Cards and offer track not used during the game are saved, but the model will throw error when trying to access non-valid resource
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

    /**
     * Resolves a String nickname to a domain-level Player object.
     * @param nickname nickname of the requested player
     * @return the player with the matching nickname
     * @throws PlayerNotFoundException if no player with the given nickname exists in this game
     */
    public Player getPlayerFromNickname(String nickname) throws PlayerNotFoundException {

        for(Player p : game.getPlayersList()){
            if(p.getNickname().equals(nickname))
                return p;
        }
        throw new PlayerNotFoundException(nickname);
    }

    /**
     * Resolves a String cardId to a domain-level Card object
     * @param cardId the id of the requested card
     * @return the card with the matching id
     * @throws CardNotFoundException if the card does not exist in the game resources
     * @throws InvalidResourceException if the requested card exists but the card's minimum player requirement exceeds the current player count
     */
    public Card getCardFromId(String cardId) throws CardNotFoundException, InvalidResourceException {

        Card card = cardsRegistry.get(cardId);

        if(card == null) throw new CardNotFoundException();
        if(card.getMinPlayers() > game.getNumPlayers()) throw new InvalidResourceException(InvalidResourceTypeEnum.CARD);


        return card;


    }

    /**
     * Resolves a String offerCardId to a domain-level OfferCard object
     * @param offerCardId the id of the requested OfferCard
     * @return the OfferCard with the matching id
     * @throws OfferCardNotFoundException if the offer card does not exist in the game resources
     * @throws InvalidResourceException if the requested offer card exists but the card's minimum player requirement exceeds the current player count
     */
    public OfferCard getOfferCard(String offerCardId) throws OfferCardNotFoundException, InvalidResourceException {

        OfferCard offerCard = offerCardsRegistry.get(offerCardId);

        if(offerCard == null) throw new OfferCardNotFoundException();
        if(offerCard.getMinPlayers() > game.getNumPlayers()) throw new InvalidResourceException(InvalidResourceTypeEnum.OFFER_CARD);

        return offerCard;
    }

}
