package it.polimi.ingsw.am31.am31.network.Messages;

import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.view.LocalState.StateUpdater;

public interface IMessageVisitor {
    void visitError(ErrorMessage message);
    void visitUpdate(UpdateMessage message, StateUpdater updater);
}
