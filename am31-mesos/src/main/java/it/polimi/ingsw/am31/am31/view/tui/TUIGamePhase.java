package it.polimi.ingsw.am31.am31.view.tui;


import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.InGameErrorEvent;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.localState.LocalOfferCard;
import it.polimi.ingsw.am31.am31.view.localState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.BoardUpdateEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameEventResolveEvent;
import org.fusesource.jansi.Ansi;
import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.*;
import static org.fusesource.jansi.Ansi.ansi;


public class TUIGamePhase implements TUIPhase {


    private enum TuiGameStep {MAIN, PLAYER_DETAIL, CARDLINE_DETAIL, OFFER_DETAIL, TOTEM_PLACE, EVENTS_SOLVED}

    private volatile TuiGameStep currentstep;
    private LocalGameState gameState;
    private ClientController controller;
    private TextUserInterface TUI;
    private int choosingTotem;
    private int choosingCard;
    private String boardRowRequest = "0";

    public TUIGamePhase(TextUserInterface TUI, ClientController controller, LocalGameState gameState) {
        this.TUI = TUI;
        this.controller = controller;
        this.gameState = gameState;
        currentstep = TuiGameStep.MAIN;
        choosingTotem = 0;
        choosingCard = 0;
    }


    @Override
    /**
     * Prints gamescreen, based on the players choice.
     */
    public void draw() {
        //erases screen and resets font
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Fallback per Linux/Mac
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        reset();
        switch (currentstep) {
            case MAIN: {
                drawMain();
                break;
            }
            case OFFER_DETAIL: {
                drawOfferTrack();
                break;
            }
            case PLAYER_DETAIL: {
                drawPlayers();
                break;
            }
            case CARDLINE_DETAIL: {
                drawCardLines();
                break;
            }
            case TOTEM_PLACE: {
                drawOfferTrack();
                break;
            }
            case EVENTS_SOLVED: {
                drawEventsSolved();
                break;
            }//modified for card choice
            default: {
                currentstep = TuiGameStep.MAIN;
                break;
            }
        }
    }

    /**
     * Prints the EventSolvedScreen, displays all the latest events solved
     */
    protected void drawEventsSolved() {

    printDetailedCardLine(gameState.getEventsSolved());
    System.out.print("      \nLAST EVENTS SOLVED, type any key - to go back  \n>");
    }

    /**
     * Prints the main game Screen, displaying non-detailed info on the game, and prompts the player
     * to choose to see tribes, the offertrack or the cardlines
     */
    protected void drawMain() {
        //prints the round, phase, and era
        System.out.println(ansi().fg(Ansi.Color.DEFAULT).a("ROUND " + gameState.getRoundNumber() +
                " ERA " + gameState.getEra() + " " + gameState.getCurrentRoundPhase()).reset());
        //prints players in turn order
        System.out.println("\nPLAYERS, in order of action");
        int i = 1;
        for (LocalPlayerState p : gameState.getTurnOrder())
            if (p != null)
                print(p, i++ + "- " + p.getNickname() + " " + p.getColor() + " " + ansi().reset() + "\n");
        //prints upperline, but only ids
        print(Ansi.Color.DEFAULT, "\n     UPPER LINE:\n");
        printCardLine(gameState.getBoard().getUpperLine());

        //prints offer track not in detail
        print(Ansi.Color.DEFAULT, "\n     OFFER TRACK:\n");
        printOfferTrack(gameState);

        //prints lowerline
        print(Ansi.Color.DEFAULT, "\n     LOWER LINE:\n");
        printCardLine(gameState.getBoard().getUnderLine());
        //prints your own tribe and stats
        System.out.println("\n     YOUR STATS AND TRIBE:");
        for (LocalPlayerState p : gameState.getPlayers())
            if (p.getNickname().equals(controller.getLocalPlayerUsername())) {
                System.out.println("" + p + "\n");
                for (Card c : p.getTribe())
                    printCard(c);
                for (Card c : p.getBuildings())
                    printCard(c);
            }
        //prints who's in turn now. if bonus phase, search for the player with the building bd20
        if(!gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.BONUS_DRAWING_PHASE))
        {
            if (gameState.getPlayerActing() != null)
                System.out.println(gameState.getPlayerActing().getNickname().equals(controller.getLocalPlayerUsername()) ? "\n▶ it's your turn" : "\n▶ it's " + getColor(gameState.getPlayerActing())+gameState.getPlayerActing().getNickname() + " [" + gameState.getPlayerActing().getColor() + "] "+ansi().reset()+"'s turn");
        }
        else {
            for (LocalPlayerState p : gameState.getPlayers())
                if(p.getBuildings().stream().anyMatch(b -> b.getCardId().equals("bd20")) )
                    System.out.println(p.getNickname().equals(controller.getLocalPlayerUsername()) ? "\n▶ You have a bonus draw" : "\n▶ Player " + getColor(p)+p.getNickname()+ " [" + p.getColor() + "] "+ansi().reset()+ " has a bonus draw");

        }

        //if action_phase, tells you what to draw.
        if(gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.ACTION_PHASE))
            for(LocalOfferCard c : gameState.getBoard().getOfferTrack()) {
                if(!c.isFree() && c.getPlayer().equals(controller.getLocalPlayerUsername())) {
                    if(c.getFood()>0){
                        System.out.println("This turn you got "+c.getFood()+FOOD); break;}
                    System.out.println("This turn you chose to draw: ");
                    System.out.print(c.getDrawFromUpper()>0? c.getDrawFromUpper()+" Cards from the UpperLine\n " : "" );
                    System.out.print(c.getDrawFromUnder()>0? c.getDrawFromUnder()+" Cards from the UnderLine\n" : "" );
                }
            }
        else if (gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.BONUS_DRAWING_PHASE)) {
            LocalPlayerState bonusPlayer = gameState.getPlayerActing();
            if (bonusPlayer != null && bonusPlayer.getNickname().equals(controller.getLocalPlayerUsername()))
                System.out.println("[OPTIONAL] Draw 1 from UpperLine");
        }
        //prints choices
        System.out.println("\nPress:\n1- for detailed CardLines" +
                "\n2- for detailed offerTrack" +
                "\n3- to look at all tribes\n");
    }

    /**
     * Prints the detailed offerTrack, showing occupied spaces and details on cards
     * prompts the player to place its totem on card or to go draw a card
     */
    protected void drawOfferTrack() {
        printTurnOrder(gameState, controller.getLocalPlayerUsername());
        printDetailedOfferTrack(gameState);
        if (choosingTotem == 0)
            System.out.println(ansi().a("\nPress:\n1- to go back to MAIN" +
                    "\n2- to place totem on a tile." +
                    "\n3- to go to cards and draw"));
        else
            System.out.println("\nType the Id of the card you want to place in\n");

    }

    /**
     * Prints the detailed tribes and scores of every player.
     */
    protected void drawPlayers() {
       printOrderdTribe(gameState);
        System.out.println("\nPress 1- go back to MAIN\n");
    }

    /**
     * Draws the upper and lower CardLines, prompts the player to choose to draw
     */
    protected void drawCardLines() {
        if(boardRowRequest.equals("1")||boardRowRequest.equals("0")) {
            System.out.println(ansi().a("UPPER LINE:"));
            System.out.println();
            //first without buildings, then buildings with desc
            printDetailedCardLine(gameState.getBoard().getUpperLineNOB());
            System.out.println();
            for (Card c : gameState.getBoard().getUpperLineB())
                printDetailedCard(c);
        }
            System.out.println();
        if(boardRowRequest.equals("2")||boardRowRequest.equals("0")) {
            System.out.println(ansi().a("LOWER LINE:"));
            System.out.println();
            printDetailedCardLine(gameState.getBoard().getUnderLineNOB());
            System.out.println();
            for (Card c : gameState.getBoard().getUnderLineB())
                printDetailedCard(c);
        }
        if (choosingCard == 0)
            System.out.println("\nPress: \n1- Go back to Main" +
                    "\n2- to draw a card");
        else if (choosingCard == 1)
            System.out.println("\nChoose a Row to draw from, 1 = upper, 2 = lower");
        else if (choosingCard == 2)
            System.out.println("\nChoose a cardId / type " + TUIConfig.SKIP_VALUE);
    }

    @Override
    /**
     * Handles the player's standard input, based on the current phase handles it differently.
     * the input is usually checked, and can lead to sending requests to the server.
     */
    public void handleInput(String input) throws Exception {
        //to handle the input we use both the model phase and the currentstep.
        switch (currentstep) {
            case EVENTS_SOLVED: {
                if (!input.equals(""))
                    currentstep = TuiGameStep.MAIN;
            } break;
            case MAIN: {
                switch (input) {
                    case "1":
                        currentstep = TuiGameStep.CARDLINE_DETAIL;
                        break;
                    case "2":
                        currentstep = TuiGameStep.OFFER_DETAIL;
                        break;
                    case "3":
                        currentstep = TuiGameStep.PLAYER_DETAIL;
                        break;
                    default: break; //ignores other inputs
                }
                break;
            }
            case OFFER_DETAIL: {
                switch (input) {
                    case "1": {
                        currentstep = TuiGameStep.MAIN;
                        break;
                    }
                    case "2": {
                        if (!gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.TOTEM_PLACING)) {
                            System.out.println(ansi().a("\nNot the time for this\n"));
                            break;
                        }
                        //if right time, handle choice
                        currentstep = TuiGameStep.TOTEM_PLACE;
                        choosingTotem = 1;
                        break;
                    }
                    case "3": {
                        currentstep = TuiGameStep.CARDLINE_DETAIL;
                        choosingCard = 1;
                        break;
                    }
                }
                break;
            }
            case PLAYER_DETAIL:
                if (input.equals("1")) currentstep = TuiGameStep.MAIN;
                break;
            case CARDLINE_DETAIL: {
                switch (choosingCard) {
                    case 0: {//choice if drawing or other
                        switch (input) {
                            case "1":
                                currentstep = TuiGameStep.MAIN;
                                boardRowRequest = "0";
                                break;
                            case "2":
                                if (gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.ACTION_PHASE)||gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.BONUS_DRAWING_PHASE))
                                    choosingCard = 1;
                                else {
                                    System.out.println("Not the time for this");
                                    choosingCard = 0;
                                }
                                boardRowRequest = "0";
                                break;
                            default:
                                System.out.println("Invalid input");
                                break;
                        }
                        break;
                    }
                    case 1: {//row choice
                        if (!gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.ACTION_PHASE) && !gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.BONUS_DRAWING_PHASE)) {
                            System.out.println("Not the time for this");
                            choosingCard = 0;
                            break;
                        }
                        if (!(input.equals("1") || input.equals("2"))) {
                            System.out.println("\nInvalid input\n");
                        } else {
                            boardRowRequest = input;
                           choosingCard = 2;
                       }
                        break;
                    }
                    //break;
                    case 2: {//card or skip choice
                        if (!gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.ACTION_PHASE) && !gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.BONUS_DRAWING_PHASE)) {
                            System.out.println("Not the time for this");
                            choosingCard = 0;
                            break;
                        }
                        if (boardRowRequest.equals("1")) {
                            if (!input.equals(SKIP_VALUE)) {
                                try {
                                    controller.requestDraw(input.toLowerCase(), BoardRows.UPPER);

                                } catch (Exception e) {
                                    System.out.println("Failed to send request");
                                }
                            }
                            else try {
                                    controller.requestSkip(BoardRows.UPPER);

                                } catch (Exception e) {
                                    System.out.println("Failed to send request");
                                }
                        }
                        else if (boardRowRequest.equals("2")) {
                            if (!input.equals(SKIP_VALUE)) {
                                try {
                                    controller.requestDraw(input.toLowerCase(), BoardRows.LOWER);

                                } catch (Exception e) {
                                    System.out.println("Failed to send request");
                                }
                            }
                            else try {
                                controller.requestSkip(BoardRows.LOWER);

                            } catch (Exception e) {
                                System.out.println("Failed to send request");
                            }
                        }
                        boardRowRequest = "0";
                        choosingCard = 0;
                        break;
                    }
                }
                break;
            }

            case TOTEM_PLACE: {
                //input should be an offer card Id (letter A to G)
                try {
                    controller.requestTotemPlacement(input.toUpperCase());
                  //  System.out.println("Successfully requested totem"); //for testing
                    choosingTotem = 0;
                } catch (Exception e) {
                    System.out.println("Failed to send request");
                }
                currentstep = TuiGameStep.MAIN;
                break;
            }
            default:
                System.out.println("\nInvalid input\n");
                break;
        }
        TUI.printScreen();
    }


    @Subscribe
    /**
     * TUI subscribes to updates to the Board, responds reprinting the current scereen witrh updated data
     */
    public void handleBoardUpdate(BoardUpdateEvent e) {
        TUI.printScreen();
    }

    @Subscribe
    /**
     * TUI subscribes to events solved on the board, responds printing the events solved screen
     */
    public void handleEventUpdate(GameEventResolveEvent e) {
        System.out.println("A game event has been resolved: " + e.getCard().getCardId()+ "\n");
        currentstep = TuiGameStep.EVENTS_SOLVED;
        TUI.printScreen();
    }

    @Subscribe
    /**
     * TUI subribes to error messages sent by the server. InGameError are sent when the player sends invalid input.
     * error are printed out
     */
    public void handleErroMessage(InGameErrorEvent e){
        System.out.println(e.getErrorMessage());
    }


}
