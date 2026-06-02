package it.polimi.ingsw.am31.am31.network.messages.updateMessages.boardUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO that describes the state of a single offer track slot: the card ID,
 * the nickname of the player whose totem is on it, and whether the slot is free.
 * Not an UpdateMessage; used as a nested element inside {@link OfferTrackUpdate}.
 * The actual card object is resolved client-side from the ID.
 * WARNING: may contain null values. Check with the isFree method before trying to access the player.
 */
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
