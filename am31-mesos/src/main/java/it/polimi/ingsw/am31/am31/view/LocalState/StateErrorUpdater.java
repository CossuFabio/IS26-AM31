package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IErrorVisitor;

public class StateErrorUpdater implements IErrorVisitor {

    private LocalGameState localState;

    public StateErrorUpdater(LocalGameState localState){
        this.localState = localState;
    }




}
