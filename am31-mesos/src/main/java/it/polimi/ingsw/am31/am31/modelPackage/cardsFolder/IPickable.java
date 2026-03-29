package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder;

import it.polimi.ingsw.am31.am31.exceptions.InvalidPickException;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public interface IPickable {

    void onPick(Player player);

    //By default the player can pick cards
    default void canPick(Player player) throws InvalidPickException {}

    //Dispatch for which deck player will add the card
    void addToPlayer(Player player);
}
