package it.polimi.ingsw.am31.am31.modelPackage.deckFolder;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.CardLoader;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.RitualEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TribeDeck extends Deck {

    public TribeDeck(int nPlayers) throws IOException {

    super();
    CardLoader loader = new CardLoader();
    List<Card> catalog = loader.tribeCardLoader();

    //Make the lists of cards
    List<Card> list1 = catalog.stream()
            .filter(c -> c.getEra() == 1 && c.getMinPlayers() <= nPlayers)
            .collect(Collectors.toList());

    List<Card> list2 = catalog.stream()
            .filter(c -> c.getEra() == 2 && c.getMinPlayers() <= nPlayers)
            .collect(Collectors.toList());

    List<Card> list3 = catalog.stream()
            .filter(c -> c.getEra() == 3 && c.getMinPlayers() <= nPlayers)
            .collect(Collectors.toList());



    //After making the lists, shuffle and compose in eraDeck.
    Collections.shuffle(list1);
    Collections.shuffle(list2);
    Collections.shuffle(list3);

    this.eraDeck.addAll(list1);
    this.eraDeck.addAll(list2);
    this.eraDeck.addAll(list3);
    this.eraDeck.add(new SustainEventCard(3,3));
    this.eraDeck.add(new RitualEventCard( 3,7,15));
    }


}