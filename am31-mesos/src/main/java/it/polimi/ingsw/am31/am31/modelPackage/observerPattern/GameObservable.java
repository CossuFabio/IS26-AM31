package it.polimi.ingsw.am31.am31.modelPackage.observerPattern;

/**
 * Domain classes that need to notify players about changes on their states
 */
public interface GameObservable {
    /**
     * Changes the observer handler for the GameObservable
     * @param observer the data structure that handles all the observers
     */
    public void setObserverHandler(ObserverHandler observer);
}
