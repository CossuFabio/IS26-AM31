package it.polimi.ingsw.am31.am31.network.requests;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;

public class JoinNetworkRequest extends NetworkRequest {
    private final String playerID;
    private final Color color;
    private final int gameID;


    //TODO: Finish this
    public JoinNetworkRequest(String playerNickname, Color color, int id) {
        super("JoinNetworkRequest");
        this.playerID =  playerNickname;
        this.color = color;
        this.gameID = id;
    }

    public String getPlayerID() {return playerID;}
    public Color getColor() {return color;}
    public int getGameID() {return gameID;}
}
