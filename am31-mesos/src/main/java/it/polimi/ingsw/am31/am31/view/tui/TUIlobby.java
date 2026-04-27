package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;

import java.util.Scanner;

public class TUIlobby implements TUIPhase {
    private final TextUserInterface TUI;
    private final ClientController controller;

    public TUIlobby(TextUserInterface TUI, ClientController controller) {
        this.TUI = TUI;
        this.controller = controller;
    }

    public void draw() throws Exception {
            NetworkRequest request = null;
            Scanner scanner= new Scanner(System.in);
            String input = scanner.next();
            System.out.println("Type:\n1 - Create a game\n2 - Show the current lobbies\n3 - Join a lobby\n4 - Place your Totem\n5 - Draw a card");
            //the "lobby phase"
            switch (input) {
                case "1":
                    int nplayers = 1;
                    while (nplayers < 2 || nplayers > 5) {
                        System.out.println("Enter the number of players (2-5)");
                        nplayers = scanner.nextInt();
                        if (nplayers < 2 || nplayers > 5) System.out.println("Invalid number!");
                    }
                    Color colorz = null;
                    while(colorz == null) {
                        System.out.println("Choose your totem's color (white, black, red, yellow, blue)");
                        try {
                            colorz = Color.valueOf(scanner.next().toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid color!");
                        }
                    }
                    request = new NewGameNetworkRequest(nplayers, colorz);
                    break;
                case "2":
                    request = new ShowLobbyNetworkRequest();
                    break;
                case "3":
                    System.out.println("Enter the number of the lobby");
                    int i = scanner.nextInt();
                    Color color = null;
                    while (color == null) {
                        System.out.println("Enter the color of the totem (white, black, red, yellow, blue)");
                        try {
                            color = Color.valueOf(scanner.next().toUpperCase());
                            request = new JoinGameNetworkRequest(color, i);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid color!");
                        }
                    }
            }      controller.sendRequest(request);
    }
}
