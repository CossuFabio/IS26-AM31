package it.polimi.ingsw.am31.am31.view.localState;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.print;
import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.printColor;
import static org.fusesource.jansi.Ansi.ansi;

public class LocalPlayerState {
    private Color color;
    private int food;
    private int prestigePoints;
    private String nickname;
    private List<Card> tribe;
    private List<Card> buildings;
    private boolean hasBonusDraw;

    public LocalPlayerState(String nickname, Color color){
        this.nickname = nickname;
        this.color = color;
        prestigePoints = 0;
        food = 0;
        tribe = new ArrayList<>();
        buildings = new ArrayList<>();
        hasBonusDraw = false;
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
    //for testing
    public void addBuilding(Card newbuilding){
        buildings.add(newbuilding);
    }

    //getters
    public int getFood(){return food;}
    public int getPrestigePoints(){return prestigePoints;}
    public Color getColor(){return color;}
    public String getNickname(){
      return nickname;
    }
    public List<Card> getTribe(){return tribe;}
    public List<Card> getBuildings(){return buildings;}


    public String toString(){
        return "NICKNAME: "+nickname+", COLOR: "+color+", FOOD: "+food+", POINTS: "+prestigePoints;
    }

    public void setBonusDraw(boolean hasBonusDraw){
        this.hasBonusDraw = hasBonusDraw;
    }

    public boolean hasBonusDraw(){return hasBonusDraw; }

}
