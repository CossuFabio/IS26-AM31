package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IUpdateVisitor;

//The only purpose of this class is encapsulating the state of an offerTrack before sending to the client
public class OfferCardMessage {


    private final String cardId;
    private final String totemPlayerNickname;

    @JsonIgnore
    public static final String EMPTY_CARD = "CARD_IS_EMPTY";


    //Serves as a double check: in case of error putting a null playerId, the user does not crash when trying to read nickname
    private final boolean isFree;



    @JsonCreator
    public OfferCardMessage(@JsonProperty("cardId") String cardId,
                            @JsonProperty("totemPlayerNickname") String totemPlayer,
                            @JsonProperty("isFree") boolean isFree) {
        this.cardId = cardId;
        this.totemPlayerNickname = totemPlayer;
        this.isFree = isFree;
    }

    public String getCardId(){return this.cardId; }
    public String getTotemPlayerNickname(){return this.totemPlayerNickname;}



    public boolean checkValidity(){
        return cardId != null && totemPlayerNickname != null;
    }

    @JsonProperty("isFree")
    public boolean isFree(){return this.isFree;}


}
