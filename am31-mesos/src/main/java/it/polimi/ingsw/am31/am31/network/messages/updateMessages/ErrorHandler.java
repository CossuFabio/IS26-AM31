package it.polimi.ingsw.am31.am31.network.messages.updateMessages;

import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;

public interface ErrorHandler {
    void handleErrorMessage(ErrorMessage message);
}
