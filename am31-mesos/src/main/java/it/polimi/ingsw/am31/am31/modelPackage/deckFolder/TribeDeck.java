package it.polimi.ingsw.am31.am31.modelPackage.deckFolder;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.RitualEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that represents the deck of tribe cards (Characters and Events)
 */
public class TribeDeck extends Deck {

    /**
     * Creates the main deck, filtering out cards that require more players than the current game has
     * @param nPlayers the number of players, used to filter out cards with a higher minPlayers requirement
     * @param catalog resource injection of the tribe cards that compose the deck.<br> They must be already shuffled
     */
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