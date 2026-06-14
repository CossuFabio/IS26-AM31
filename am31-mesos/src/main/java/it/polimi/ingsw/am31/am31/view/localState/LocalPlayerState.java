package it.polimi.ingsw.am31.am31.view.localState;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.print;
import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.printColor;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Represents the local state of a player in the game.
 */
public class LocalPlayerState {
    private Color color;
    private int food;
    private int prestigePoints;
    private String nickname;
    private List<Card> tribe;
    private List<Card> buildings;
    private boolean hasBonusDraw;

    /**
     * Constructs a new LocalPlayerState with the given nickname and color.
     * Initializes prestige points and food to 0, and empty lists for tribe and buildings.
     * @param nickname The nickname of the player.
     * @param color The color associated with the player.
     */
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

    /**
     * Sets the player's current food count.
     * @param newfood The new food count.
     */
    public void setFood(int newfood){
        this.food = newfood;
    }
    /**
     * Sets the player's current prestige points.
     * @param newpp The new prestige points.
     */
    public void setPrestigePoints(int newpp){
        this.prestigePoints = newpp;
    }

    /**
     * Sets the player's tribe cards.
     * @param newtribe The new list of tribe cards.
     */
    public void setTribe(List<Card> newtribe){
        tribe = new ArrayList<>();
        tribe.addAll(newtribe);
    }

    /**
     * Sets the player's building cards.
     * @param newbuildings The new list of building cards.
     */
    public void setBuildings(List<Card> newbuildings){
        buildings = new ArrayList<>();
        buildings.addAll(newbuildings);
    }
    /**
     * Adds a single building card to the player's collection (primarily for testing).
     * @param newbuilding The building card to add.
     */
    public void addBuilding(Card newbuilding){
        buildings.add(newbuilding);
    }

    /**
     * @return The current food count.
     */
    public int getFood(){return food;}
    /**
     *
     * @return The current prestige points.
     */
    public int getPrestigePoints(){return prestigePoints;}
    /**
     *
     * @return The player's Color.
     */
    public Color getColor(){return color;}
    /**
     *
     * @return The player's nickname.
     */
    public String getNickname(){
      return nickname;
    }
    /**
     * @return A list of Card objects representing the player's tribe.
     */
    public List<Card> getTribe(){return tribe;}
    /**
     * @return A list of Card objects representing the player's buildings.
     */
    public List<Card> getBuildings(){return buildings;}


    /**
     * @return A string containing the player's nickname, color, food, and prestige points.
     */
    public String toString(){
        return "NICKNAME: "+nickname+", COLOR: "+color+", FOOD: "+food+", POINTS: "+prestigePoints;
    }

    /**
     * Sets whether the player has a bonus draw available.
     * @param hasBonusDraw True if the player has a bonus draw, false otherwise.
     */
    public void setBonusDraw(boolean hasBonusDraw){
        this.hasBonusDraw = hasBonusDraw;
    }

    /**
     * Checks if the player has a bonus draw available.
     * @return True if the player has a bonus draw, false otherwise.
     */
    public boolean hasBonusDraw(){return hasBonusDraw; }

}
