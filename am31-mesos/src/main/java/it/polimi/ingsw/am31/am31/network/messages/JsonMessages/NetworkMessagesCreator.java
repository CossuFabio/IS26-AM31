package it.polimi.ingsw.am31.am31.network.messages.JsonMessages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class NetworkMessagesCreator {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String createDrawMessage(Player player, Card card, BoardRows row){
        return mapper.createObjectNode()
                .put(NetworkMessagesConstants.PLAYER_NICKNAME_JSON_FIELD, player.getNickname())
                .put(NetworkMessagesConstants.CARD_ID_JSON_FIELD, card.getCardId())
                .put(NetworkMessagesConstants.SELECTED_ROW_JSON_FIELD, row.toString())
                .toString();
    }

    public static String createTotemPlacingMessage(Player player, OfferCard offerCard){

        return mapper.createObjectNode()
                .put(NetworkMessagesConstants.PLAYER_NICKNAME_JSON_FIELD, player.getNickname())
                .put(NetworkMessagesConstants.OFFER_CARD_ID_JSON_FIELD,offerCard.getOfferCardId())
                .toString();

    }



}
