package it.polimi.ingsw.am31.am31.handlers;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectIdsConstants;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectsCatalog;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.GeneralAdditionalFoodDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.InventorAdditionalFoodDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import it.polimi.ingsw.am31.am31.testUtils.emptyHandlers.EmptyDrawHandler;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum.*;
import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawHandlers {
    
    Player player = createPlayer().name("Test").build();

    @Test
    void drawsShaman(){

        int stars = 2;
        int initialStars = player.getRitualStars();

        player.addCard(createShaman().stars(stars).build());

        assertEquals(player.getRitualStars(), initialStars + stars);


        player.addCard(createShaman().stars(stars).build());

        assertEquals(player.getRitualStars(), initialStars + stars + stars);

    }

    @Test
    void drawsHunter(){

        int initialFood = player.getFood();

        int numHuntersNoMark = 3;
        for(int i = 0; i < numHuntersNoMark; i++){
            player.addCard(createHunter().mark(false).build());
        }
        //All hunters are unmarked => no food is added
        assertEquals(player.getFood(), initialFood);
        player.addCard(createHunter().mark(true).build());

        assertEquals(initialFood + numHuntersNoMark, player.getFood());

    }

    @Test
    void generalAdditionalFoodDecoratorTest(){

        player.addDrawEffect(GeneralAdditionalFoodDecorator::new);
        int foodBonus = 5; //Bonus given by the decorator

        int initialFood = player.getFood();

        //Initialize the tribe - Farmer missing => incomplete set => no food bonus

        //Both hunters unmarked => no food bonus
        player.addCard(createHunter().mark(false).build());
        player.addCard(createHunter().mark(false).build());

        player.addCard(createArtist().build());
        player.addCard(createInventor().icon(ARROW).build());
        player.addCard(createBuilder().prestigePoints(1).discount(1).build());
        player.addCard(createShaman().stars(1).build());

        player.addCard(createArtist().build());
        player.addCard(createInventor().icon(ARROW).build());
        player.addCard(createBuilder().prestigePoints(1).discount(1).build());
        player.addCard(createShaman().stars(1).build());

        player.addCard(createArtist().build());
        player.addCard(createInventor().icon(ARROW).build());
        player.addCard(createBuilder().prestigePoints(1).discount(1).build());
        player.addCard(createShaman().stars(1).build());

        assertEquals(initialFood, player.getFood());

        //Adding farmers to complete two sets (one hunter missing for the bonus of the third set)
        player.addCard(createFarmer().discount(2).build());

        assertEquals(initialFood + foodBonus, player.getFood());
        int currentFood = initialFood + foodBonus;

        player.addCard(createFarmer().discount(2).build());
        assertEquals(currentFood + foodBonus, player.getFood());
        currentFood += foodBonus;


        player.addCard(createFarmer().discount(2).build());
        assertEquals(currentFood, player.getFood());

        //Now adding a marked hunter: should add 2 foods for previous hunters + 5 by the decorator
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        //Expected value before adding the marked one
        currentFood += foodBonus + visitor.getHunters();

        player.addCard(createHunter().mark(true).build());
        assertEquals(currentFood, player.getFood());



    }


    @Test
    void inventorsAdditionalFoodTest(){

        int currentFood = player.getFood();

        //Shouldn't add food: the decorator hasn't been added yet, and it is a finite pair
        player.addCard(createInventor().icon(ARROW).build());
        player.addCard(createInventor().icon(ARROW).build());

        assertEquals(currentFood, player.getFood());

        //Should be counted towards the bonus: the decorator hasn't been added yet, but it doesn't form a pair
        player.addCard(createInventor().icon(BAIT).build());

        int ppBuilding = 5;
        player.addCard(createBuilding().cost(2).prestigePointsGained(ppBuilding).effect(p -> {p.addDrawEffect(InventorAdditionalFoodDecorator::new);}).build());

        int foodBonus = InventorAdditionalFoodDecorator.FOOD_BONUS; //Bonus given by the decorator

        //Should add food: this icon has been added before the creation of the decorator
        player.addCard(createInventor().icon(BAIT).build());

        assertEquals(foodBonus, player.getFood());

        //Should add food: new couple
        player.addCard(createInventor().icon(BREAD).build());
        player.addCard(createInventor().icon(BREAD).build());

        assertEquals(2*foodBonus, player.getFood());


    }

    @Test
    void inventorsEmptyIconTest(){

        Player owner = createPlayer().build();
        owner.addCard(createInventor().icon(null).build());
        owner.addCard(createArtist().build());
        owner.addDrawEffect(InventorAdditionalFoodDecorator::new);

        int startingFood = owner.getFood();

        owner.addCard(createInventor().icon(EMPTY).build());
        assertEquals(startingFood, owner.getFood());

        owner.addCard(createInventor().icon(EMPTY).build());
        assertEquals(startingFood, owner.getFood());

    }





}
