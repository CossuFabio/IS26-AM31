package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;

import java.util.ArrayList;
import java.util.List;

public class LocalBoardState {
    private ArrayList<LocalOfferCard> offerTrack;
    private ArrayList<Card> upperLine;
    private ArrayList<Card> underLine;
    public LocalBoardState (){
        upperLine = new ArrayList<>();
        underLine = new ArrayList<>();
        offerTrack = new ArrayList<>();
    }


    public void setOfferTrack(ArrayList<LocalOfferCard> offerTrack){
        this.offerTrack = offerTrack;
//could move this to localGameState?
    }
    public void setUpperLine(List<Card> cards){
        upperLine = new ArrayList<>();
        upperLine.addAll(cards);
    }
    public void setUnderLine(List<Card> cards){
        underLine = new ArrayList<>();
        underLine.addAll(cards);
    }
    //getters, use by tui and gui
    public ArrayList<Card> getUpperLine(){return upperLine;}
    public ArrayList<Card> getUnderLine(){return underLine;}
    public ArrayList<LocalOfferCard> getOfferTrack (){return offerTrack;}
}