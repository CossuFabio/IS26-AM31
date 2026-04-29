package it.polimi.ingsw.am31.am31.modelPackage.deckFolder;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingDeck extends Deck {



    public BuildingDeck(int nplayers, List<BuildingCard> catalog) {

        List<BuildingCard> list1 = catalog.stream().filter(c -> c.getEra() == 1).collect(Collectors.toList());
        List<BuildingCard> list2 = catalog.stream().filter(c -> c.getEra() == 2).collect(Collectors.toList());
        List<BuildingCard> list3 = catalog.stream().filter(c -> c.getEra() == 3).collect(Collectors.toList());

        //lists are shuffled, then we add cards based on nplayers
        Collections.shuffle(list1);
        Collections.shuffle(list2);
        Collections.shuffle(list3);

        for (int i = 0; i < GameConstants.getEraOneBuildings(nplayers); i++) {
            this.eraDeck.add(list1.getFirst());
            list1.removeFirst();
        }

        for (int i = 0; i < GameConstants.getEraTwoBuildings(nplayers); i++) {
            this.eraDeck.add(list2.getFirst());
            list2.removeFirst();
        }

        for (int i = 0; i < GameConstants.getEraThreeBuildings(nplayers); i++) {
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
        }



    }
}

