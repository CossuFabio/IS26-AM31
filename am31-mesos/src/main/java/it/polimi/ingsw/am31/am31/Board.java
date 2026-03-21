package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.BuildingCard;
import it.polimi.ingsw.am31.am31.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final ArrayList<OfferCard> offerTrack;
    private final ArrayList<Card> upperLine;
    private final ArrayList<Card> underLine;
    private final ArrayList<BuildingCard> upperBLine;
    private final ArrayList<BuildingCard> underBLine;


    public Board(int numPlayers) {
        offerTrack = new ArrayList<OfferCard>();

        upperLine = new ArrayList<Card>();
        underLine = new ArrayList<Card>();

        upperBLine = new ArrayList<BuildingCard>();
        underBLine = new ArrayList<BuildingCard>();
    }

    public void moveLowerTribes(){
        underLine.clear();
        underLine.addAll(upperLine);
        upperLine.clear();
    }
    public void moveLowerBuildings(){
        underBLine.clear();
        underBLine.addAll(upperBLine);
        upperBLine.clear();
    }
//ADDERS
    public void addUpper(Card card){
        upperLine.add(card);
    }
    public void addLower(Card card){
        underLine.add(card);
    }
    public void addBuildingUpper(BuildingCard card){
        upperBLine.add(card);
    }
    public void addBuildingLower(BuildingCard card){
        underBLine.add(card);
    }
//GETTERS
    public ArrayList<BuildingCard> getUnderBLine(){return underBLine;}
    public ArrayList<Card> getUnderLine() {return underLine;}
    public ArrayList<Card> getUpperLine(){
        ArrayList<Card> result = new ArrayList<Card>();
        result.addAll(upperLine);
        result.addAll(upperBLine);
        return result;
    }
//TODO TESTING
    public Card drawFromUpper(Card card){
        if(upperLine.contains(card)){
            upperLine.remove(card);
            return card;
        }
        else if(upperBLine.contains(card)){
            upperBLine.remove(card);
            return card;
        }
        return null;
    }

    public Card drawFromLower(Card card){

        if(underLine.contains(card)){
            underLine.remove(card);
            return card;
        }
        else if(underBLine.contains(card)){
            underBLine.remove(card);
            return card;
        }
        return null;
    }


    //TODO implement
    public void showBoardState(){

    }

    public List<OfferCard> getOfferCards(){
        return offerTrack;
    }

    public OfferCard getNextCard(int currentCard) {
        OfferCard nextOfferCard = null;
        if (currentCard < getOfferTrackSize() - 1 ){
            nextOfferCard = offerTrack.get(currentCard+1);
        }
        return nextOfferCard;
    }

    public int getOfferTrackSize(){return offerTrack.size();}



}
