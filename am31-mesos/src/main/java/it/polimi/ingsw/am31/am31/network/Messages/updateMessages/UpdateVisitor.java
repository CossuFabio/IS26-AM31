package it.polimi.ingsw.am31.am31.network.Messages.updateMessages;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.GameRoundStatusUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.GameStartUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;

public class UpdateVisitor implements IUpdateVisitor{
    public UpdateVisitor(){
    }
    public boolean visit(ShowLobbyUpdate showLobbyUpdate) {
        for(LobbyDescriptor l : showLobbyUpdate.getLobbies()){
            System.out.println("Partita: " + l.getId() + ", richiede: " + l.getnPlayers() + " giocatori. Giocatori in lobby: " + l.getFreeSlots());
        }
        return true;
    }

    public boolean visit(GameRoundStatusUpdate gameRoundStatusUpdate) {
        return true;
    }

    public void visit(GameStartUpdate gameStartUpdate) {
    }
}
