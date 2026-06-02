package it.polimi.ingsw.am31.am31.network.messages;

import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;

/**
 * Visitor interface that routes an incoming {@link Message} to the correct handler.
 * Distinguishes between {@link ErrorMessage} and {@link UpdateMessage}
 */
public interface IMessageVisitor {
    void visitError(ErrorMessage message);
    void visitUpdate(UpdateMessage message);
}
