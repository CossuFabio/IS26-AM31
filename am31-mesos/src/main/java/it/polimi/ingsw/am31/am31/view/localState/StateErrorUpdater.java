package it.polimi.ingsw.am31.am31.view.localState;

import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.ErrorHandler;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.*;

/**
 * View-side handler for {@link ErrorMessage}s received from the network.
 * <p>
 * Maps each {@link ErrorCode} to the corresponding view event and posts it on the
 * {@link IEventBus} so that View components can react.
 * </p>
 */
public class StateErrorUpdater implements ErrorHandler {

    private final LocalGameState localState;
    private final IEventBus eventBus;

    /**
     * @param localState the local game state instance
     * @param eventBus   the event bus used to dispatch error events
     */
    public StateErrorUpdater(LocalGameState localState, IEventBus eventBus){

        this.localState = localState;
        this.eventBus = eventBus;

    }

    /**
     * Maps the error code of the given message to the corresponding view event and posts it.
     *
     * @param errorMessage the error message received from the server
     */
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
            case CARD_NOT_FOUND:
            case INSUFFICIENT_FOOD:
            case INVALID_DRAW:
            case INVALID_RESOURCE:
            case OFFER_CARD_NOT_FOUND:
            case OFFER_TRACK_TILE_ALREADY_TAKEN:
            case WRONG_PLAYER_TURN:
            case WRONG_ROUND_PHASE:
            case CANNOT_SKIP_DRAW:
                eventBus.post(new InGameErrorEvent(errorMessage.getMessage()));
                break;

            //Network errors
            case ErrorCode.CONNECTION_LOST:{
                eventBus.post(new ConnectionLostEvent());
                break;
            }


            default:
                break;


        }




    }



}
