package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;

public interface IPickable {

    void onPick(Player player);
    void addToPlayer(Player player);
}
