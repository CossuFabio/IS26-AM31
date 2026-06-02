package it.polimi.ingsw.am31.am31.network.messages.updateMessages;

import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;

/** Handler for incoming {@link ErrorMessage} */
public interface ErrorHandler {

    /**
     * @param message The message to handle
     */
    void handleErrorMessage(ErrorMessage message);
}
