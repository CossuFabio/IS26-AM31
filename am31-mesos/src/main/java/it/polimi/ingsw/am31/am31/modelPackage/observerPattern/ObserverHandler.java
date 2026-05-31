package it.polimi.ingsw.am31.am31.modelPackage.observerPattern;

/**
 * Defines method for a data structure that handles observers
 */
public interface ObserverHandler extends GameObserver{

    /**
     * Add an observer to the handler if not already present, otherwise silently returns.
     * @param o the new observer
     */
    void addObserver(GameObserver o);

    /**
     * Remove the observer from the structure. The comparison in reference based.
     * If the observer does not exist, silently return.
     * @param o the observer that should be removed
     */
    void removeObserver(GameObserver o);

    /**
     * Remove the observer from the structure. The comparison in based on the identifier.
     * If the observer does not exist, silently return.
     * @param identifier the identifier of the observer that should be removed
     */
    void removeObserver(String identifier);
}
