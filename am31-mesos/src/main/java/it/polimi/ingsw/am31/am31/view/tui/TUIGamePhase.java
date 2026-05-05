package it.polimi.ingsw.am31.am31.view.tui;

import com.google.common.eventbus.Subscribe;
import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.TotemNetworkRequest;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalOfferCard;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.BoardUpdateEvent;
import org.fusesource.jansi.Ansi;

import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.printCard;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;


public class TUIGamePhase implements TUIPhase{


    private enum TuiGameStep {MAIN, PLAYER_DETAIL, CARDLINE_DETAIL, OFFER_DETAIL, TOTEM_PLACE}
    private TuiGameStep currentstep;
    private LocalGameState gameState;
    private ClientController controller;
    private TextUserInterface TUI;
    private int choosingTotem = 0;
    private int choosingCard = 0;
    private String cardrequest;

    public TUIGamePhase(TextUserInterface TUI, ClientController controller, LocalGameState gameState){
        this.TUI = TUI;
        this.controller = controller;
        this.gameState = gameState;
        currentstep = TuiGameStep.MAIN;
    }



    @Override
    public void draw(){
    //erases screen and resets font
        System.out.println(ansi().eraseScreen());
        System.out.println(ansi().reset());
    switch(currentstep) {
        case MAIN:{ drawMain();}break;
        case OFFER_DETAIL:{drawOffer();}break;
        case PLAYER_DETAIL:{drawPlayers();}break;
        case CARDLINE_DETAIL:{drawCardLines();}break;
        case TOTEM_PLACE:{drawOffer();}break;//modified for card choice
        default: currentstep = TuiGameStep.MAIN; break;
    }
    }

    public void drawMain(){
        //prints the round, phase, and era
        System.out.println(ansi().fg(Ansi.Color.RED).a("ROUND " +gameState.getRoundNumber()+
                " ERA "+gameState.getEra()+" "+gameState.getCurrentRoundPhase()).reset());
        //prints players in order of acting
        System.out.println("\nPLAYERS, in order of action\n");
        for(LocalPlayerState p: gameState.getTurnorder())
            System.out.println(ansi().a(p.getNickname()+" "+p.getColor()));
        //prints upperline, but only ids
        System.out.println(ansi().a("\nUpperline:"));
        for(Card c :gameState.getBoard().getUpperLine())
            System.out.print(ansi().a(" |"+c.getCardId()+"| "));

        //prints offertrack, but only ids? and player inside?
        System.out.print(ansi().a("\nOffer Track: "));
        for(LocalOfferCard c: gameState.getBoard().getOfferTrack()) {
            //should this display nicknames or color? or both?
            System.out.print(ansi().a(" |"+c.getOfferCardId() + " " + c.getPlayer().getNickname()+"| "));
        }
        //prints lowerline
        System.out.println(ansi().a("\nLower Line: "));
        for(Card c :gameState.getBoard().getUnderLine())
            System.out.print(ansi().a(" |"+c.getCardId()+"| "));
        //prints your own tribe and stats?
        //prints if its your turn or not
        //System.out.println(gameState.getPlayerActing().equals(controller.getLocalPlayerUsername()) ? "it's your turn" : "it's "+gameState.getPlayerActing()+"'s turn" );
        //prints choices
        System.out.println(ansi().a("\nPress:\n1- for detailed CardLines" +
                "\n2- for detailed offerTrack" +
                "\n3- to look at others tribes"));
    }


    public void drawOffer(){
    System.out.println(ansi().a(""));
        for(LocalOfferCard c: gameState.getBoard().getOfferTrack()){
            if(c.getFood()>0)
                System.out.println(ansi().a("<" + c.getOfferCardId() + " gives " + c.getFood() + " food "));
            else
                System.out.println(ansi().a("<" +c.getOfferCardId()+" gives "+c.getDrawFromUpper()+" up. cards, "+c.getDrawFromUnder()+" down. card. "));
            if(c.getPlayer().getNickname().equals("EMPTY"))
                System.out.println(ansi().a("it's free >\n"));
            else
                System.out.println(ansi().a("it's occupied by ")+c.getPlayer().getNickname()+" "+c.getPlayer().getColor()+">\n");
    }
        if(choosingTotem==0)
        System.out.println(ansi().a("\nPress:\n1- to go back to MAIN" +
                "\n2- to place totem on a tile.\n"));
        else
            System.out.println(ansi().a("\nType the Id of the card you want to place in\n>"));
}
    public void drawPlayers(){
        for(LocalPlayerState p: gameState.getPlayers()) {
            System.out.println(p.toString()); //formatting based on player color
            for(Card c: p.getTribe())
                System.out.println(c.toString());
            for(Card c: p.getBuildings())
                System.out.println(c.toString());
        }
        ansi().reset();
        System.out.println("\nPress 1- go back to MAIN");
    }
    public void drawCardLines(){
            System.out.println(ansi().a("Upper line:"));
            for(Card c: gameState.getBoard().getUpperLine())
                printCard(c); //prints cards in funny color
            System.out.println(ansi().a("Lower Line:"));
            for(Card c: gameState.getBoard().getUnderLine())
                printCard(c);
        if(choosingCard == 0)
        System.out.println("\nPress: \n1- Go back to Main" +
                "\n2- to draw a card");
        else if (choosingCard == 1)
            System.out.println("\nChoose a Row to draw from, 1 = upper, 2 = lower");
        else if (choosingCard == 2)
            System.out.println("\nChoose a cardId");
    }

    @Override
    public void handleInput(String input) throws Exception{
        //to handle the input we use both the model phase and the currentstep.
        switch (currentstep){
            case MAIN:{
                if(Integer.parseInt(input)>4 || Integer.parseInt(input)<1)
                    break; //ignores invalid input
                switch(input){
                    case "1":currentstep=TuiGameStep.CARDLINE_DETAIL;  break;
                    case "2":currentstep=TuiGameStep.OFFER_DETAIL; break;
                    case "3":currentstep=TuiGameStep.PLAYER_DETAIL; break;
                }
        }break;
            case OFFER_DETAIL:{
                switch(input){
                    case "1":{currentstep=TuiGameStep.MAIN;}break;
                    case "2":{
                        if(!gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.TOTEM_PLACING))
                        {
                            System.out.println(ansi().a("\nNot the time for this\n"));
                            break;
                        }
                //if right time, handle choice
                        currentstep= TuiGameStep.TOTEM_PLACE;
                        choosingTotem = 1;
                    }
                }
            } break;
            case PLAYER_DETAIL:if(input=="1") currentstep = TuiGameStep.MAIN; break;

            case CARDLINE_DETAIL: {
                switch (choosingCard) {
                    case 0: {
                        switch (Integer.parseInt(input)) {
                            case 1:
                                currentstep = TuiGameStep.MAIN;
                                break;
                            case 2:
                                if(gameState.getCurrentRoundPhase().equals(RoundPhasesEnum.ACTION_PHASE))
                                    choosingCard = 1;
                                else System.out.println("Not the time for this");
                                break;
                            default:
                                System.out.println("Invalid input");
                                break;
                        }
                        break;
                    }
                    case 1: {
                        choosingCard = 2;
                        cardrequest = input;
                    }
                    break;
                    case 2: {
                        int temp = Integer.parseInt(input);
                        if (temp == 1)
                            try {
                                controller.sendRequest(new DrawNetworkRequest(cardrequest, BoardRows.UPPER));
                            } catch (Exception e) {
                                System.out.println("Failed to send request");
                            }
                        else
                            try {
                                controller.sendRequest(new DrawNetworkRequest(cardrequest, BoardRows.LOWER));
                            } catch (Exception e) {
                                System.out.println("Failed to send request");
                            }
                        choosingCard = 0;
                        currentstep = TuiGameStep.MAIN;
                    }
                    break;
                }
            }
            case TOTEM_PLACE:{
              //input should be a offer card Id (letter A to G)
                try{
                    controller.sendRequest(new TotemNetworkRequest(input.toUpperCase()));
                }catch(Exception e) {
                    System.out.println("Failed to send request");
                }
                    currentstep = TuiGameStep.OFFER_DETAIL; //or main?
            }break;
            default:System.out.println("\nInvalid input\n"); break;
        }
        TUI.printScreen();
    }


    @Subscribe
    public void handleBoardUpdate (BoardUpdateEvent e){
        TUI.printScreen();
    }

    //Servono gli eventi nei parametri!!!
    //    @Subscribe
//    public void handleNewRound () {}//when round changes, takes you back to main?  (completely optional btw)
//
//    @Subscribe
//    public void handleEndGame (){}


}
