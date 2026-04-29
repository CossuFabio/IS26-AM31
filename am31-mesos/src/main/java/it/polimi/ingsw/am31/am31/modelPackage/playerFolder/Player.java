package it.polimi.ingsw.am31.am31.modelPackage.playerFolder;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObservable;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame.DefaultEndGameHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame.IEndGameHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endRound.DefaultEndRoundHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endRound.IEndRoundHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn.DefaultEndTurnHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn.IEndTurnHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent.DefaultHuntHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent.IHuntHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.DefaultCardDrawHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.IDrawHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent.DefaultPaintHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent.IPaintHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose.IRitualLoseStrategy;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose.RitualLoseHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin.IRitualWinStrategy;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin.RitualWinHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.DefaultSustainHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.ISustainDiscountCharacter;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Player implements GameObservable {
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
    private final DefaultSustainHandler sustainHandler;
    private IPaintHandler paintHandler;
    private final RitualLoseHandler ritualLoseHandler;
    private final RitualWinHandler ritualWinHandler;
    private IEndRoundHandler endRoundHandler;

    private int bonusDrawFromUpper;
    private int bonusDrawFromLower;

    private ObserverHandler observers;

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

        bonusDrawFromLower = 0;
        bonusDrawFromUpper = 0;

        //Prevents NullPointerException but must be set from game when creating new player!
        this.observers = new GameObserversSet();

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
        observers.onPlayerScoresUpdate(this);
    }

    public int getFood() {
        return food;
    }

    public void editPrestigePoints(int valPP) {
        int oldPP = getPrestigePoints();
        int newPP = oldPP + valPP;
        this.prestigePoints = newPP;
        observers.onPlayerScoresUpdate(this);
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

    public ArrayList<CharacterCard> getTribe() {
        return new ArrayList<CharacterCard>(personalTribeCards);
    }

    public ArrayList<BuildingCard> getBuildings() {
        return new ArrayList<BuildingCard>(personalBuildingCards);
    }

    public void addCard(CharacterCard card) {
        this.drawHandler.handleDraw(this, card);
        personalTribeCards.add(card);
        observers.onPlayerTribeUpdate(this);
    }

    public void addCard(BuildingCard card)
    {
        this.drawHandler.handleDraw(this, card);
        personalBuildingCards.add(card);
        observers.onPlayerNewBuildingEvent(this);
    }

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

    public boolean hasBonusDraw(){return bonusDrawFromLower > 0 || bonusDrawFromUpper > 0 ; }

    public void addBonusDrawFromUpper(int drawBonus){ this.bonusDrawFromUpper += drawBonus; }

    public void addBonusDrawFromLower(int drawBonus){ this.bonusDrawFromLower += drawBonus; }

    public int getBonusDrawFromUpper(){ return this.bonusDrawFromUpper; }
    public int getBonusDrawFromLower(){ return this.bonusDrawFromLower; }

    @Override
    public boolean equals(Object player){
        if(player == null || player.getClass() != Player.class) return false;
        return this == player || this.nickname.equals(((Player) player).getNickname());
    }

    @Override
    public int hashCode() {
        return nickname.hashCode();
    }

    @Override
    public void setObserverHandler(ObserverHandler obs){
        this.observers = obs;
    }



}
