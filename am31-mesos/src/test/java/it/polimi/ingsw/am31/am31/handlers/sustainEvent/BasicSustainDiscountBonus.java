package it.polimi.ingsw.am31.am31.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Farmer;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.ArtistDiscountBonus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.*;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicSustainDiscountBonus {

    Player owner;


    int othersCardNumber = 6;
    SustainEventCard sustain;
    int sustainMalus = 1;

    @BeforeEach
    void setUp(){
        owner = createPlayer().build();

        sustain = createSustainEvent().prestigePointsMalus(sustainMalus).build();

        for(int i = 0; i<othersCardNumber; i++) owner.addCard(createShaman().build());


    }

    @Test
    void testDefaultSustainHandlerNoMalus(){

        int startingFood = othersCardNumber + 2;
        owner.editFood(startingFood - owner.getFood());
        startingFood = owner.getFood();

        owner.resolveSustain(sustain.getPrestigePointsMalus());

        assertEquals(startingFood - othersCardNumber, owner.getFood());

    }

    @Test
    void testDefaultSustainHandlerWithMalus(){

        int startingFood = othersCardNumber / 2;
        owner.editFood(startingFood - owner.getFood());
        startingFood = owner.getFood();

        owner.resolveSustain(sustain.getPrestigePointsMalus());

        assertEquals(0, owner.getFood());
        assertEquals(startingFood - othersCardNumber, owner.getPrestigePoints());

    }

    @Test
    void testDefaultSustainWithFarmer(){

        int startingFood = othersCardNumber;
        owner.editFood(startingFood);
        Farmer f1 = createFarmer().discount(2).build();
        Farmer f2 = createFarmer().discount(3).build();
        int totalDiscount = 5;

        f1.addToPlayer(owner);
        f2.addToPlayer(owner);

        owner.resolveSustain(sustainMalus);

        assertEquals(startingFood - owner.getTribe().size() + totalDiscount,
                owner.getFood());

    }

    @Test
    void testWithExceedingDiscount(){

        int startingFood = othersCardNumber;
        int startingPp = owner.getPrestigePoints();

        owner.editFood(startingFood);
        Farmer f1 = createFarmer().discount(30).build();

        f1.addToPlayer(owner);


        owner.resolveSustain(sustainMalus);

        assertEquals(startingFood, owner.getFood());
        assertEquals(startingPp, owner.getPrestigePoints());

    }


    @Test
    void testWithBonusEffect(){

        owner.addSustainBonus(ArtistDiscountBonus::new);

        createArtist().build().addToPlayer(owner);

        int startingFood = owner.getTribe().size();

        owner.editFood(startingFood);

        owner.resolveSustain(sustainMalus);

        int expectedFood = startingFood - owner.getTribe().size() + ArtistDiscountBonus.DISCOUNT_GIVEN;

        assertEquals(expectedFood, owner.getFood());

    }

}
