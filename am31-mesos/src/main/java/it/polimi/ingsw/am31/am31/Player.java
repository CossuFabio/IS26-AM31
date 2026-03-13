package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.Card;

import java.util.List;

public class Player {
    private Color color;
    private int food;
    private int prestigePoints;
    private int virtualPrestigePoints;
    private List<Card> personalBuildingCards;
    private List<Card> personalTribeCards;
    private int bonusStars;
    //private IDrawHandler drawHandler;
    //private IEndTurnHandler endTurnHandler;
    //private IEndGameHandler endGameHandler;
    //private IHuntHandler huntHandler;
    //private ISustainHandler sustainHandler;
    //private IPaintHandler paintHandler;
    //private IRitualLoseHandler ritualLoseHandler;
    //private IRitualWinHandler ritualWinHandler;
    //private IEndRoundHandler endRoundHandler;

    public void editFood(int valFood){
        int oldFood = getFood();
        int newFood = oldFood + valFood;
        this.food = newFood;
    }

    public int getFood() {
        return food;
    }

    public void editPrestigePoints(int valPP){
        int oldPP = getPrestigePoints();
        int newPP = oldPP + valPP;
        this.prestigePoints = newPP;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public int finalScore(){
        int endPoints = 0;
        return endPoints;
    }

    public int getRitualStars(){
        return bonusStars;
    }


}
