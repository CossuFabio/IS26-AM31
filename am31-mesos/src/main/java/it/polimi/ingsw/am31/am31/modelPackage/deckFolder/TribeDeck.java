package it.polimi.ingsw.am31.am31.modelPackage.deckFolder;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.RitualEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;

import java.util.List;
import java.util.stream.Collectors;

//This class does not care about shuffling the deck, it just filters for minPlayers.
//This allows us to inject non-shuffled decks for testing
public class TribeDeck extends Deck {

    public TribeDeck(int nPlayers, List<Card> catalog){

    super();

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



        this.eraDeck.addAll(list1);
        this.eraDeck.addAll(list2);
        this.eraDeck.addAll(list3);
        }


}