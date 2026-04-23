package it.polimi.ingsw.am31.am31.view;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;

import java.util.ArrayList;

public class LocalBoardState {
    private ArrayList<OfferCard> offerTrack;
    private ArrayList<Card> upperLine;
    private ArrayList<Card> underLine;
    private ArrayList<BuildingCard> upperBLine;
    private ArrayList<BuildingCard> underBLine;
}
