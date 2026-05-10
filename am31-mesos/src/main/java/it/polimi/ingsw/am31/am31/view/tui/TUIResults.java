package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LeaderBoardEntryUpdate;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalLeaderBoard;

import java.util.List;

public class TUIResults implements TUIPhase {



    private enum TuiResultsStep {SHOW_RESULTS, MAIN, SHOW_TRIBES, RANKING}

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
        switch (currentStep){
            case SHOW_RESULTS:
                String localPlayer = controller.getLocalPlayerUsername();
                //if client won, shows special message
                if(gameState.getLeaderboard()
                        .stream()
                        .anyMatch(s->s.playerState().getNickname().equals(localPlayer) && s.isWinner()))
                    System.out.println("YOU WON!");
                //prints leaderboard
                for(LocalLeaderBoard p : gameState.getLeaderboard()) {
                    boolean pWon = p.isWinner();
                    if(pWon)
                        System.out.println("WINNER:"+ p.playerState().getNickname()+" | "+p.playerState().getPrestigePoints()+" Prestige Points and "+p.playerState().getFood()+" food |");
                    else
                        System.out.println(p.playerState().getNickname() + " | "+p.playerState().getPrestigePoints()+" Prestige Points and "+p.playerState().getFood()+" food |");
                }
                break;
        }


    }

    @Override
    public void handleInput(String input) throws Exception {

    }

}
