package it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates;

//The only purpose of this class is encapsulating the state of an offerTrack before sending to the client
public class OfferCardMessage {


    private final String cardId;
    private final String totemPlayerNickname;

    //Serves as a double check: in case of error putting a null playerId, the user does not crash when trying to read nickname
    private final boolean isFree;

    public OfferCardMessage(String cardId, String totemPlayerId, boolean isFree) {
        this.cardId = cardId;
        this.totemPlayerNickname = totemPlayerId;
        this.isFree = isFree;
    }

    public String getCardId(){return this.cardId; }
    public String getTotemPlayerNickname(){return this.totemPlayerNickname;}
    public boolean isFree(){return this.isFree;}

}
