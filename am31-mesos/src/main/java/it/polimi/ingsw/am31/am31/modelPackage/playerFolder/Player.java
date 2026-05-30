package it.polimi.ingsw.am31.am31.modelPackage.playerFolder;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObservable;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame.DefaultEndGameHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame.IEndGameHandler;
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

/**
 * Represents a player in a game of Mesos.
 * Game event resolution (sustenance, hunt, ritual, end-of-turn, end-game) is handled
 * by a set of pluggable handlers that building cards can extend via the decorator pattern.
 */
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


    private boolean bonusDraw;

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

        // Related to building number 20
        bonusDraw = false;

        //Prevents NullPointerException but must be set from game when creating new player!
        this.observers = new GameObserversSet();

    }

    public String getNickname() {
        return nickname;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Adds {@code valFood} to the player's food supply (pass negative to consume).
     * Result is clamped to 0; food cannot go negative.
     *
     * @param valFood amount to add or subtract
     */
    public void editFood(int valFood) {

        int newFood = this.food + valFood;
        if(newFood < 0) this.food = 0;
        else this.food = newFood;
        observers.onPlayerScoresUpdate(this);
    }

    public int getFood() {
        return food;
    }

    /**
     * Adds {@code valPP} to the player's prestige points (pass negative to subtract).
     * Unlike food, prestige points can go negative.
     *
     * @param valPP amount to add or subtract
     */
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

    public void increaseRitualStars(int starsToAdd) {
        ritualStars += starsToAdd;
    }

    public ArrayList<CharacterCard> getTribe() {
        return new ArrayList<CharacterCard>(personalTribeCards);
    }

    public ArrayList<BuildingCard> getBuildings() {
        return new ArrayList<BuildingCard>(personalBuildingCards);
    }

    /**
     * Adds a character card to the player's tribe, triggering any active draw effects.
     *
     * @param card the card to add
     */
    public void addCard(CharacterCard card) {
        this.drawHandler.handleDraw(this, card);
        personalTribeCards.add(card);
        observers.onPlayerTribeUpdate(this);
    }

    /**
     * Adds a building card to the player's tribe, triggering any active draw effects.
     *
     * @param card the card to add
     */
    public void addCard(BuildingCard card) {
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



    // Each addEffect method takes as parameter the constructor of the decorator for the correct handler
    // and passes it the current handler that will be wrapped with the new decorator

    /**
     * Wraps the current end-of-turn handler with a new decorator.
     * Called when a building card modifies the end-of-turn behaviour.
     *
     * @param decoratorFunc function that takes the current handler and returns the decorated one
     */
    public void addEndTurnEffect(Function<IEndTurnHandler, IEndTurnHandler> decoratorFunc){
        this.endTurnHandler = decoratorFunc.apply(this.endTurnHandler);
    }

    /**
     * Wraps the current end-game handler with a new decorator.
     *
     * @param decoratorFunc function that takes the current handler and returns the decorated one
     */
    public void addEndGameEffect(Function<IEndGameHandler, IEndGameHandler> decoratorFunc){
        this.endGameHandler = decoratorFunc.apply(this.endGameHandler);
    }

    /**
     * Wraps the current hunt-event handler with a new decorator.
     *
     * @param decoratorFunc function that takes the current handler and returns the decorated one
     */
    public void addHuntEffect(Function<IHuntHandler, IHuntHandler> decoratorFunc){
        this.huntHandler = decoratorFunc.apply(this.huntHandler);
    }

    /**
     * Wraps the current card-draw handler with a new decorator.
     *
     * @param decoratorFunc function that takes the current handler and returns the decorated one
     */
    public void addDrawEffect(Function<IDrawHandler, IDrawHandler> decoratorFunc){
        this.drawHandler = decoratorFunc.apply(this.drawHandler);
    }

    /**
     * Wraps the sustain event handler with a new decorator.
     *
     * @param newBonus function that takes the current handler and returns the decorated one
     */
    public void addSustainBonus(Supplier<ISustainDiscountCharacter> newBonus){
        this.sustainHandler.addSustainDiscountEffect(newBonus.get());
    }

    /**
     * Wraps the current paintings handler with a new decorator.
     *
     * @param decoratorFunc function that takes the current handler and returns the decorated one
     */
    public void addPaintEffect(Function<IPaintHandler, IPaintHandler> decoratorFunc){
        this.paintHandler = decoratorFunc.apply(this.paintHandler);
    }

    /**
     * Change the behavior of the player when winning the ritual event
     * @param newStrategy Constructor of the new Strategy for winning ritual
     */
    public void addRitualWinEffect(Supplier<IRitualWinStrategy> newStrategy){
        this.ritualWinHandler.setStrategy(newStrategy.get());
    }


    /**
     * Change the behavior of the player when losing the ritual event
     * @param newStrategy Constructor of the new Strategy for losing ritual
     */
    public void addRitualLoseEffect(Supplier<IRitualLoseStrategy> newStrategy){
        this.ritualLoseHandler.setStrategy(newStrategy.get());
    }

    public boolean hasBonusDraw(){
        return bonusDraw;
    }

    // No need to pass a boolean to set if the bonus is true or false, the only case when this method is called
    // is when the bonus is added
    /**
     * Permanently grants this player the bonus draw ability.
     * Called once when the player acquires the bonus-draw building card.
     */
    public void addBonusDraw(){
        this.bonusDraw = true;
        observers.onPlayerBonusDrawUpdate(this);
    }

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
