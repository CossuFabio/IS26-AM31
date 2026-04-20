package it.polimi.ingsw.am31.am31.modelPackage.observerPattern;

public interface ObserverHandler extends GameObserver{
    void addObserver(GameObserver o);
    void removeObserver(GameObserver o);
}
