package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.BuildingCard;
import it.polimi.ingsw.am31.am31.cards.Card;
import it.polimi.ingsw.am31.am31.cards.CharacterCard;
import it.polimi.ingsw.am31.am31.handlers.endGame.IEndGameHandler;
import it.polimi.ingsw.am31.am31.handlers.endRound.IEndRoundHandler;
import it.polimi.ingsw.am31.am31.handlers.endTurn.IEndTurnHandler;
import it.polimi.ingsw.am31.am31.handlers.huntEvent.IHuntHandler;
import it.polimi.ingsw.am31.am31.handlers.onDraw.IDrawHandler;
import it.polimi.ingsw.am31.am31.handlers.paintEvent.IPaintHandler;
import it.polimi.ingsw.am31.am31.handlers.ritualLose.IRitualLoseStrategy;
import it.polimi.ingsw.am31.am31.handlers.ritualLose.RitualLoseHandler;
import it.polimi.ingsw.am31.am31.handlers.ritualWin.IRitualWinStrategy;
import it.polimi.ingsw.am31.am31.handlers.ritualWin.RitualWinHandler;
import it.polimi.ingsw.am31.am31.handlers.sustainEvent.ISustainHandler;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Player {
    private Color color;
    private int food;
    private int prestigePoints;
    private final String nickname;
    private List<BuildingCard> personalBuildingCards;
    private List<CharacterCard> personalTribeCards;
    private int ritualStars;
    private IDrawHandler drawHandler;
    private IEndTurnHandler endTurnHandler;
    private IEndGameHandler endGameHandler;
    private IHuntHandler huntHandler;
    private ISustainHandler sustainHandler;
    private IPaintHandler paintHandler;
    private RitualLoseHandler ritualLoseHandler;
    private RitualWinHandler ritualWinHandler;
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
        //TODO implement
        return 0;
    }

    public List<CharacterCard> getTribe() {return personalTribeCards; }
    public List<BuildingCard> getBuildings() {return personalBuildingCards; }


    public int getRitualStars(){
        return ritualStars;
    }

    public void increaseStars(int starsToAdd){ritualStars += starsToAdd; }

    public void addToTribe(CharacterCard card){
        personalTribeCards.add(card);
        this.drawHandler.handleDraw(this, card);
    }

    public void winRitual(int prestigePoints){
        ritualWinHandler.handleRitualWin(this, prestigePoints);
    };

    public void loseRitual(int prestigePoints){
        ritualLoseHandler.handleLose(this, prestigePoints);
    }

    public void resolveHunt(int food, int prestigePoints){
        huntHandler.handleHunt(this, food, prestigePoints);
    }

    public void resolveSustain(int malus){
        sustainHandler.handleSustain(this, malus);
    }

    public void resolvePainters(int threshold, int malusPrestigePoints, int bonusPrestigePoints){
        paintHandler.handlePaint(this, threshold, bonusPrestigePoints, malusPrestigePoints);
    }

    public void resolveEndTurn(){
        endTurnHandler.handleEndTurn(this);
    }

    public void resolveEndGame(){
        endGameHandler.handleEndGame(this);
    }


    // Each addEffect method takes as parameter the constructor of the decorator for the correct handler
    // and passes it the current handler that will be wrapped with the new decorator
    public void addEndTurnEffect(Function<IEndTurnHandler, IEndTurnHandler> decoratorFunc){
        this.endTurnHandler = decoratorFunc.apply(this.endTurnHandler);
    }

    public void addEndGameEffect(Function<IEndGameHandler, IEndGameHandler> decoratorFunc){
        this.endGameHandler = decoratorFunc.apply(this.endGameHandler);
    }

    public void addHuntEffect(Function<IHuntHandler, IHuntHandler> decoratorFunc){
        this.huntHandler = decoratorFunc.apply(this.huntHandler);
    }
    public void addDrawEffect(Function<IDrawHandler, IDrawHandler> decoratorFunc){
        this.drawHandler = decoratorFunc.apply(this.drawHandler);
    }

    public void addSustainEffect(Function<ISustainHandler, ISustainHandler> decoratorFunc){
        this.sustainHandler = decoratorFunc.apply(this.sustainHandler);
    }

    public void addPaintEffect(Function<IPaintHandler, IPaintHandler> decoratorFunc){
        this.paintHandler = decoratorFunc.apply(this.paintHandler);
    }

    public void addEndRoundEffect(Function<IEndRoundHandler, IEndRoundHandler> decoratorFunc){
        this.endRoundHandler = decoratorFunc.apply(this.endRoundHandler);
    }

    public void addRitualWinEffect(IRitualWinStrategy newStrategy){
        this.ritualWinHandler.setStrategy(newStrategy);
    }
    
    public void addRitualLoseEffect(IRitualLoseStrategy newStrategy){
        this.ritualLoseHandler.setStrategy(newStrategy);
    }


}
