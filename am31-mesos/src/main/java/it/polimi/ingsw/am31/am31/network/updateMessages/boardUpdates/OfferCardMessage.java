package it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//The only purpose of this class is encapsulating the state of an offerTrack before sending to the client
public class OfferCardMessage {

    public static final String EMPTY_CARD = "CARD_IS_EMPTY";
    private final String cardId;
    private final String totemPlayerNickname;

    //Serves as a double check: in case of error putting a null playerId, the user does not crash when trying to read nickname
    private final boolean isFree;

    @JsonCreator
    public OfferCardMessage(@JsonProperty("cardID") String cardId,
                            @JsonProperty("totemPlayerId") String totemPlayerId,
                            @JsonProperty("isFree") boolean isFree) {
        this.cardId = cardId;
        this.totemPlayerNickname = totemPlayerId;
        this.isFree = isFree;
    }

    public String getCardId(){return this.cardId; }
    public String getTotemPlayerNickname(){return this.totemPlayerNickname;}
    public boolean isFree(){return this.isFree;}


    public boolean checkValidity(){
        return cardId != null && totemPlayerNickname != null;
    }

}
