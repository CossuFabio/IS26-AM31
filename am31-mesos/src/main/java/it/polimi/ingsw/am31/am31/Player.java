package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.Card;
import it.polimi.ingsw.am31.am31.handlers.endGame.IEndGameHandler;
import it.polimi.ingsw.am31.am31.handlers.endRound.IEndRoundHandler;
import it.polimi.ingsw.am31.am31.handlers.endTurn.IEndTurnHandler;
import it.polimi.ingsw.am31.am31.handlers.huntEvent.IHuntHandler;
import it.polimi.ingsw.am31.am31.handlers.onDraw.IDrawHandler;
import it.polimi.ingsw.am31.am31.handlers.paintEvent.IPaintHandler;
import it.polimi.ingsw.am31.am31.handlers.ritualLose.IRitualLoseStrategy;
import it.polimi.ingsw.am31.am31.handlers.ritualWin.IRitualWinStrategy;


import java.util.ArrayList;
import java.util.List;

public class Player {
    private Color color;
    private int food;
    private int prestigePoints;
    private final String nickname;
    private List<Card> personalBuildingCards;
    private List<Card> personalTribeCards;
    private int ritualStars;
    private IDrawHandler drawHandler;
    private IEndTurnHandler endTurnHandler;
    private IEndGameHandler endGameHandler;
    private IHuntHandler huntHandler;
    private ISustainHandler sustainHandler;
    private IPaintHandler paintHandler;
    private IRitualLoseStrategy ritualLoseHandler;
    private IRitualWinStrategy ritualWinHandler;
    private IEndRoundHandler endRoundHandler;

    public Player(String nickname, Color color){
        this.nickname = nickname;
        this.color = color;
        food = 0;
        prestigePoints = 0;
        personalBuildingCards = new ArrayList<>();
        personalTribeCards = new ArrayList<>();
        ritualStars = 0;


    }


    public void editFood(int valFood){
        int oldFood = getFood();
        int newFood = oldFood + valFood;
        this.food = newFood;
    }

    public int getFood(){
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
       //TO-DO
        return 0;
    }

    public int getRitualStars(){
        return ritualStars;
    }

    public void increaseStars(int starsToAdd){ritualStars += starsToAdd; }

    public void addToTribe(Card card){
        personalTribeCards.add(card);

    }

    public void winRitual(int prestigePoints){};

    public void loseRitual(int prestigePoints){}

    public void resolveHunt(int prestigePoints, int food){}

    public void resolveSustain(int malus){}

    public void resolvePainters(int threshold, int malusPrestigePoints, int bonusprestigePoints){}

    public void resolveEndTurn(){}

    public void resolveEndGame(){}

    public IDrawHandler getDrawHandler(){return drawHandler;}

    public void setDrawHandler(){}

    public IEndTurnHandler getEndTurnHandler(){return endTurnHandler;}

    public void setEndTurnHandler(){}

    public IEndGameHandler getEndGameHandler(){return endGameHandler;}

    public void addEffect(IEndTurnHandler newEffect){
        //newEffect.setWrapped(this.endTurnHandler);
        this.endTurnHandler = newEffect;
    }

}
