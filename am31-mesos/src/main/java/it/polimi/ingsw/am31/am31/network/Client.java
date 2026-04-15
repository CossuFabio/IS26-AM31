package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.exceptions.TooManyPlayersException;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.requests.JoinNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.ShowLobbyNetworkRequest;
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
        String nickname = scanner.nextLine();

        //modifica:
        System.out.println("Enter server IP: ");
        String ip = scanner.nextLine(); // aggiunto

        System.out.println("Enter 1 for RMI, 2 for Socket: ");
        int type = scanner.nextInt();
        VirtualServer connection = null;
        switch (type) {
            //ip is localhost
            //case 1 starts
            case 1: connection = new RmiClient(ip,ServerConfig.SERVER_PORT,nickname);
                    break;
            case 2: connection = new RmiClient(ip,ServerConfig.SERVER_PORT,nickname); //temporary
                break;
            default:
                break;
        }
        Scanner scan = new Scanner(System.in);

        while(true){
            NetworkRequest request = null;
            System.out.println("Type:\n1 - Create a game\n2 - Show the current lobbies\n3 - Join a lobby");
            System.out.print("> ");
            String input = scan.next();

            switch (input) {
                case "1" :
                    System.out.println("Enter the number of players");
                    int nplayers = scan.nextInt();
                    request = new NewGameNetworkRequest(nplayers);
                    break;
                case "2" :
                    request = new ShowLobbyNetworkRequest(nickname);
                    break;
                case "3" :
                    System.out.println("Enter the number of the lobby");
                    int i = scan.nextInt();
                    System.out.println("Enter the color of the totem");
                    Color color = Color.valueOf(scan.next());
                    request = new JoinNetworkRequest(nickname, color, i);
                    break;
            }
            ((VirtualServer) connection).sendRequest(request);
        }

        /*System.out.print("Commands:\ncreateGame [nplayer]\nshowLobbies\njoinLobby [number of lobby] [totem's color]\n");
        while(true) {
            NetworkRequest request = null;
            System.out.print("> ");
            // Receives input commands, create request and sends it to server
            String command = scan.next();
            if(command.equals("createGame") )
            {
                int nplayers = scan.nextInt();
                request = new NewGameNetworkRequest(nplayers);
            }
            else if(command.equals("showLobbies"))
            {
                request = new ShowLobbyNetworkRequest(nickname);
            }
            else if(command.equals("joinLobby"))
            {
                int i = scan.nextInt();
                Color color = Color.valueOf(scan.next());
                request = new JoinNetworkRequest(nickname, color, i);
            }
            else {
                System.out.println("Comando non riconosciuto: " + command);
            }

            if(request != null) {
                ((VirtualServer) connection).sendRequest(request);
            }
        }*/

    }
}
