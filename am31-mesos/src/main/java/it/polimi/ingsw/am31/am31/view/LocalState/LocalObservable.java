package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;

public interface LocalObservable {
    void addObserver(LocalObserver observer);
}
