package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;


/**
 * Transport-agnostic interface used by the application layer on the client. Allows the client to send {@link NetworkRequest}
 * across the network abstracting from the network protocol.
 */
public interface VirtualServer {

    /**
     * Sends a NetworkRequest to the server
     * @param request the DTO of the request
     * @throws Exception if an error occurs while sending the message
     */
    void sendRequest(NetworkRequest request) throws Exception;

    /**
     * Close the connection to the server
     * @throws Exception if an error occurs when trying to close the connection
     */
    void disconnect() throws Exception;

    /**
     * Sets the player identifier attached to all outgoing requests.
     * @param identifier the player's username
     */
    void setIdentifier(String identifier);

}
