package it.polimi.ingsw.am31.am31.modelPackage.deckFolder;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

//Like the TribeDeck, we do not shuffle here, but we just take out the requested buildings from the rules.
//This class allows injection for test cases

public class BuildingDeck extends Deck {



    // Exceptions are silently caught.
    // This allows us to use this for testing, but we need to be sure that the injected catalogue is consistent for
    // the game.
    public BuildingDeck(int nPlayers, List<BuildingCard> catalog){
        super();

        List<BuildingCard> list1 = catalog.stream().filter(c -> c.getEra() == 1).collect(Collectors.toList());
        List<BuildingCard> list2 = catalog.stream().filter(c -> c.getEra() == 2).collect(Collectors.toList());
        List<BuildingCard> list3 = catalog.stream().filter(c -> c.getEra() == 3).collect(Collectors.toList());

        try {
            for (int i = 0; i < GameConstants.getEraOneBuildings(nPlayers); i++) {
                this.eraDeck.add(list1.getFirst());
                list1.removeFirst();
            }
        } catch (NoSuchElementException ignored) {

        }

        try {
            for (int i = 0; i < GameConstants.getEraTwoBuildings(nPlayers); i++) {
                this.eraDeck.add(list2.getFirst());
                list2.removeFirst();
            }
        } catch (NoSuchElementException ignored) {}

        try {
            for (int i = 0; i < GameConstants.getEraThreeBuildings(nPlayers); i++) {
                this.eraDeck.add(list3.getFirst());
                list3.removeFirst();
            }
        } catch (NoSuchElementException ignored) {}


    }
}

