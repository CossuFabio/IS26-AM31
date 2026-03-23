package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.BuildingCard;
import it.polimi.ingsw.am31.am31.cards.CharacterCard;
import it.polimi.ingsw.am31.am31.handlers.endGame.DefaultEndGameHandler;
import it.polimi.ingsw.am31.am31.handlers.endGame.IEndGameHandler;
import it.polimi.ingsw.am31.am31.handlers.endRound.DefaultEndRoundHandler;
import it.polimi.ingsw.am31.am31.handlers.endRound.IEndRoundHandler;
import it.polimi.ingsw.am31.am31.handlers.endTurn.DefaultEndTurnHandler;
import it.polimi.ingsw.am31.am31.handlers.endTurn.IEndTurnHandler;
import it.polimi.ingsw.am31.am31.handlers.huntEvent.DefaultHuntHandler;
import it.polimi.ingsw.am31.am31.handlers.huntEvent.IHuntHandler;
import it.polimi.ingsw.am31.am31.handlers.onDraw.DefaultCardDrawHandler;
import it.polimi.ingsw.am31.am31.handlers.onDraw.IDrawHandler;
import it.polimi.ingsw.am31.am31.handlers.paintEvent.DefaultPaintHandler;
import it.polimi.ingsw.am31.am31.handlers.paintEvent.IPaintHandler;
import it.polimi.ingsw.am31.am31.handlers.ritualLose.IRitualLoseStrategy;
import it.polimi.ingsw.am31.am31.handlers.ritualLose.RitualLoseHandler;
import it.polimi.ingsw.am31.am31.handlers.ritualWin.IRitualWinStrategy;
import it.polimi.ingsw.am31.am31.handlers.ritualWin.RitualWinHandler;
import it.polimi.ingsw.am31.am31.handlers.sustainEvent.DefaultSustainHandler;
import it.polimi.ingsw.am31.am31.handlers.sustainEvent.ISustainDiscountCharacter;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Player {
    private final Color color;
    private int food;
    private int prestigePoints;
    private final String nickname;
    private final List<BuildingCard> personalBuildingCards;
    private final List<CharacterCard> personalTribeCards;
    private int ritualStars;
    private IDrawHandler drawHandler;
    private IEndTurnHandler endTurnHandler;
    private IEndGameHandler endGameHandler;
    private IHuntHandler huntHandler;
    private DefaultSustainHandler sustainHandler;
    private IPaintHandler paintHandler;
    private RitualLoseHandler ritualLoseHandler;
    private RitualWinHandler ritualWinHandler;
    private IEndRoundHandler endRoundHandler;

    public Player(String nickname, Color color) {
        this.nickname = nickname;
        this.color = color;
        food = 0;
        prestigePoints = 0;
        personalBuildingCards = new ArrayList<>();
        personalTribeCards = new ArrayList<>();
        ritualStars = 0;

        this.drawHandler = new DefaultCardDrawHandler();
        this.endTurnHandler = new DefaultEndTurnHandler();
        this.endGameHandler = new DefaultEndGameHandler();
        this.huntHandler = new DefaultHuntHandler();
        this.sustainHandler = new DefaultSustainHandler();
        this.paintHandler = new DefaultPaintHandler();
        this.ritualLoseHandler = new RitualLoseHandler();
        this.ritualWinHandler = new RitualWinHandler();
        this.endRoundHandler = new DefaultEndRoundHandler();
    }

    public String getNickname() {
        return nickname;
    }

    public Color getColor() {
        return color;
    }

    public void editFood(int valFood) {

        int newFood = this.food + valFood;
        if(newFood < 0) this.food = 0;
        else this.food = newFood;

    }

    public int getFood() {
        return food;
    }

    public void editPrestigePoints(int valPP) {
        int oldPP = getPrestigePoints();
        int newPP = oldPP + valPP;
        this.prestigePoints = newPP;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public int getRitualStars() {
        return ritualStars;
    }

    public void increaseStars(int starsToAdd) {
        ritualStars += starsToAdd;
    }

    public List<CharacterCard> getTribe() {
        return personalTribeCards;
    }

    public List<BuildingCard> getBuildings() {
        return personalBuildingCards;
    }


    public void addToTribe(CharacterCard card) {
        //Draw effects are triggered before the card is considered part of the tribe
        this.drawHandler.handleDraw(this, card);
        personalTribeCards.add(card);
    }

    public void addToBuildings(BuildingCard newBuilding){
        this.drawHandler.handleDraw(this, newBuilding);
        personalBuildingCards.add(newBuilding);
    }
    
    //public void addCard(Character)
    //Public void addCard(Building)

    public int getBuildersDiscount(){
        return this.personalTribeCards.stream().mapToInt(card -> card.getBuildingDiscount()).sum();
    }

    public void winRitual(int prestigePoints) {
        ritualWinHandler.handleRitualWin(this, prestigePoints);
    }

    public void loseRitual(int prestigePoints) {
        ritualLoseHandler.handleLose(this, prestigePoints);
    }

    public void resolveHunt(int food, int prestigePoints) {
        huntHandler.handleHunt(this, food, prestigePoints);
    }

    public void resolveSustain(int malus) {
        sustainHandler.handleSustain(this, malus);
    }

    public void resolvePainters(int threshold, int malusPrestigePoints, int bonusPrestigePoints) {
        paintHandler.handlePaint(this, threshold, bonusPrestigePoints, malusPrestigePoints);
    }

    public void resolveEndTurn(int turnOrder, int nPlayers) {
        endTurnHandler.handleEndTurn(this, turnOrder, nPlayers);
    }

    public void resolveEndGame() {
        endGameHandler.handleEndGame(this);
    }

    public void resolveEndRound(){endRoundHandler.handleEndRound(this);}

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

    public void addSustainBonus(Supplier<ISustainDiscountCharacter> newBonus){
        this.sustainHandler.addSustainDiscountEffect(newBonus.get());
    }

    public void addPaintEffect(Function<IPaintHandler, IPaintHandler> decoratorFunc){
        this.paintHandler = decoratorFunc.apply(this.paintHandler);
    }

    public void addEndRoundEffect(Function<IEndRoundHandler, IEndRoundHandler> decoratorFunc){
        this.endRoundHandler = decoratorFunc.apply(this.endRoundHandler);
    }

    public void addRitualWinEffect(Supplier<IRitualWinStrategy> newStrategy){
        this.ritualWinHandler.setStrategy(newStrategy.get());
    }
    
    public void addRitualLoseEffect(Supplier<IRitualLoseStrategy> newStrategy){
        this.ritualLoseHandler.setStrategy(newStrategy.get());
    }


}
