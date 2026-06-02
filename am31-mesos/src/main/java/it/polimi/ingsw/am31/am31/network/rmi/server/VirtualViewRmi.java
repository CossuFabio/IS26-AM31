package it.polimi.ingsw.am31.am31.network.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Intra-protocol RMI callback interface; the server calls these methods on the client stub to push data
 * to a connected client.
 * <p>Parameters are JSON strings: {@link String} is already {@link java.io.Serializable} and travels over
 * RMI without extra work, so the DTO root classes ({@code NetworkRequest}, {@code Message}) do not need
 * to implement {@link java.io.Serializable}
 */
public interface VirtualViewRmi extends Remote {

    /**
     * Delivers a JSON-serialized update message to the client
     *
     * @param data the serialized UpdateMessage
     * @throws RemoteException if the RMI call fails
     */
    void receiveUpdate(String data) throws RemoteException;

    /**
     * Delivers a JSON-serialized error message to the client
     *
     * @param errorMessage the serialized ErrorMessage
     * @throws RemoteException if the RMI call fails
     */
    void receiveErrorMessage(String errorMessage) throws RemoteException;

}
