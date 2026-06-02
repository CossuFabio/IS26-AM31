package it.polimi.ingsw.am31.am31.network.messages.updateMessages;

/** Collections of all UpdateMessage String-type identifiers */
public class UpdateMethodsConstants {

    //Game methods
    public final static String GAME_PLAYERS_LIST_UPDATE_METHOD = "GameUpdatePlayers";
    public final static String GAME_ROUND_UPDATE_METHOD = "GameUpdateRound"; //Updates both the round number and the phase
    public final static String GAME_SHOW_LOBBY_UPDATE_METHOD = "GameShowLobby";
    public final static String GAME_START_UPDATE = "GameStarted"; //when the game starts
    public final static String GAME_END_UPDATE = "GameEnded";
    public final static String GAME_EVENT_RESOLVE_UPDATE =  "GameEventResolveUpdate";

    //Player methods
    public final static String PLAYER_SCORES_UPDATE_METHOD    = "PlayerUpdateScores";
    public final static String PLAYER_TRIBES_UPDATE_METHOD    = "PlayerUpdateTribes";
    public final static String PLAYER_BUILDINGS_UPDATE_METHOD = "PlayerUpdateBuildings";
    public final static String PLAYER_BONUS_DRAW_METHOD = "PlayerBonusDrawUpdate";

    //Board methods
    public final static String BOARD_OFFERCARD_UPDATE_METHOD = "BoardUpdateOfferCard";
    public final static String BOARD_CARDLINE_UPDATE_METHOD  = "BoardUpdateCardLine";
    public final static String BOARD_TURNORDER_UPDATE_METHOD = "BoardUpdateTurnOrder";

    //Others
    public final static String GAME_CRASHED_METHOD = "GameCrashed";
    public final static String USERNAME_ACCEPTED_METHOD = "UsernameAccepted";

}
