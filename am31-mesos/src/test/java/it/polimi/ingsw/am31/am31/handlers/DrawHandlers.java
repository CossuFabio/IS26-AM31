package it.polimi.ingsw.am31.am31.handlers;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.GeneralAdditionalFoodDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.InventorAdditionalFoodDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawHandlers {
    
    Player player = new Player("Test", null);

    @Test
    void drawsShaman(){

        int stars = 2;
        int initialStars = player.getRitualStars();

        player.addCard(new Shaman("dummy", 1, 2,stars));

        assertEquals(player.getRitualStars(), initialStars + stars);


        player.addCard(new Shaman("dummy", 1,2, stars));

        assertEquals(player.getRitualStars(), initialStars + stars + stars);

    }

    @Test
    void drawsHunter(){

        int initialFood = player.getFood();

        int numHuntersNoMark = 3;
        for(int i = 0; i < numHuntersNoMark; i++){
            player.addCard(new Hunter("dummy", 1,2, false));
        }
        //All hunters are unmarked => no food is added
        assertEquals(player.getFood(), initialFood);
        player.addCard(new Hunter("dummy", 1,2,true));

        assertEquals(initialFood + numHuntersNoMark, player.getFood());

    }

    @Test
    void generalAdditionalFoodDecoratorTest(){

        player.addDrawEffect(GeneralAdditionalFoodDecorator::new);
        int foodBonus = 5; //Bonus given by the decorator

        int initialFood = player.getFood();

        //Initialize the tribe - Farmer missing => incomplete set => no food bonus

        //Both hunters unmarked => no food bonus
        player.addCard(new Hunter("dummy", 1, 2,false));
        player.addCard(new Hunter("dummy", 1, 2,false));

        player.addCard(new Artist("dummy", 1,2));
        player.addCard(new Inventor("dummy", 1, 2, IconEnum.ARROW));
        player.addCard(new Builder("dummy", 1,2,1,1));
        player.addCard(new Shaman("dummy", 1,2,1));

        player.addCard(new Artist("dummy", 1,2));
        player.addCard(new Inventor("dummy", 1, 2,IconEnum.ARROW));
        player.addCard(new Builder("dummy", 1,2,1,1));
        player.addCard(new Shaman("dummy", 1,2,1));

        player.addCard(new Artist("dummy", 1,2));
        player.addCard(new Inventor("dummy", 1, 2,IconEnum.ARROW));
        player.addCard(new Builder("dummy", 1,2,1,1));
        player.addCard(new Shaman("dummy", 1,2,1));

        assertEquals(initialFood, player.getFood());

        //Adding farmers to complete two sets (one hunter missing for the bonus of the third set)
        player.addCard(new Farmer("dummy", 1, 1,2));

        assertEquals(initialFood + foodBonus, player.getFood());
        int currentFood = initialFood + foodBonus;

        player.addCard(new Farmer("dummy", 1, 1,2));
        assertEquals(currentFood + foodBonus, player.getFood());
        currentFood += foodBonus;


        player.addCard(new Farmer("dummy", 1, 1,2));
        assertEquals(currentFood, player.getFood());

        //Now adding a marked hunter: should add 2 foods for previous hunters + 5 by the decorator
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        //Expected value before adding the marked one
        currentFood += foodBonus + visitor.getHunters();

        player.addCard(new Hunter("dummy", 1, 2,true));
        assertEquals(currentFood, player.getFood());



    }


    @Test
    void inventorsAdditionalFoodTest(){

        int currentFood = player.getFood();

        //Shouldn't add food: the decorator hasn't been added yet, and it is a finite pair
        player.addCard(new Inventor("dummy", 1, 2,IconEnum.ARROW));
        player.addCard(new Inventor("dummy", 1, 2,IconEnum.ARROW));

        assertEquals(currentFood, player.getFood());

        //Should be counted towards the bonus: the decorator hasn't been added yet, but it doesn't form a pair
        player.addCard(new Inventor("dummy", 1,2, IconEnum.BAIT));

        int ppBuilding = 5;
        player.addCard(new BuildingCard("dummy", 1, 2, ppBuilding, p -> {p.addDrawEffect(InventorAdditionalFoodDecorator::new);}));
        //player.addDrawEffect(InventorAdditionalFoodDecorator::new);
        int foodBonus = InventorAdditionalFoodDecorator.FOOD_BONUS; //Bonus given by the decorator

        //Should add food: this icon has been added before the creation of the decorator
        player.addCard(new Inventor("dummy", 1,2, IconEnum.BAIT));

        assertEquals(foodBonus, player.getFood());

        //Should add food: new couple
        player.addCard(new Inventor("dummy", 1, 2,IconEnum.BREAD));
        player.addCard(new Inventor("dummy", 1,2, IconEnum.BREAD));

        assertEquals(2*foodBonus, player.getFood());


    }

}
