package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalOfferCard;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;

import static org.fusesource.jansi.Ansi.*;


public class TUIGamephase implements TUIPhase{


    private enum TuiGameStep {MAIN, PLAYER_DETAIL, CARDLINE_DETAIL, OFFER_DETAIL, TOTEM_PLACE}
    private TuiGameStep currentstep;
    private LocalGameState gameState;
    private ClientController controller;
    private TextUserInterface TUI;
    private int choosingTotem = 0;

public TUIGamephase (TextUserInterface TUI, ClientController controller, LocalGameState gameState){
    this.TUI = TUI;
    this.controller = controller;
    this.gameState = gameState;
    currentstep = TuiGameStep.MAIN;
}



    @Override
    public void draw(){
    //erases screen
        System.out.println(ansi().eraseScreen());
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

        //prints the round, phase and era
        System.out.println(ansi().fg(Color.BLACK).bgBrightGreen().a("" +
                "\nROUND " +gameState.getRoundNumber()+
                " ERA "+gameState.getEra()+"\n").reset());
        //prints upperline, but only ids
        for(Card c :gameState.getBoard().getUpperLine())
            System.out.println(ansi().a(" <"+c.getCardId()+"> "));
        //prints players in order of acting
        System.out.println(ansi().a(" >\n"));
        for(LocalPlayerState p: gameState.getTurnorder())
            System.out.println(ansi().a(p+" "));
        //prints offertrack, but only ids? and player inside?
        System.out.println(ansi().a("\n"));
        for(LocalOfferCard c: gameState.getBoard().getOfferTrack()) {
            //should this display nicknames or color? or both?
            System.out.println(ansi().a("<"+c.getOfferCardId() + " " + c.getPlayer().getNickname()+"> "));
        }
        //prints lowerline
        System.out.println(ansi().a("\n"));
        for(Card c :gameState.getBoard().getUnderLine())
            System.out.println(ansi().a(" <"+c.getCardId()+"> "));
        //prints your own tribe and stats?
        //TODO add own info
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

    }
    public void drawCardLines(){

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
            case PLAYER_DETAIL: break;

            case CARDLINE_DETAIL: break;

            case TOTEM_PLACE:{
              //input should be a offer card Id (letter A to G)
            }break;
            default:System.out.println("\nInvalid input\n"); break;
        }
        TUI.printScreen();
    }
    @Override
    public void handleError(String errorMsg){

    }
}
