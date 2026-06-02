package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;

/**
 * Transport-agnostic interface used by the application layer on the server. Allows sending updates and
 * error messages using DTOs.
 */
public interface VirtualView {

    /**
     * Allows the server to send UpdateMessage across the network
     * @param data the DTO carrying information about the update
     * @throws Exception if an error occurs while sending the message
     */
    void receiveUpdate(UpdateMessage data) throws Exception;

    /**
     * Allows the server to send ErrorMessage across the network caused by the receiver of this message.
     * @param error the DTO carrying information about the error
     * @throws Exception if an error occurs while sending the message
     */
    void receiveErrorMessage(ErrorMessage error);

    /**
     * Updates the timestamp of the last request received from this client. Used for the heartbeat mechanism.
     */
    void updateLastTime();

    /**
     * Called by the heartbeat daemon thread on the server
     * @return the timestamp of the last request received from this client
     */
    long getLastTime();

    /**
     * Closes the transport connection
     */
    void forceDisconnect();

}
