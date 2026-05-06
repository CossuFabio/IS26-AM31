package it.polimi.ingsw.am31.am31.modelPackage;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.EverybodyPlayedException;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObservable;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;


import java.util.*;

public class TurnOrder implements GameObservable {

    private final int numPlayers;

    //Fixed number of slots
    private final ArrayList<Player> slots;

    //Next player that moves
    private int nextToAct;
    //Where to place totem when a player's turn ends
    private int nextEmptySlot;

    private ObserverHandler observers;

    public TurnOrder(int numPlayers){
        this.numPlayers = numPlayers;
        this.slots = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) slots.add(null);
        this.nextToAct = 0;
        this.nextEmptySlot = 0;
        this.observers = new GameObserversSet();
    }



    //First round only: places players in random order
    public void setPlayerFirstRound(Player player){
        slots.set(nextEmptySlot, player);
        nextEmptySlot++;
        if (nextEmptySlot == numPlayers) nextEmptySlot = 0;
        observers.onTurnOrderUpdate(this);
    }

    //Player that has to place totem in current TotemPhase
    public Player getPlayerActing(){
        return slots.get(nextToAct);
    }

    //After a player placed totem, moves the cursor and frees the slot
    public void goToNextPlayer() throws EverybodyPlayedException {
        if (nextToAct == numPlayers) throw new EverybodyPlayedException();
        slots.set(nextToAct, null);
        nextToAct++;
        observers.onTurnOrderUpdate(this);
    }

    public boolean everybodyPlayed(){
        return nextToAct == numPlayers;
    }

    //After finishing action, the player is set in the first empty slot
    public void setPlayer(Player player){
        int place = nextEmptySlot;
        slots.set(place, player);
        player.resolveEndTurn(place, numPlayers);
        nextEmptySlot++;
        if (nextEmptySlot == numPlayers) {
            //Reset the turnorder after everybody placed
            nextEmptySlot = 0;
            nextToAct = 0;
        }
        observers.onTurnOrderUpdate(this);
    }


    public List<Player> getOrder(){
        List<Player> result = new ArrayList<>();
        for(int i = 0; i<slots.size(); i++){
            result.add(slots.get(i));
        }
        return result;
    }


    @Override
    public void setObserverHandler(ObserverHandler observer) {
        this.observers = observer;
    }
}
