package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;

//Encapsulates a player in lobby
public class PlayerMessage {


    private final String nickname;
    private final Color color;

    @JsonCreator
    public PlayerMessage(@JsonProperty("nickname") String nickname, @JsonProperty("color")Color color){
        this.nickname = nickname;
        this.color = color;
    }

    public Color getColor(){return color;}
    public String getNickname(){return nickname; }

}
