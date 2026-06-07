package it.polimi.ingsw.am31.am31.modelPackage.boardFolder;

import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.CardNotFoundException;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObservable;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that represents the board of the game. Contains both the upper and the lower card lines and the offer track.
 */
public class Board implements GameObservable {

    private final ArrayList<OfferCard> offerTrack;
    private final ArrayList<Card> upperLine;
    private final ArrayList<Card> underLine;
    private final ArrayList<BuildingCard> upperBLine;
    private final ArrayList<BuildingCard> underBLine;
    //observable
    private ObserverHandler observers;

    /**
     * @param numPlayers the number of players in the game, required to refresh card lines at the end of the game
     *                   according to rules
     * @param offerCardsCatalog parameter that serves as injection for offer cards resource
     * @throws IOException when the resource injection fails
     */
    public Board(int numPlayers, List<OfferCard> offerCardsCatalog) throws IOException {

        this.offerTrack = (ArrayList<OfferCard>) offerCardsCatalog.stream().filter(c -> c.getMinPlayers() <= numPlayers)
                .collect(Collectors.toList());

        upperLine = new ArrayList<Card>();
        underLine = new ArrayList<Card>();
        upperBLine = new ArrayList<BuildingCard>();
        underBLine = new ArrayList<BuildingCard>();

        this.observers = new GameObserversSet();
    }

    /**
     * Clears the lower card line and moves cards in the upper line to the lower one
     */
    public void moveLowerTribes(){
        underLine.clear();
        underLine.addAll(upperLine);
        upperLine.clear();
        //has to update both upper and lower
        observers.onCardLineUpdate(this, BoardRows.UPPER);
        observers.onCardLineUpdate(this, BoardRows.LOWER);

    }

    /**
     * Clears the lower buildings card line and moves building cards in the upper line to the lower one
     */
    public void moveLowerBuildings(){
        underBLine.clear();
        underBLine.addAll(upperBLine);
        upperBLine.clear();
        //has to update both upper and lower
        observers.onCardLineUpdate(this, BoardRows.UPPER);
        observers.onCardLineUpdate(this, BoardRows.LOWER);
    }

    //ADDERS

    /**
     * @param card BuildingCard added to the upper building card line
     */
    public void addUpper(BuildingCard card){
        upperBLine.add(card);
        observers.onCardLineUpdate(this, BoardRows.UPPER);
    }


    /**
     * @param card Card added to the upper card line
     */
    public void addUpper(Card card){
        upperLine.add(card);
        observers.onCardLineUpdate(this, BoardRows.UPPER);
    }

    /**
     * @param card BuildingCard added to the lower building card line
     */
    public void addLower(BuildingCard card) {
        underBLine.add(card);
        observers.onCardLineUpdate(this, BoardRows.LOWER);
    }

    /**
     * @param card Card added to the lower card line
     */
    public void addLower(Card card){
        underLine.add(card);
        observers.onCardLineUpdate(this, BoardRows.LOWER);
    }

    //GETTERS

    /**
     * @return An immutable list representing the upper buildings line
     */
    public List<BuildingCard> getUpperBLine(){return upperBLine.stream().toList();}

    /**
     * @return An immutable list representing the lower buildings line
     */
    public List<BuildingCard> getUnderBLine(){return underBLine.stream().toList();}

    /**
     * @return An immutable list representing the complete lower card line (characters, events and buildings)
     */
    public List<Card> getUnderLine() {
        ArrayList<Card> result = new ArrayList<Card>();
        result.addAll(underLine);
        result.addAll(underBLine);
        return result;
    }

    /**
     * @return An immutable list representing the complete upper card line (characters, events and buildings)
     */
    public List<Card> getUpperLine(){
        ArrayList<Card> result = new ArrayList<Card>();
        result.addAll(upperLine);
        result.addAll(upperBLine);
        return result;
    }


    /**
     * @param card The pickable card the player wants to draw from the upper line
     * @throws CardNotFoundException if the card is not found in the upper line
     */
    public void drawFromUpper(IPickable card) throws CardNotFoundException{
        if(!(upperBLine.remove(card) || upperLine.remove(card))) throw new CardNotFoundException();
        observers.onCardLineUpdate(this, BoardRows.UPPER);
    }

    /**
     * @param card The pickable card the player wants to draw from the lower line
     * @throws CardNotFoundException if the card is not found in the lower line
     */
    public void drawFromLower(IPickable card) throws CardNotFoundException{
        if(!(underBLine.remove(card) || underLine.remove(card))) throw new CardNotFoundException();
        observers.onCardLineUpdate(this, BoardRows.LOWER);
    }

    /**
     * @return An immutable list of {@link OfferCard} representing the offer track
     */
    public List<OfferCard> getOfferCards(){
        ArrayList<OfferCard> trackCopy = new ArrayList<OfferCard>();
        trackCopy.addAll(offerTrack);
        return trackCopy;
    }




    @Override
    public void setObserverHandler(ObserverHandler obs){

        this.observers = obs;

        //observers = new GameObserversSet();
    }

    private boolean hasCharacters(List<Card> cards){
        CountVisitor countVisitor = new CountVisitor();
        cards.forEach(c -> c.acceptVisit(countVisitor));
        return countVisitor.getTotalCharacters() > 0;
    }

    private boolean hasPickable(List<Card> cards){
        return cards.stream().anyMatch(c -> c.canBePicked());
    }

    /**
     * Helper method to check if the upper card line has pickable cards
     * @return true if the upper line contains cards that can be picked without special conditions like food costs
     */
    public boolean upperLineHasPickable() { return hasPickable(getUpperLine()); }

    /**
     * Helper method to check if the lower card line has pickable cards
     * @return true if the lower line contains cards that can be picked without special conditions like food costs
     */
    public boolean underLineHasPickable() { return hasPickable(getUnderLine()); }

    /**
     * Helper method to check if the upper card line has character cards
     * @return true if the upper line contains at least one CharacterCard
     */
    public boolean upperLineHasCharacters() { return hasCharacters(getUpperLine()); }

    /**
     * Helper method to check if the lower card line has character cards
     * @return true if the lower line contains at least one CharacterCard
     */
    public boolean underLineHasCharacters() { return hasCharacters(getUnderLine()); }





}
