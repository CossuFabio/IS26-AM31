package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;

import java.util.ArrayList;
import java.util.List;

public class LocalBoardState {
    private List<LocalOfferCard> offerTrack;
    private List<Card> upperLine;
    private List<Card> underLine;
    public LocalBoardState (){
        upperLine = new ArrayList<>();
        underLine = new ArrayList<>();
        offerTrack = new ArrayList<>();
    }


    public void setOfferTrack(List<LocalOfferCard> offerTrack){
        this.offerTrack = offerTrack;
    }
    public void setUpperLine(List<Card> cards){
        upperLine = new ArrayList<>();
        upperLine.addAll(cards);
    }
    public void setUnderLine(List<Card> cards){
        underLine = new ArrayList<>();
        underLine.addAll(cards);
    }

    public void reset(){
        upperLine = new ArrayList<>();
        underLine = new ArrayList<>();
        offerTrack = new ArrayList<>();
    }


    //getters, use by tui and gui
    public List<Card> getUpperLine(){return upperLine;}
    public List<Card> getUnderLine(){return underLine;}
    public List<LocalOfferCard> getOfferTrack (){return offerTrack;}
}