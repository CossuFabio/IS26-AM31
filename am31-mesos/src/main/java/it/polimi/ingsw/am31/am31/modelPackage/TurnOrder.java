package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.IncorrectMethodCallException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.PlayerAlreadyInTurnOrderException;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObservable;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the turn order tile. Manages the totem-placing phase, tracking which player
 * acts next and where each player places their totem at the end of their action.
 */
public class TurnOrder implements GameObservable {

    private final int numPlayers;

    //Fixed number of slots
    private final ArrayList<Player> slots;

    //Next player that moves
    private int nextToAct;
    //Where to place totem when a player's turn ends
    private int nextEmptySlot;

    //Is a switch that controls the possibility to place totem
    private boolean isPlacingPhase = false;

    //Used to call the methods in the correct order
    private boolean firstRoundDone = false;

    private ObserverHandler observers;

    /**
     * @param numPlayers the total number of players, used to determine the food and prestige changes applied when a player
     *                   returns to the turn order tile at the end of their turn in the ACTION_PHASE
     */
    public TurnOrder(int numPlayers){
        this.numPlayers = numPlayers;
        this.slots = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) slots.add(null);
        this.nextToAct = 0;
        this.nextEmptySlot = 0;
        this.observers = new GameObserversSet();
    }



    //First round only: places players in random order

    /**
     * This method must be called only at the start of the game. Places the player on the turn order
     * card without changing their food or prestige points.
     * @param player the next player to put on the turn order card
     * @throws IncorrectMethodCallException if called after the starting phase of the game
     */
    public void setPlayerFirstRound(Player player) throws IncorrectMethodCallException{
        if(firstRoundDone) throw new IncorrectMethodCallException("setPlayerFirstRound");


        if(slots.contains(player)) throw new PlayerAlreadyInTurnOrderException(player);
        slots.set(nextEmptySlot, player);
        nextEmptySlot++;
        if (nextEmptySlot == numPlayers) {
            nextEmptySlot = 0;
            nextToAct = 0;
            isPlacingPhase = true;
            firstRoundDone = true;
        };
        observers.onTurnOrderUpdate(this);
    }

    /**
     * @return the next player that has to place their totem in the current TOTEM_PHASE
     * @throws IncorrectMethodCallException if called outside the TOTEM_PHASE
     */
    public Player getPlayerActing() throws IncorrectMethodCallException{
        if(!isPlacingPhase) throw new IncorrectMethodCallException("getPlayerActing");
        return slots.get(nextToAct);
    }

    //After a player placed totem, moves the cursor and frees the slot

    /**
     * Notifies this object that the player moved their totem on the offer track. The player is removed from the
     * turn order card and the acting player changes
     * @throws IncorrectMethodCallException if called outside the TOTEM_PHASE
     */
    public void goToNextPlayer() throws IncorrectMethodCallException {
        if(!isPlacingPhase) throw new IncorrectMethodCallException("goToNextPlayer");
        slots.set(nextToAct, null);
        nextToAct++;
        if(everybodyPlayed()) isPlacingPhase = false;
        observers.onTurnOrderUpdate(this);
    }

    /**
     * @return true if every player placed their totem on the offer track for the current TOTEM_PHASE
     */
    public boolean everybodyPlayed(){
        return nextToAct == numPlayers;
    }

    //After finishing action, the player is set in the first empty slot

    /**
     * Sets the player on the first free tile on the turn order card and updates their score according to the rules.
     * @param player the player returning to the turn order tile
     */
    public void setPlayer(Player player){
        if(!firstRoundDone) throw new IncorrectMethodCallException("setPlayer");
        if(isPlacingPhase) throw new IncorrectMethodCallException("setPlayer");
        if(slots.contains(player)) throw new PlayerAlreadyInTurnOrderException(player);
        int place = nextEmptySlot;
        slots.set(place, player);
        player.resolveEndTurn(place, numPlayers);
        nextEmptySlot++;
        if (nextEmptySlot == numPlayers) {
            //Reset the turn order after everybody placed
            nextEmptySlot = 0;
            nextToAct = 0;
            isPlacingPhase = true;
        }
        observers.onTurnOrderUpdate(this);
    }

    /**
     * @return a mutable list representing the current state of the turn order card, sorted by position.
     * <br> May contain null values for empty slots.
     */
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
