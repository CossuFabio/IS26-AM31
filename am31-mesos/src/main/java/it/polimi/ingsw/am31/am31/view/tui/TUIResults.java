package it.polimi.ingsw.am31.am31.view.tui;


import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.GlobalRankingEntry;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.localState.LocalLeaderBoard;
import it.polimi.ingsw.am31.am31.view.localState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.BoardUpdateEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.ReturnToLobbyEvent;
import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.Ansi;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.*;
import static java.lang.Math.min;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * TUI Results
 * <p>Class for drawing the Results screen after a game, implemented in CLI</p>
 */
public class TUIResults implements TUIPhase {



    private enum TuiResultsStep {SHOW_RESULTS, SHOW_TRIBES, GLOBAL_LEADERBOARD, BACK_TO_LOBBY}

    private volatile TuiResultsStep currentStep;
    private final LocalGameState gameState;
    private final ClientController controller;
    private final TextUserInterface TUI;

    public TUIResults(TextUserInterface TUI, ClientController controller, LocalGameState gameState){
        this.TUI = TUI;
        this.controller = controller;
        this.gameState = gameState;
        this.currentStep = TuiResultsStep.SHOW_RESULTS;
    }


    @Override
    /**
     * Prints out the current state of the results screen
     */
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
            case GLOBAL_LEADERBOARD: {
                drawLeaderboard();
                break;
            }
        }
    }



    @Override
    /**
     * User input handled based on the current step to navigate the results screen
     * @param input The text entered by the user
     */
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
                    case "3": {
                        currentStep = TuiResultsStep.GLOBAL_LEADERBOARD;
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
            case GLOBAL_LEADERBOARD: {
                if(input.equals("1"))
                    currentStep = TuiResultsStep.SHOW_RESULTS;
                else if(input.equals("2")){

                    currentStep = TuiResultsStep.BACK_TO_LOBBY;
                    controller.getEventBus().post(new ReturnToLobbyEvent());
                }

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

    /**
     * <p>Draws the results screen, with every players' points and the winner(s)</p>
     */
    protected void drawResults (){
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
            score = score + p.playerState().getNickname()  + " | " + p.playerState().getPrestigePoints() + POINTS+" "+ p.playerState().getFood() + FOOD;
            int size = score.length()+RESULT_TITLE_SIZE;
            System.out.println(upperBorder(size));
            System.out.println(centeredInsideBorder(score,size));
            System.out.println(lowerBorder(size));
        }
        System.out.println();
        System.out.println("Press: \n1 - To go back to Lobby"+"\n2 - To show players' tribes\n3 - To go to Global Leaderboard");
    }

    /**
     * <p>Draws every players' tribe, with their scores</p>
     */
    protected void drawTribes (){
        printOrderdTribe(gameState);
        System.out.println("\nPress:\n1 - To go back to results");}

    /**
     * <p>Draws the leaderboard of all players that have played a game.</p>
     */
    protected void drawLeaderboard() {
        int col = 5; //number of GlobalRankingEntry attributes
        int colsize = RANKING_COL_SIZE; //arbitrary

        String top = "┌" + ("─".repeat(colsize) +"┬").repeat(col-1) + "─".repeat(colsize)+ "┐";
        String mid = "├" + ("─".repeat(colsize) +"┼").repeat(col-1) + "─".repeat(colsize)+ "┤";
        String bot = "└" + ("─".repeat(colsize) +"┴").repeat(col-1) + "─".repeat(colsize)+ "┘";
        List<GlobalRankingEntry> ranking = gameState.getGlobalRanking();
       System.out.println(top);
       //table head
        System.out.println("│"+
                StringUtils.center("RANK",colsize) +"│"+
                StringUtils.center("NICKNAME",colsize)+"│"+
                StringUtils.center("FOOD",colsize)+"│"+
                StringUtils.center("POINTS",colsize) +"│"+
                StringUtils.center("N. OF GAMES",colsize)+"│");
        System.out.println(mid);
        for(int i=0;i<ranking.size();i++){
            GlobalRankingEntry e = ranking.get(i);
            String nick = e.getPlayerNickname();
            if (nick.length() > colsize)
                nick = nick.substring(0, colsize);
            //Prints the inside of a line
           System.out.println("│"+
                   StringUtils.center(Integer.toString(e.getRank()),colsize) +"│"+
                   StringUtils.center(nick,colsize)+"│"+
                   StringUtils.center(Integer.toString(e.getTotalFood()),colsize)+"│"+
                   StringUtils.center(Integer.toString(e.getTotalPrestigePoints()),colsize) +"│"+
                   StringUtils.center(Integer.toString(e.getGamesPlayed()),colsize)+"│");
           if(i+1<ranking.size())
                   System.out.println(mid);
           else break;
        }
        System.out.println(bot);

        System.out.println("\nType:\n1 - Go back to Results\n2 - Go back to Lobby");
    }
    @Subscribe
    /**
     * TUI subscribes to the BoardUpdate event, when the board changes, the leaderboard is re-printed
     */
    public void handleBoardUpdate(BoardUpdateEvent e) {
        TUI.printScreen();
    }

}
