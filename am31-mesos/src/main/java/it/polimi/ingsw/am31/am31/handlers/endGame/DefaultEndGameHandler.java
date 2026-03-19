package it.polimi.ingsw.am31.am31.handlers.endGame;

import it.polimi.ingsw.am31.am31.Player;

public class DefaultEndGameHandler implements IEndGameHandler {
    public DefaultEndGameHandler() {
    }

    @Override
    public void handleEndGame(Player player) {
        int bonusPointsCharacters = player.getTribe()
                .stream()
                .mapToInt((card) -> card.getPrestigePoints())
                .sum();

        int bonusPointsBuildings = player.getBuildings()
                .stream()
                .mapToInt((card) -> card.getPrestigePointsGained())
                .sum();

        player.editPrestigePoints(bonusPointsCharacters + bonusPointsBuildings);

    }
}