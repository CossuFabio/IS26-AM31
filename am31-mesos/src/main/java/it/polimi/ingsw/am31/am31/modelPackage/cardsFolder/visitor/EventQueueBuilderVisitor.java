package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Builds the ordered event queue from a player's event cards.
 * Sustain events are placed last; all other event types are resolved first.
 * Within each group, events are sorted by era.
 */
public class EventQueueBuilderVisitor implements TribeVisitor{

    private final List<EventCard> lowPriorityQueue;
    private final List<EventCard> highPriorityQueue;

    public EventQueueBuilderVisitor(){
        this.highPriorityQueue = new ArrayList<>();
        this.lowPriorityQueue = new ArrayList<>();
    }

    /*NO OP FOR ALL OTHER TYPES*/

    @Override
    public void visit(Hunter hunter) {

    }

    @Override
    public void visit(Shaman shaman) {

    }

    @Override
    public void visit(Farmer farmer) {

    }

    @Override
    public void visit(Inventor inventor) {
    }

    @Override
    public void visit(Builder builder) {

    }

    @Override
    public void visit(Artist artist) {

    }

    @Override
    public void visit(BuildingCard building) {

    }

    @Override
    public void visit(HuntEventCard event) {
        highPriorityQueue.add(event);
    }

    @Override
    public void visit(SustainEventCard event) {
        lowPriorityQueue.add(event);
    }

    @Override
    public void visit(RitualEventCard event) {
        highPriorityQueue.add(event);
    }

    @Override
    public void visit(PaintingEventCard event) {
        highPriorityQueue.add(event);
    }

    /**
     * Returns the complete ordered event queue: non-sustain events by era first,
     * sustain events by era last.
     */
    public List<EventCard> getCompleteQueue(){

        List<EventCard> sortedHighPriority = highPriorityQueue
                .stream()
                .sorted(Comparator.comparingInt(EventCard::getEra))
                .toList();

        List<EventCard> sortedLowPriority = lowPriorityQueue
                .stream()
                .sorted(Comparator.comparingInt(EventCard::getEra))
                .toList();

        return Stream.concat(sortedHighPriority.stream(), sortedLowPriority.stream())
                .toList();
    }

}
