package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.BuildingDeck;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.TribeDeck;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.PlayerMessage;

import java.util.ArrayList;
import java.util.List;

public class LocalGameState implements LocalObservable {
    private int era;
    private RoundPhasesEnum currentRoundPhase;
    private LocalPlayerState playerActing;
    private int roundNumber;
    private List<LocalPlayerState> players;
    private LocalBoardState board;
    private LocalObserver gameObserver; //only one, its the players view
    private BuildingDeck buildings;
    private TribeDeck cards;
    public LocalGameState () {
        players = new ArrayList<LocalPlayerState>();
    }


//this is the only observable object client side
@Override
    public void addObserver(LocalObserver obs) {
        gameObserver=obs;
    }

    public void GameStart() {
        //calls on its observer that the game has started
        gameObserver.onGameStartUpdate();
    }
    public void ShowLobby(List<LobbyDescriptor> lobbies){
        //sends the view the lobbies
        gameObserver.onShowLobbyUpdate(lobbies);
    }

    //setter methods, called by the connection when it receives updates.
    public void setPlayerActing() {

    }
    public void setCurrentRoundPhase(RoundPhasesEnum newPhase) {
        this.currentRoundPhase=newPhase;
        gameObserver.onRoundPhaseUpdate();
    }
    public void setPlayers(List<PlayerMessage> playersList){
        //remakes the list everytime

        int i=0;
        for(PlayerMessage p: playersList) {
            //SOSEW
            //Te l'ho commentato perche tirava IndexOutOfBounds
            //players.set(i, new LocalPlayerState(p.getNickname(), p.getColor()));
            //se vuoi fix veloce con funzionale:
            //players = playersList.stream().map(p-> new LocalPlayerState(p.getNickname(), p.getColor())).toList();
            //Occhio che la lista mi sa che è non modificabile se fai tolist, quindi o la rimetti da zero come fa la riga sopra o cambi
            //funzione di collezione della mappa
            i++;
        }
        gameObserver.onPlayerListUpdate();
    }
    public void setCardLine(List<Card> cards, BoardRows row){
        if(row.equals(BoardRows.LOWER))
            board.setUnderLine(cards);
        else if(row.equals(BoardRows.UPPER))
            board.setUpperLine(cards);
        gameObserver.onCardLineUpdate();
    }
    public void setEra(int era){
        this.era=era;
        gameObserver.onEraUpdate();
    }
    public void setRoundNumber(int newRound){
        this.roundNumber=newRound;
        gameObserver.onRoundNumberUpdate();
    }
    public void setOfferTrack(ArrayList<LocalOfferCard> offerTrack){
//updates the offerTrack, after every totem placement
        board.setOfferTrack(offerTrack);
        gameObserver.onOfferTrackUpdate();
    }

    public void updatePlayerScore(String id,int newfood, int newpp){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
            {
                //finds the player, udpates scores
                p.setFood(newfood);
                p.setPrestigePoints(newpp);
        }
        gameObserver.onPlayerScoreUpdate();
    }
    public void updatePlayerBuildings(String id, List<Card> cards){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
                p.setTribe();
    }
    public void updatePlayerDeck(String id, List<Card> cards){

    }
}