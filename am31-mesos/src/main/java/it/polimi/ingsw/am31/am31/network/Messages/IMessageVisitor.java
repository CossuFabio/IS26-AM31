package it.polimi.ingsw.am31.am31.network.messages;

import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;

public interface IMessageVisitor {
    void visitError(ErrorMessage message);
    void visitUpdate(UpdateMessage message);
}
