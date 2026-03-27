package it.polimi.ingsw.am31.am31.modelPackage.boardFolder;

import it.polimi.ingsw.am31.am31.exceptions.CardNotFoundException;
import it.polimi.ingsw.am31.am31.exceptions.InsufficientFoodException;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.CardLoader;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    private final ArrayList<OfferCard> offerTrack;
    private final ArrayList<Card> upperLine;
    private final ArrayList<Card> underLine;
    private final ArrayList<BuildingCard> upperBLine;
    private final ArrayList<BuildingCard> underBLine;


    public Board(int numPlayers) throws IOException {
        super();
        CardLoader loader = new CardLoader();
        List<OfferCard> catalog = loader.offerCardLoader();
        this.offerTrack = (ArrayList<OfferCard>) catalog.stream().filter(c -> c.getMinPlayers() <= numPlayers)
                .collect(Collectors.toList());

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
    public void addUpper(BuildingCard card){
        upperBLine.add(card);
    }
    public void addUpper(Card card){
        upperLine.add(card);
    }
    public void addLower(BuildingCard card) { underBLine.add(card);}
    public void addLower(Card card){
        underLine.add(card);
    }
//GETTERS
public ArrayList<BuildingCard> getUpperBLine(){return upperBLine;}
    public ArrayList<BuildingCard> getUnderBLine(){return underBLine;}
    public ArrayList<Card> getUnderLine() {return underLine;}
    public ArrayList<Card> getUpperLine(){
        ArrayList<Card> result = new ArrayList<Card>();
        result.addAll(upperLine);
        result.addAll(upperBLine);
        return result;
    }

//TODO TESTING
    //TODO: BAD - MUST REDO
    public void drawFromUpper(CharacterCard card) throws CardNotFoundException{
        if(upperLine.contains(card))
            upperLine.remove(card);
        else throw new CardNotFoundException();

    }

    public void drawFromLower(CharacterCard card) throws CardNotFoundException{
        if(underLine.contains(card))
            underLine.remove(card);
        else
            throw new CardNotFoundException();
    }

    public void drawFromUpper(BuildingCard card) throws CardNotFoundException{
        if(upperBLine.contains(card))
            upperBLine.remove(card);
        else throw new CardNotFoundException();

    }

    public void drawFromLower(BuildingCard card) throws CardNotFoundException{
        if(underBLine.contains(card))
            underBLine.remove(card);
        else
            throw new CardNotFoundException();
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
