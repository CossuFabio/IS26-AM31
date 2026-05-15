package it.polimi.ingsw.am31.am31.network.Messages.errorMessage;

import static it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCategory.*;

//Used for  higher granularity filtering
public enum ErrorCode {

    //Lobby Errors
    GAME_ALREADY_STARTED(LOBBY_ERROR),
    INVALID_PLAYER_NUMBER(LOBBY_ERROR),
    LOBBY_NOT_FOUND(LOBBY_ERROR),
    PLAYER_ALREADY_IN_GAME(LOBBY_ERROR),
    PLAYER_COLOR_ALREADY_TAKEN(LOBBY_ERROR),
    TOO_MANY_PLAYERS(LOBBY_ERROR),
    USERNAME_ALREADY_TAKEN(LOBBY_ERROR),


    //In-game errors
    CARD_NOT_FOUND(IN_GAME_ERROR),
    INSUFFICIENT_FOOD(IN_GAME_ERROR),
    INVALID_DRAW(IN_GAME_ERROR),
    INVALID_RESOURCE(IN_GAME_ERROR),
    OFFER_CARD_NOT_FOUND(IN_GAME_ERROR),
    OFFER_TRACK_TILE_ALREADY_TAKEN(IN_GAME_ERROR),
    WRONG_PLAYER_TURN(IN_GAME_ERROR),
    WRONG_ROUND_PHASE(IN_GAME_ERROR),
    CANNOT_SKIP_DRAW(IN_GAME_ERROR),

    //Network errors
    BAD_REQUEST(NETWORK_ERROR),
    USERNAME_ALREADY_IN_USE(NETWORK_ERROR),
    USERNAME_NOT_REGISTERED(NETWORK_ERROR),
    CONNECTION_LOST(NETWORK_ERROR); //Detected client-side

    //This binds the specific error code to its super type, maintaining this hierarchy lighter than the UpdateMessage
    //and the NetworkUpdate hierarchies because there is no need to carry information inside the ErrorMessage
    private final ErrorCategory category;
    ErrorCode(ErrorCategory c){this.category = c; }
    public ErrorCategory getCategory() { return category; }



}
