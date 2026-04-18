package it.polimi.ingsw.am31.am31.network.updateMessages;

public class UpdateMethodsConstants {

    //Game methods
    public final static String GAME_PLAYERS_LIST_UPDATE_METHOD = "GameUpdatePlayers";
    public final static String GAME_ROUND_UPDATE_METHOD = "GameUpdateRound"; //Updates both the round number and the phase
    public final static String GAME_SHOW_LOBBY_UPDATE_METHOD = "GameShowLobby";


    //Player methods
    public final static String PLAYER_SCORES_UPDATE_METHOD    = "PlayerUpdateScores";
    public final static String PLAYER_TRIBES_UPDATE_METHOD    = "PlayerUpdateTribes";
    public final static String PLAYER_BUILDINGS_UPDATE_METHOD = "PlayerUpdateBuildings";


    //Board methods
    public final static String BOARD_OFFERCARD_UPDATE_METHOD = "BoardUpdateOfferCard";
    public final static String BOARD_CARDLINE_UPDATE_METHOD  = "BoardUpdateCardLine";
    public final static String BOARD_TURNORDER_UPDATE_METHOD = "BoardUpdateTurnOrder";

}
