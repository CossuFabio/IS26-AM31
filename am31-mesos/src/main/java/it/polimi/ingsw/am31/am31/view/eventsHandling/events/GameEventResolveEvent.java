package it.polimi.ingsw.am31.am31.view.eventsHandling.events;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEvent;

public class GameEventResolveEvent extends ViewEvent {
    private final Card card;

    public GameEventResolveEvent(Card card) {
        this.card = card;
    }

    public Card getCard() {return card;}
}
