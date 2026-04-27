package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;

import java.util.List;

public class LocalPlayerState {
    private Color color;
    private int food;
    private int prestigePoints;
    private String nickname;
    private List<IPickable> tribe;

    public LocalPlayerState(String nickname, Color color){
        this.nickname = nickname;
        this.color = color;
    }
   //useless public void setColor(){}
    public void setFood(int newfood){
        this.food = newfood;
    }
    public void setPrestigePoints(int newpp){
        this.prestigePoints = newpp;
    }
   //useless public void setNickname(){}
    public void setTribe(){}

    public String getNickname(){
        return nickname;
    }
}
