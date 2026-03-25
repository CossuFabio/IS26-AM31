package it.polimi.ingsw.am31.am31.handlers;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.cards.*;
import it.polimi.ingsw.am31.am31.handlers.onDraw.GeneralAdditionalFoodDecorator;
import it.polimi.ingsw.am31.am31.handlers.onDraw.InventorAdditionalFoodDecorator;
import it.polimi.ingsw.am31.am31.visitor.CountVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawHandlers {
    
    Player player = new Player("Test", null);

    @Test
    void drawsShaman(){

        int stars = 2;
        int initialStars = player.getRitualStars();

        player.addCard(new Shaman(1, 2,stars));

        assertEquals(player.getRitualStars(), initialStars + stars);


        player.addCard(new Shaman(1,2, stars));

        assertEquals(player.getRitualStars(), initialStars + stars + stars);

    }

    @Test
    void drawsHunter(){

        int initialFood = player.getFood();

        int numHuntersNoMark = 3;
        for(int i = 0; i < numHuntersNoMark; i++){
            player.addCard(new Hunter(1,2, false));
        }
        //All hunters are unmarked => no food is added
        assertEquals(player.getFood(), initialFood);
        player.addCard(new Hunter(1,2,true));

        assertEquals(initialFood + numHuntersNoMark, player.getFood());

    }

    @Test
    void generalAdditionalFoodDecoratorTest(){

        player.addDrawEffect(GeneralAdditionalFoodDecorator::new);
        int foodBonus = 5; //Bonus given by the decorator

        int initialFood = player.getFood();

        //Initialize the tribe - Farmer missing => incomplete set => no food bonus

        //Both hunters unmarked => no food bonus
        player.addCard(new Hunter(1, 2,false));
        player.addCard(new Hunter(1, 2,false));

        player.addCard(new Artist(1,2));
        player.addCard(new Inventor(1, 2,IconEnum.ARROW));
        player.addCard(new Builder(1,2,1,1));
        player.addCard(new Shaman(1,2,1));

        player.addCard(new Artist(1,2));
        player.addCard(new Inventor(1, 2,IconEnum.ARROW));
        player.addCard(new Builder(1,2,1,1));
        player.addCard(new Shaman(1,2,1));

        player.addCard(new Artist(1,2));
        player.addCard(new Inventor(1, 2,IconEnum.ARROW));
        player.addCard(new Builder(1,2,1,1));
        player.addCard(new Shaman(1,2,1));

        assertEquals(initialFood, player.getFood());

        //Adding farmers to complete two sets (one hunter missing for the bonus of the third set)
        player.addCard(new Farmer(1, 1,2));

        assertEquals(initialFood + foodBonus, player.getFood());
        int currentFood = initialFood + foodBonus;

        player.addCard(new Farmer(1, 1,2));
        assertEquals(currentFood + foodBonus, player.getFood());
        currentFood += foodBonus;


        player.addCard(new Farmer(1, 1,2));
        assertEquals(currentFood, player.getFood());

        //Now adding a marked hunter: should add 2 foods for previous hunters + 5 by the decorator
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        //Expected value before adding the marked one
        currentFood += foodBonus + visitor.getHunters();

        player.addCard(new Hunter(1, 2,true));
        assertEquals(currentFood, player.getFood());



    }


    @Test
    void inventorsAdditionalFoodTest(){

        int currentFood = player.getFood();

        //Shouldn't add food: the decorator hasn't been added yet
        player.addCard(new Inventor(1, 2,IconEnum.ARROW));
        player.addCard(new Inventor(1, 2,IconEnum.ARROW));

        assertEquals(currentFood, player.getFood());

        //Shouldn't be counter towards the bonus: the decorator hasn't been added yet
        player.addCard(new Inventor(1,2, IconEnum.BAIT));


        player.addDrawEffect(InventorAdditionalFoodDecorator::new);
        int foodBonus = 3; //Bonus given by the decorator

        //Shouldn't add food: this icon has been added before the creation of the decorator
        player.addCard(new Inventor(1,2, IconEnum.BAIT));

        assertEquals(currentFood, player.getFood());

        //Should add food: new couple
        player.addCard(new Inventor(1, 2,IconEnum.BREAD));
        player.addCard(new Inventor(1,2, IconEnum.BREAD));

        assertEquals(currentFood + foodBonus, player.getFood());


    }

}
