package it.polimi.ingsw.am31.am31.network.Messages;

import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;

public class MessageVisitor implements IMessageVisitor {

    public MessageVisitor(){}

    @Override
    public void visitError(ErrorMessage message) {
        System.out.println("[ERROR] " + message.getMessage());
    }

    @Override
    public void visitUpdate(UpdateMessage message) {
        if(message != null && message.checkValidity())
            System.out.println(message.getUpdateType());
    }
}