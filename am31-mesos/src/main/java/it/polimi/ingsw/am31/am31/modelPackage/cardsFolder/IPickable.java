package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public interface IPickable {

    void onPick(Player player);
    void addToPlayer(Player player);
}
