package it.polimi.ingsw.am31.am31.network.requests;

public class TotemNetworkRequest extends NetworkRequest {
    private final String playerID;
    private final String offerTrackID;

    protected TotemNetworkRequest(String playerID, String offerTrackID){
        super("TOTEM_PLACING");
        this.playerID = playerID;
        this.offerTrackID = offerTrackID;
    }

    public String getPlayerID() {return playerID;}
    public String getOfferTrackID() {return offerTrackID;}
}
