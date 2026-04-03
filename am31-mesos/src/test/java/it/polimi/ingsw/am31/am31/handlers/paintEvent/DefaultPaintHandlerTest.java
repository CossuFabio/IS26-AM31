package it.polimi.ingsw.am31.am31.handlers.paintEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Artist;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent.BonusPaintHandlerDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultPaintHandlerTest {

    Player player = new Player("Test", null);


    @Test
    void shouldHandleDefaultPaintLose() {

        int startingPrestige = player.getPrestigePoints();
        int minArtist = 3;
        int bonus = 5;
        int malus = 3;

        for(int i = 0; i < minArtist-1; i++){
            player.addCard(new Artist("dummy", 1, 2));
        }
        player.resolvePainters(minArtist, malus, bonus);
        assertEquals(startingPrestige-malus, player.getPrestigePoints());

    }

    @Test
    void shouldHandleDefaultPaintWin() {

        int startingPrestige = player.getPrestigePoints();
        int minArtist = 3;
        int bonus = 5;
        int malus = 3;

        for(int i = 0; i < minArtist; i++){
            player.addCard(new Artist("dummy", 1, 2));
        }

        CountVisitor countVisitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(countVisitor));

        player.resolvePainters(minArtist, malus, bonus);
        assertEquals(startingPrestige + bonus*countVisitor.getArtists(), player.getPrestigePoints());

    }

    @Test
    void shouldHandleBonusFoodWin() {

        player.addCard(new BuildingCard("dummy", 1, 1, 1, (Player player) -> player.addPaintEffect(BonusPaintHandlerDecorator::new)));
        int startingPrestige = player.getPrestigePoints();
        int minArtist = 3;
        int bonus = 5;
        int malus = 3;

        for(int i = 0; i < minArtist; i++){
            player.addCard(new Artist("dummy", 1, 2));
        }

        CountVisitor countVisitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(countVisitor));

        player.resolvePainters(minArtist, malus, bonus);
        assertEquals(startingPrestige + bonus*countVisitor.getArtists(), player.getPrestigePoints());
        assertEquals(player.getFood(), countVisitor.getArtists());


    }



}