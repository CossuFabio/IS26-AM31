package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;

import java.util.ArrayList;
import java.util.List;

public class LocalPlayerState {
    private Color color;
    private int food;
    private int prestigePoints;
    private String nickname;
    private List<Card> tribe;
    private List<Card> buildings;

    public LocalPlayerState(String nickname, Color color){
        this.nickname = nickname;
        this.color = color;
    }
   //useless public void setColor(){}
   //useless public void setNickname(){}

    public void setFood(int newfood){
        this.food = newfood;
    }
    public void setPrestigePoints(int newpp){
        this.prestigePoints = newpp;
    }

    public void setTribe(List<Card> newtribe){
        tribe = new ArrayList<>();
        tribe.addAll(newtribe);
    }

    public void setBuildings(List<Card> newbuildings){
        buildings = new ArrayList<>();
        buildings.addAll(newbuildings);
    }
    public String getNickname(){
        return nickname;
    }
}
