package it.polimi.ingsw.am31.am31.view.localState;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.view.tui.TuiColorVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the local state of the game board.
 */
public class LocalBoardState
{
    private List<LocalOfferCard> offerTrack;
    private List<Card> upperLine;
    private List<Card> underLine;

    /**
     * Constructor for the Local Board
     */
    public LocalBoardState (){
        upperLine = new ArrayList<>();
        underLine = new ArrayList<>();
        offerTrack = new ArrayList<>();
    }

    /**
     * Sets the offer track with a new list of local offer cards.
     * @param offerTrack The new list of LocalOfferCard objects.
     */
    public void setOfferTrack(List<LocalOfferCard> offerTrack){
        this.offerTrack = offerTrack;
    }
    /**
     * Sets the upper line of cards on the board.
     * @param cards The new list of Card objects for the upper line.
     */
    public void setUpperLine(List<Card> cards){
        upperLine = new ArrayList<>();
        upperLine.addAll(cards);
    }
    /**
     * Sets the under line of cards on the board.
     * @param cards The new list of Card objects for the under line.
     */
    public void setUnderLine(List<Card> cards){
        underLine = new ArrayList<>();
        underLine.addAll(cards);
    }

    /**
     * Resets the board state by clearing all card lines and the offer track.
     */
    public void reset(){
        upperLine = new ArrayList<>();
        underLine = new ArrayList<>();
        offerTrack = new ArrayList<>();
    }


    //getters, use by tui and gui
    /**
     * Returns the upper line of cards on the board.
     * @return A list of Card objects representing the upper line.
     */
    public List<Card> getUpperLine(){return upperLine;}
    /**
     * Returns the under line of cards on the board.
     * @return A list of Card objects representing the under line.
     */
    public List<Card> getUnderLine(){return underLine;}
    /**
     * Returns the offer track.
     * @return A list of LocalOfferCard objects representing the offer track.
     */
    public List<LocalOfferCard> getOfferTrack (){return offerTrack;}
    /**
     * Returns the upper line of cards, excluding building cards.
     * @return A list of Card objects that are not buildings from the upper line.
     */
    public List<Card> getUpperLineNOB(){
        TuiColorVisitor visitor = new TuiColorVisitor();
        return upperLine.stream().filter(carta -> !visitor.checkBuilding(carta)).toList();
    }
    /**
     * Returns the under line of cards, excluding building cards.
     * @return A list of Card objects that are not buildings from the under line.
     */
    public List<Card> getUnderLineNOB(){
        TuiColorVisitor visitor = new TuiColorVisitor();
        return underLine.stream().filter(carta -> !visitor.checkBuilding(carta)).toList();
    }
    /**
     * Returns the upper line of cards, including only building cards.
     * @return A list of Card objects that are buildings from the upper line.
     */
    public List<Card> getUpperLineB(){
        TuiColorVisitor visitor = new TuiColorVisitor();
        return upperLine.stream().filter(carta -> visitor.checkBuilding(carta)).toList();
    }
    /**
     * Returns the under line of cards, including only building cards.
     * @return A list of Card objects that are buildings from the under line.
     */
    public List<Card> getUnderLineB(){
        TuiColorVisitor visitor = new TuiColorVisitor();
        return underLine.stream().filter(carta -> visitor.checkBuilding(carta)).toList();
    }
}