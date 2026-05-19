package it.polimi.ingsw.am31.am31.network;

public class ServerConfig {
    
    public static final int SERVER_PORT_RMI = 1100;
    public static final int SERVER_PORT_SOCKET = 1200;
    public static final String SERVER_NAME = "MesosServer";
    public static  String SERVER_IP_ADDRESS = "127.0.0.1";


    public static final int HEARTBEAT_SERVER_INTERVAL = 5000;
    public static final int HEARTBEAT_TIMEOUT = 3*ClientConfig.CLIENT_HEARTBEAT_INTERVAL;

    //10 minutes timeout
    public static final long WAITING_ROOM_TIMEOUT = 10 * 1000 * 60; //minutes number[1] * second/millisecond * minute/second

}
