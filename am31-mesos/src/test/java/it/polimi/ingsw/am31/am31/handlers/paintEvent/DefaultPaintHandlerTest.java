package it.polimi.ingsw.am31.am31.handlers.paintEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Artist;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent.BonusPaintHandlerDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.createArtist;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.createBuilding;
import static org.junit.jupiter.api.Assertions.*;

class DefaultPaintHandlerTest {

    Player player = createPlayer().name("Test").build();


    @Test
    void shouldHandleDefaultPaintLose() {

        int startingPrestige = player.getPrestigePoints();
        int minArtist = 3;
        int bonus = 5;
        int malus = 3;

        for(int i = 0; i < minArtist-1; i++){
            player.addCard(createArtist().era(1).build());
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
            player.addCard(createArtist().era(1).build());
        }

        CountVisitor countVisitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(countVisitor));

        player.resolvePainters(minArtist, malus, bonus);
        assertEquals(startingPrestige + bonus*countVisitor.getArtists(), player.getPrestigePoints());

    }

    @Test
    void shouldHandleBonusFoodWin() {
        player.addCard(createBuilding().era(1).cost(1).prestigePointsGained(1).effect((Player player) -> player.addPaintEffect(BonusPaintHandlerDecorator::new)).build());
        int startingPrestige = player.getPrestigePoints();
        int minArtist = 3;
        int bonus = 5;
        int malus = 3;

        for(int i = 0; i < minArtist; i++){
            player.addCard(createArtist().era(1).build());
        }

        CountVisitor countVisitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(countVisitor));

        player.resolvePainters(minArtist, malus, bonus);
        assertEquals(startingPrestige + bonus*countVisitor.getArtists(), player.getPrestigePoints());
        assertEquals(player.getFood(), countVisitor.getArtists());


    }



}