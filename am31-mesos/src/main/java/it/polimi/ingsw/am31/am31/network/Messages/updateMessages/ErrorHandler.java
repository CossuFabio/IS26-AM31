package it.polimi.ingsw.am31.am31.network.Messages.updateMessages;

import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;

public interface ErrorHandler {
    void handleErrorMessage(ErrorMessage message);
}
