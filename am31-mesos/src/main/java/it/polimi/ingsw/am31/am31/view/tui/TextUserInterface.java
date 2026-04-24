package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.requests.JoinNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.view.LocalGameState;
import it.polimi.ingsw.am31.am31.view.View;

import java.util.Scanner;

public class TextUserInterface implements View, ObserverHandler {
    private final ClientController controller;
    private LocalGameState gameState;

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

    @Override
    public void onPlayerNewBuildingEvent(Player player) {

    }

    @Override
    public void onPlayerScoresUpdate(Player player) {

    }

    @Override
    public void onPlayerTribeUpdate(Player player) {

    }

    @Override
    public void onGameRoundStatusUpdate(Game game) {

    }

    @Override
    public void onPlayersListUpdate(Game game) {

    }

    @Override
    public void onCardLineUpdate(Board board, BoardRows row) {

    }

    @Override
    public void onOfferTrackUpdate(Board board) {

    }

    @Override
    public void onTurnOrderUpdate(Board board) {

    }

    @Override
    public void onGameStartUpdate(LocalGameState  newGame) {
        this.gameState=newGame;
        gameState.addObserver(this);
        //this.GameStart(), changes interface into game interface, no more join lobby, create lobby, etc...
    }

    @Override
    public void addObserver(GameObserver o) {

    }

    @Override
    public void removeObserver(GameObserver o) {

    }
}
