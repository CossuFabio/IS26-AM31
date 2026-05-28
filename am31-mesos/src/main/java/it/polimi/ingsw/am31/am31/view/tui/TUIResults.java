package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LeaderBoardEntryUpdate;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalLeaderBoard;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.BoardUpdateEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.ReturnToLobbyEvent;
import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.Ansi;

import java.util.List;

import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.*;
import static org.fusesource.jansi.Ansi.ansi;

public class TUIResults implements TUIPhase {



    private enum TuiResultsStep {SHOW_RESULTS, SHOW_TRIBES, BACK_TO_LOBBY}

    private TuiResultsStep currentStep;
    private LocalGameState gameState;
    private final ClientController controller;
    private final TextUserInterface TUI;

    public TUIResults(TextUserInterface TUI, ClientController controller, LocalGameState gameState){
        this.TUI = TUI;
        this.controller = controller;
        this.gameState = gameState;
        this.currentStep = TuiResultsStep.SHOW_RESULTS;
    }


    @Override
    public void draw() {
        switch (currentStep) {
            case SHOW_RESULTS: {
                drawResults(); break;
            }
            case SHOW_TRIBES: {
                drawTribes();break;
            }
            case BACK_TO_LOBBY: {
                System.out.println("Going back to lobby...");
                break;
            }
        }
    }

    @Override
    public void handleInput(String input) {
        if (input == null || input.isBlank())
            return;
        switch(currentStep) {
            case SHOW_RESULTS: {
                switch (input) {
                    case "1": {
                        currentStep = TuiResultsStep.BACK_TO_LOBBY;
                        controller.getEventBus().post(new ReturnToLobbyEvent());
                        break;
                    }
                    case "2": {
                        currentStep = TuiResultsStep.SHOW_TRIBES;
                        TUI.printScreen();
                        break;
                    }
                    default:{
                        System.out.println("Invalid input!");
                        TUI.printScreen();
                        break;
                    }
                }
                break;
            }
            case SHOW_TRIBES: {
                if(input.equals("1"))
                    currentStep = TuiResultsStep.SHOW_RESULTS;
                else
                    System.out.println("Invalid input!");
                TUI.printScreen();
                break;
            }
            case BACK_TO_LOBBY: {
                break;
            }
        }
    }


    void drawResults (){
        String localPlayer = controller.getLocalPlayerUsername();
        String fixedMessage = "";
        System.out.println(upperBorder(RESULT_TITLE_SIZE));
        //if client won, shows special message
        if (gameState.getLeaderboard()
                .stream()
                .anyMatch(s -> s.playerState().getNickname().equals(localPlayer) && s.isWinner())){
            fixedMessage = "YOU WON";

            System.out.println(coloredInsideBorder(fixedMessage,RESULT_TITLE_SIZE, Ansi.Color.GREEN));
        }
        else {
            fixedMessage ="GAME ENDED";
            System.out.println(coloredInsideBorder(fixedMessage,RESULT_TITLE_SIZE, Ansi.Color.GREEN));
        }System.out.println(lowerBorder(RESULT_TITLE_SIZE));

        System.out.println("RESULTS:");
        //prints leaderboard
        for (LocalLeaderBoard p : gameState.getLeaderboard()) {
            boolean pWon = p.isWinner();
            String score = "";
            if (pWon)
                score = "WINNER: ";
            score = score + getColor(p.playerState()) + p.playerState().getNickname() + ansi().reset() + " | " + p.playerState().getPrestigePoints() + POINTS+" "+ p.playerState().getFood() + FOOD;
            int size = score.length()+20;
            System.out.println(upperBorder(size));
            if (pWon)
                System.out.println(insideBorder(score,size));
            else
                System.out.println(score);
            System.out.println(lowerBorder(size));
        }
        System.out.println();
        System.out.println("Press: \n1 - To go back to Lobby"+"\n2 - To show players' tribes");
    }

    void drawTribes (){
        for(LocalPlayerState p : gameState.getPlayers()) {
        System.out.println(p.toString());
        for (Card c : p.getTribe())
            System.out.println(c.toString());
        for (Card c : p.getBuildings())
            System.out.println(c.toString());
    }
        ansi().reset();
        System.out.println("Press 1 to go back to results");}

    @Subscribe
    public void handleBoardUpdate(BoardUpdateEvent e) {
        TUI.printScreen();
    }

}
