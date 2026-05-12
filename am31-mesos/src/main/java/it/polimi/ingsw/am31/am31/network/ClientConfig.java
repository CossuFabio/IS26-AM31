package it.polimi.ingsw.am31.am31.network;

public class ClientConfig {
    public static final int CLIENT_HEARTBEAT_INTERVAL = 3000;
    //Using zero we don't need to select the port, the OS will find the first free port
    public static final int CLIENT_PORT = 0;

    //STARTING ID FOR UNREGISTERED CLIENT
    public static final String UNREGISTERED_CLIENT_ID = "<UNREGISTERED_CLIENT>";

}
