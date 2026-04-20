package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.exceptions.TooManyPlayersException;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.requests.*;
import it.polimi.ingsw.am31.am31.network.rmi.client.RmiClient;


import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class Client {
    //main client class
    public static void main(String[] args) throws IOException, NotBoundException, TooManyPlayersException, Exception  {
        Scanner scanner = new Scanner(System.in);
        ClientController controller = new ClientController();
        System.out.println("Enter Nickname: ");
        String nickname = args[0];//scanner.nextLine();

        //modifica:
        System.out.println("Enter server IP: ");
        //String ip = scanner.nextLine(); // aggiunto

        //FOR TESTING: uncomment this to use a fixed ip.
        String ip = "127.0.0.1";

        System.out.println("Enter 1 for RMI, 2 for Socket: ");
        int type = 1;  //scanner.nextInt();
        int newport = Integer.parseInt(args[1]);
        VirtualServer connection = null;
        switch (type) {
            //ip is localhost
            //case 1 starts
            case 1: connection = new RmiClient(ip,newport,nickname);
                    break;
            case 2: connection = new RmiClient(ip,ServerConfig.SERVER_PORT_SOCKET,nickname); //temporary
                break;
            default:
                break;
        }
        //starts pinging every 5 seconds
        Ping(connection);
        while(true){
            NetworkRequest request = null;
            System.out.println("Type:\n1 - Create a game\n2 - Show the current lobbies\n3 - Join a lobby");
            System.out.print("> ");
            String input = scanner.next();

            switch (input) {
                case "1" :
                    System.out.println("Enter the number of players");
                    int nplayers = scanner.nextInt();
                    request = new NewGameNetworkRequest(nplayers);
                    break;
                case "2" :
                    request = new ShowLobbyNetworkRequest();
                    break;
                case "3" :
                    System.out.println("Enter the number of the lobby");
                    int i = scanner.nextInt();
                    System.out.println("Enter the color of the totem");
                    Color color = Color.valueOf(scanner.next());
                    request = new JoinNetworkRequest(color, i);
                    break;
            }
            ((VirtualServer) connection).sendRequest(request);
        }
    }
    public static void Ping (VirtualServer connection) {
        Thread pingThread = new Thread (() -> {
            while(true) {
                try{
                    Thread.sleep(5000);
                    NetworkRequest ping = new PingNetworkRequest();
                    connection.sendRequest(ping);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    System.out.println("Server Error");
                    try {
                        connection.disconnect();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                }
                }

        });
        pingThread.setDaemon(true);
        pingThread.start();
    }
}
