package it.polimi.ingsw.am31.am31.network.Messages.updateMessages;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.GameRoundStatusUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.GameStartUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;

public class UpdateVisitor {
    public UpdateVisitor(){
    }
    public void visit(ShowLobbyUpdate showLobbyUpdate) {
        for(LobbyDescriptor l : showLobbyUpdate.getLobbies()){
            System.out.println("Partita: " + l.getId() + ", richiede: " + l.getnPlayers() + " giocatori. Giocatori in lobby: " + l.getFreeSlots());
        }

    }

    public void visit(GameRoundStatusUpdate gameRoundStatusUpdate) {

    }

    public void visit(GameStartUpdate gameStartUpdate) {
    }
}
