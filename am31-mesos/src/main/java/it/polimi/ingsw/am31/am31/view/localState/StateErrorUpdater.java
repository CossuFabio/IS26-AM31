package it.polimi.ingsw.am31.am31.view.localState;

import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.ErrorHandler;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.*;

public class StateErrorUpdater implements ErrorHandler {

    private final LocalGameState localState;
    private final IEventBus eventBus;

    public StateErrorUpdater(LocalGameState localState, IEventBus eventBus){

        this.localState = localState;
        this.eventBus = eventBus;

    }


    //There are ErrorCode values not handled because they come from exception already handled by the ClientController
    @Override
    public void handleErrorMessage(ErrorMessage errorMessage){

        switch(errorMessage.getErrorCode()){
            //lobby errors
            case ErrorCode.USERNAME_ALREADY_IN_USE: {
                eventBus.post(new FailedRegistrationEvent());
                break;
            }
            case ErrorCode.PLAYER_COLOR_ALREADY_TAKEN: {
                eventBus.post(new InvalidColorPickEvent());
                break;
            }
            case ErrorCode.GAME_ALREADY_STARTED:
            case ErrorCode.LOBBY_NOT_FOUND:
            case ErrorCode.PLAYER_ALREADY_IN_GAME:
            case ErrorCode.TOO_MANY_PLAYERS :{
                eventBus.post(new FailedJoinLobby(errorMessage.getMessage()));
                break;
            }
            //game errors
            case CARD_NOT_FOUND: eventBus.post(new InGameErrorEvent(errorMessage.getMessage()));
            case INSUFFICIENT_FOOD: eventBus.post(new InGameErrorEvent(errorMessage.getMessage()));
            case INVALID_DRAW: eventBus.post(new InGameErrorEvent(errorMessage.getMessage()));
            case INVALID_RESOURCE: eventBus.post(new InGameErrorEvent(errorMessage.getMessage()));
            case OFFER_CARD_NOT_FOUND: eventBus.post(new InGameErrorEvent(errorMessage.getMessage()));
            case OFFER_TRACK_TILE_ALREADY_TAKEN: eventBus.post(new InGameErrorEvent(errorMessage.getMessage()));
            case WRONG_PLAYER_TURN: eventBus.post(new InGameErrorEvent(errorMessage.getMessage()));
            case WRONG_ROUND_PHASE: eventBus.post(new InGameErrorEvent(errorMessage.getMessage()));
            case CANNOT_SKIP_DRAW: eventBus.post(new InGameErrorEvent(errorMessage.getMessage()));

            //network errors
            case ErrorCode.CONNECTION_LOST:{
                eventBus.post(new ConnectionLostEvent());
                break;
            }


            default:
                break;


        }




    }



}
