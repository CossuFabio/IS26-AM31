package it.polimi.ingsw.am31.am31.modelPackage.boardFolder;

import it.polimi.ingsw.am31.am31.exceptions.CardNotFoundException;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
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


    public Board(int numPlayers, List<OfferCard> offerCardsCatalog) throws IOException {
        CardLoader loader = new CardLoader();


        this.offerTrack = (ArrayList<OfferCard>) offerCardsCatalog.stream().filter(c -> c.getMinPlayers() <= numPlayers)
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
    public List<BuildingCard> getUpperBLine(){return upperBLine.stream().toList();}
    public List<BuildingCard> getUnderBLine(){return underBLine.stream().toList();}

    public ArrayList<Card> getUnderLine() {
        ArrayList<Card> result = new ArrayList<Card>();
        result.addAll(underLine);
        result.addAll(underBLine);
        return result;
    }
    public ArrayList<Card> getUpperLine(){
        ArrayList<Card> result = new ArrayList<Card>();
        result.addAll(upperLine);
        result.addAll(upperBLine);
        return result;
    }



    //TODO TESTING
    //remove method returns true if the array contains the argument and then it gets removed.
    //The !(buildings.remove || tribe.remove) checks if the card was present, if it wasn't the method throws the exception
    public void drawFromUpper(IPickable card) throws CardNotFoundException{
        if(!(upperBLine.remove(card) || upperLine.remove(card))) throw new CardNotFoundException();

    }

    public void drawFromLower(IPickable card) throws CardNotFoundException{
        if(!(underBLine.remove(card) || underLine.remove(card))) throw new CardNotFoundException();
    }

    public List<OfferCard> getOfferCards(){
        ArrayList<OfferCard> trackCopy = new ArrayList<OfferCard>();
        trackCopy.addAll(offerTrack);
        return trackCopy;
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
//SERVER

//New game(numGIoc, GameResources(SupplierCarte, SupplierOffer, SupplierBuildings))
//New controller(game);

//arriva richiesta pescata
//Card cardRequested = game.gameResources.getTribeCards().searchBYId(requestid)