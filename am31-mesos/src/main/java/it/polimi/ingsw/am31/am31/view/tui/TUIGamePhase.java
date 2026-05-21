package it.polimi.ingsw.am31.am31.view.tui;


import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.SkipDrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.TotemNetworkRequest;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.BoardUpdateEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameEventResolveEvent;
import org.fusesource.jansi.Ansi;

import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.*;
import static org.fusesource.jansi.Ansi.ansi;


public class TUIGamePhase implements TUIPhase {


    private enum TuiGameStep {MAIN, PLAYER_DETAIL, CARDLINE_DETAIL, OFFER_DETAIL, TOTEM_PLACE, EVENTS_SOLVED}

    private TuiGameStep currentstep;
    private LocalGameState gameState;
    private ClientController controller;
    private TextUserInterface TUI;
    private int choosingTotem;
    private int choosingCard;
    private String boardRowRequest;

    public TUIGamePhase(TextUserInterface TUI, ClientController controller, LocalGameState gameState) {
        this.TUI = TUI;
        this.controller = controller;
        this.gameState = gameState;
        currentstep = TuiGameStep.MAIN;
        choosingTotem = 0;
        choosingCard = 0;
    }


    @Override
    public void draw() {
        //erases screen and resets font
        System.out.println(ansi().eraseScreen());
        System.out.println(ansi().reset());
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

    public void drawEventsSolved() {
        System.out.println("      LAST EVENTS SOLVED, press any key - to go back   \n");
    printDetailedCardLine(gameState.getEventsSolved());
    }

    public void drawMain() {
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
        //prints who's in turn now
        if (gameState.getPlayerActing() != null)
            System.out.println(gameState.getPlayerActing().getNickname().equals(controller.getLocalPlayerUsername()) ? "\n▶ it's your turn" : "\n▶ it's " + gameState.getPlayerActing().getNickname() + " [" + gameState.getPlayerActing().getColor() + "] " + "'s turn");
        //prints your own tribe or stats?
        System.out.println("\n     YOUR STATS AND TRIBE:");
        for (LocalPlayerState p : gameState.getPlayers())
            if (p.getNickname().equals(controller.getLocalPlayerUsername())) {
                System.out.println("" + p + "\n");
                for (Card c : p.getTribe())
                    printCard(c);
                for (Card c : p.getBuildings())
                    printCard(c);
            }
        //prints choices
        System.out.println("\nPress:\n1- for detailed CardLines" +
                "\n2- for detailed offerTrack" +
                "\n3- to look at all tribes\n");
    }


    public void drawOfferTrack() {
        printTurnOrder(gameState, controller.getLocalPlayerUsername());
        printDetailedOfferTrack(gameState);
        if (choosingTotem == 0)
            System.out.println(ansi().a("\nPress:\n1- to go back to MAIN" +
                    "\n2- to place totem on a tile." +
                    "\n3- to go to cards and draw"));
        else
            System.out.println("\nType the Id of the card you want to place in\n");

    }

    public void drawPlayers() {
        //first all the players with scores
        for (LocalPlayerState p : gameState.getPlayers()) {
            System.out.println(p.toString()); //formatting based on player color
        }
        //then each one with their tribe and buildings
        for (LocalPlayerState p : gameState.getPlayers()) {
            System.out.println("\n");
            print(p,p.getNickname());
            System.out.println(ansi().reset());
            printDetailedCardLine(p.getTribe());
            System.out.println();
            printDetailedCardLine(p.getBuildings());
        }
        ansi().reset();
        System.out.println("\nPress 1- go back to MAIN\n");
    }

    public void drawCardLines() {
        System.out.println(ansi().a("UPPER LINE:"));
        System.out.println();
        //first without buildings, then buildings with desc
        printDetailedCardLine(gameState.getBoard().getUpperLineNOB());
        System.out.println();
        printDetailedCardLine(gameState.getBoard().getUpperLineB());
        System.out.println();
        System.out.println(ansi().a("LOWER LINE:"));
        System.out.println();
        printDetailedCardLine(gameState.getBoard().getUnderLineNOB());
        System.out.println();
        printDetailedCardLine(gameState.getBoard().getUnderLineB());
        if (choosingCard == 0)
            System.out.println("\nPress: \n1- Go back to Main" +
                    "\n2- to draw a card");
        else if (choosingCard == 1)
            System.out.println("\nChoose a Row to draw from, 1 = upper, 2 = lower");
        else if (choosingCard == 2)
            System.out.println("\nChoose a cardId / type " + TUIConfig.SKIP_VALUE);
    }

    @Override
    public void handleInput(String input) throws Exception {
        //to handle the input we use both the model phase and the currentstep.
        switch (currentstep) {
            case EVENTS_SOLVED: {
                if (!input.equals(""))
                    currentstep = TuiGameStep.MAIN;
            } break;
            case MAIN: {
                if (Integer.parseInt(input) > 4 || Integer.parseInt(input) < 1)
                    break; //ignores invalid input
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
                }
                break;
            }
            case OFFER_DETAIL: {
                switch (Integer.parseInt(input)) {
                    case 1: {
                        currentstep = TuiGameStep.MAIN;
                        break;
                    }
                    case 2: {
                        if (!gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.TOTEM_PLACING)) {
                            System.out.println(ansi().a("\nNot the time for this\n"));
                            break;
                        }
                        //if right time, handle choice
                        currentstep = TuiGameStep.TOTEM_PLACE;
                        choosingTotem = 1;
                        break;
                    }
                    case 3: {
                        currentstep = TuiGameStep.CARDLINE_DETAIL;
                        choosingCard = 1;
                        break;
                    }
                }
                break;
            }
            case PLAYER_DETAIL:
                if (Integer.parseInt(input) == 1) currentstep = TuiGameStep.MAIN;
                break;
            case CARDLINE_DETAIL: {
                switch (choosingCard) {
                    case 0: {//choice if drawing or other
                        switch (Integer.parseInt(input)) {
                            case 1:
                                currentstep = TuiGameStep.MAIN;
                                break;
                            case 2:
                                if (gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.ACTION_PHASE)||gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.BONUS_DRAWING_PHASE))
                                    choosingCard = 1;
                                else {
                                    System.out.println("Not the time for this");
                                    choosingCard = 0;
                                }
                                break;
                            default:
                                System.out.println("Invalid input");
                                break;
                        }
                        break;
                    }
                    case 1: {//row choice
                        if (!gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.ACTION_PHASE)||gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.BONUS_DRAWING_PHASE)) {
                            System.out.println("Not the time for this");
                            choosingCard = 0;
                            break;
                        }
                        if (!(Integer.parseInt(input) == 1 || Integer.parseInt(input) == 2)) {
                            System.out.println("\nInvalid input\n");
                        } else {
                            boardRowRequest = input;
                           choosingCard = 2;
                       }
                        break;
                    }
                    //break;
                    case 2: {//card or skip choice
                        if (!gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.ACTION_PHASE)||gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.BONUS_DRAWING_PHASE)) {
                            System.out.println("Not the time for this");
                            choosingCard = 0;
                            break;
                        }
                        int temp = Integer.parseInt(boardRowRequest);
                        if (temp == 1) {
                            if (!input.equals(TUIConfig.SKIP_VALUE)) {
                                try {
                                    controller.sendRequest(new DrawNetworkRequest(input.toLowerCase(), BoardRows.UPPER));
                                } catch (Exception e) {
                                    System.out.println("Failed to send request");
                                }
                            } else
                                try {
                                    controller.sendRequest(new SkipDrawNetworkRequest(BoardRows.UPPER));
                                } catch (Exception e) {
                                    System.out.println("Failed to send request");
                                }
                        }
                        if (temp == 2) {
                            if (!input.equals(TUIConfig.SKIP_VALUE)) {
                                try {
                                    controller.sendRequest(new DrawNetworkRequest(input.toLowerCase(), BoardRows.LOWER));
                                } catch (Exception e) {
                                    System.out.println("Failed to send request");
                                }
                            }
                            try {
                                controller.sendRequest(new SkipDrawNetworkRequest(BoardRows.LOWER));
                            } catch (Exception e) {
                                System.out.println("Failed to send request");
                            }
                        }
                        choosingCard = 0;
                        currentstep = TuiGameStep.MAIN;
                        break;
                    }
                }
                break;
            }

            case TOTEM_PLACE: {
                //input should be an offer card Id (letter A to G)
                try {
                    controller.sendRequest(new TotemNetworkRequest(input.toUpperCase()));
                    System.out.println("Successfully requested totem"); //for testing
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
    public void handleBoardUpdate(BoardUpdateEvent e) {
        TUI.printScreen();
    }

    @Subscribe
    public void handleEventUpdate(GameEventResolveEvent e) {
        System.out.println("A game event has been resolved: " + e.getCard().getCardId()+ "\n");
        currentstep = TuiGameStep.EVENTS_SOLVED;
        TUI.printScreen();
    }


}
