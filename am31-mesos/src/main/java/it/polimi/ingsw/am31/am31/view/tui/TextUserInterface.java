package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.requests.JoinNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.view.LocalGameState;
import it.polimi.ingsw.am31.am31.view.View;

import java.util.Scanner;

public class TextUserInterface implements View {
    private final ClientController controller;
    private final LocalGameState gameState;

    public TextUserInterface (ClientController controller){
        this.controller=controller;
        this.gameState = null;
        //gamestate starts as null, doesnt have a default.
    }
//class for visualization via CLI
    @Override
    public void Start() throws Exception {
        Scanner scanner = new Scanner(System.in);
        NetworkRequest request = null;
        System.out.println("Type:\n1 - Create a game\n2 - Show the current lobbies\n3 - Join a lobby\n4 - Place your Totem\n5 - Draw a card");
        System.out.print("> ");
        String input = scanner.next();

        switch (input) {
            case "1" :
                int nplayers = 1;
                while (nplayers < 2 || nplayers > 5)
                {
                    System.out.println("Enter the number of players (2-5)");
                    nplayers = scanner.nextInt();
                    if (nplayers < 2 || nplayers > 5) System.out.println("Invalid number!");
                }
                request = new NewGameNetworkRequest(nplayers);
                break;
            case "2" :
                request = new ShowLobbyNetworkRequest();
                break;
            case "3" :
                System.out.println("Enter the number of the lobby");
                int i = scanner.nextInt();
                Color color = null;
                while (color == null)
                {
                    System.out.println("Enter the color of the totem (white, black, red, yellow, blue)");
                    try
                    {
                        color = Color.valueOf(scanner.next().toUpperCase());
                        request = new JoinNetworkRequest(color, i);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.out.println("Invalid color!");
                    }
                }
                break;
            case "4" :
                System.out.println("Enter your chosen OfferCard");
                //TODO: Implement rest
                break;

        }
        controller.sendRequest(request);
    }

    @Override
    public void PrintScreen() {


    }
}
