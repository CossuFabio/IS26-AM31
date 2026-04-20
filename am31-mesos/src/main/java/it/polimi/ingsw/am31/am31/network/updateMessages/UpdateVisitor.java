package it.polimi.ingsw.am31.am31.network.updateMessages;

import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.GameRoundStatusUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;

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
}
