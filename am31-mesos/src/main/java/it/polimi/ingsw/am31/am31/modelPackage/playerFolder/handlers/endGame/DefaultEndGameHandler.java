package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

import java.util.HashSet;

public class DefaultEndGameHandler implements IEndGameHandler {

    private int artistsBonus(Player player){

        CountVisitor countVisitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(countVisitor));
        return countVisitor.getArtists()/2;

    }

    private int inventorsBonus(Player player){
        CountVisitor countVisitor = new CountVisitor();
        HashSet<IconEnum> icons = new HashSet<IconEnum>();

        player.getTribe().forEach(characterCard -> {

            characterCard.acceptVisit(countVisitor);
            //Creates the icons set
            if(characterCard.getIcon() != IconEnum.EMPTY) icons.add(characterCard.getIcon());

        });

        return countVisitor.getInventors() * icons.size();

    }


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

        int bonusPointsInventors = inventorsBonus(player);
        int bonusPointsArtists    = artistsBonus(player);

        player.editPrestigePoints(bonusPointsCharacters + bonusPointsBuildings + bonusPointsInventors + bonusPointsArtists);

    }
}