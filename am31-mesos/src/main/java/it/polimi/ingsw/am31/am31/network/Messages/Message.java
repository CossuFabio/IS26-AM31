package it.polimi.ingsw.am31.am31.network.Messages;


import it.polimi.ingsw.am31.am31.view.LocalState.StateUpdater;

public abstract class Message {

    public void acceptVisit(IMessageVisitor visitor, StateUpdater updater) {}
}
