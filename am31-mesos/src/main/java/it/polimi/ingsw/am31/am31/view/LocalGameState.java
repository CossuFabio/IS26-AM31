package it.polimi.ingsw.am31.am31.view;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

public class LocalGameState implements GameObserver {
    private int era;
    private RoundPhasesEnum currentRoundPhase;
    private LocalPlayerState playerActing;
    private int roundNumber;
    private List<LocalPlayerState> players;
    private LocalBoardState board;

    @Override
    public void onPlayerNewBuildingEvent(Player player) {

    }

    @Override
    public void onPlayerScoresUpdate(Player player) {

    }

    @Override
    public void onPlayerTribeUpdate(Player player) {

    }

    @Override
    public void onGameRoundStatusUpdate(Game game) {

    }

    @Override
    public void onPlayersListUpdate(Game game) {

    }

    @Override
    public void onCardLineUpdate(Board board, BoardRows row) {

    }

    @Override
    public void onOfferTrackUpdate(Board board) {

    }

    @Override
    public void onTurnOrderUpdate(Board board) {

    }
}
