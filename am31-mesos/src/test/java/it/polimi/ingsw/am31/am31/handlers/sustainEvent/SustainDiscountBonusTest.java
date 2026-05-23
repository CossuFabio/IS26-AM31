package it.polimi.ingsw.am31.am31.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.ArtistDiscountBonus;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.FarmerDiscountBonus;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.InventorDiscountBonus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SustainDiscountBonusTest {

    private Player owner;

    @BeforeEach
    void setUp() {
        owner = createPlayer().build();
    }

    @Test
    void testArtistBonusWithArtists() {
        int artistsNumber = 3;
        for (int i = 0; i < artistsNumber; i++) owner.addCard(createArtist().build());
        assertEquals(artistsNumber * ArtistDiscountBonus.DISCOUNT_GIVEN, new ArtistDiscountBonus().getBonus(owner));
    }


    @Test
    void testFarmerBonusWithFarmers() {
        int farmersNumber = 2;
        for (int i = 0; i < farmersNumber; i++) owner.addCard(createFarmer().build());
        assertEquals(farmersNumber * FarmerDiscountBonus.DISCOUNT_GIVEN, new FarmerDiscountBonus().getBonus(owner));
    }

    @Test
    void testInventorBonusWithInventors() {
        int inventorsNumber = 4;
        for (int i = 0; i < inventorsNumber; i++) owner.addCard(createInventor().build());
        assertEquals(inventorsNumber * InventorDiscountBonus.DISCOUNT_GIVEN, new InventorDiscountBonus().getBonus(owner));
    }

}