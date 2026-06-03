package it.polimi.ingsw.am31.am31.network.messages.updateMessages;

/** Handler for incoming {@link UpdateMessage}s */
public interface UpdateHandler {

    /**
     *  @param message The message to handle
     *  */
    void handleUpdate(UpdateMessage message);
}
