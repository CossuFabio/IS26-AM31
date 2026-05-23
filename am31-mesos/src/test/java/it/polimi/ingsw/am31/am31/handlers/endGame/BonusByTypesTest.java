package it.polimi.ingsw.am31.am31.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectIdsConstants;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectsCatalog;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Builder;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame.*;
import it.polimi.ingsw.am31.am31.testUtils.emptyHandlers.EmptyEndGameHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static it.polimi.ingsw.am31.am31.testUtils.testObservers.LogObserver.createLogObserver;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Tests for buildings that give prestige points based on specific characters types
public class BonusByTypesTest {

    private Player owner;
    private EmptyEndGameHandler baseHandler;

    private int startingFood, startingPrestigePoints;
    private EffectsCatalog effectsCatalog;

    @BeforeEach
    void setUp(){

        owner = createPlayer().build();
        baseHandler = new EmptyEndGameHandler();

        startingFood = owner.getFood();
        startingPrestigePoints = owner.getPrestigePoints();

        //Debug observer
        ObserverHandler observers = new GameObserversSet();
        observers.addObserver(createLogObserver()
                .scoresUpdate()
                .tribeUpdate()
                .build());

        owner.setObserverHandler(observers);


        effectsCatalog = new EffectsCatalog();

    }

    @Test
    void testArtistsPrestigeBonus(){

        ArtistPrestigeBonusDecorator decorator = new ArtistPrestigeBonusDecorator(baseHandler);

        int artistsNumber = 5;
        int expectedPoints = artistsNumber * ArtistPrestigeBonusDecorator.PRESTIGE_POINTS_BONUS;

        for(int i =  0; i < artistsNumber; i++){
            owner.addCard(createArtist().build());
        }

        decorator.handleEndGame(owner);

        assertEquals(expectedPoints, owner.getPrestigePoints());
        assertEquals(startingFood, owner.getFood());

    }

    @Test
    void artistsBonusWithDefault(){
        //Test correct adding effect to player
        int artistsNumber = 5;
        int currentPrestigePoints = owner.getPrestigePoints();
        int buildingPrestigePoints = 5;

        BuildingCard buildingCard = createBuilding()
                .prestigePointsGained(buildingPrestigePoints)
                .effect(effectsCatalog.getEffect(EffectIdsConstants.ARTIST_PRESTIGE_BONUS))
                .build();

        for(int i =  0; i < artistsNumber; i++){
            owner.addCard(createArtist().build());
        }

        buildingCard.addToPlayer(owner);
        owner.resolveEndGame();
        int expectedPoints = currentPrestigePoints + buildingPrestigePoints + artistsNumber * ArtistPrestigeBonusDecorator.PRESTIGE_POINTS_BONUS + DefaultEndGameHandler.PRESTIGE_POINTS_ARTISTS_PAIR * (artistsNumber/2);
        assertEquals(expectedPoints, owner.getPrestigePoints());
    }

    @Test
    void testBuilderPrestigeBonus(){

        BuilderPrestigeBonusDecorator decorator = new BuilderPrestigeBonusDecorator(baseHandler);

        int buildersNumber = 5;
        int expectedPoints = buildersNumber * BuilderPrestigeBonusDecorator.PRESTIGE_POINTS_BONUS;

        for(int i =  0; i < buildersNumber; i++){
            owner.addCard(createBuilder().build());
        }

        decorator.handleEndGame(owner);

        assertEquals(expectedPoints, owner.getPrestigePoints());
        assertEquals(startingFood, owner.getFood());

    }

    @Test
    void doubleBuilderTest(){

        //This time it is needed the default handler
        int buildingPrestige = 5;
        BuildingCard buildingCard = createBuilding()
                .prestigePointsGained(buildingPrestige)
                .effect(effectsCatalog.getEffect(EffectIdsConstants.DOUBLE_BUILDER_ENDGAME))
                .build();

        buildingCard.addToPlayer(owner);

        List<Builder> builders = List.of(
                createBuilder().prestigePoints(1).build(),
                createBuilder().prestigePoints(2).build(),
                createBuilder().prestigePoints(3).build(),
                createBuilder().prestigePoints(4).build(),
                createBuilder().prestigePoints(1).build()
        );

        int buildersPrestige = builders.stream().mapToInt(Builder::getPrestigePoints).sum();

        int expectedPrestige = startingPrestigePoints + 2*buildersPrestige + buildingPrestige;

        builders.forEach(builder -> builder.addToPlayer(owner));

        owner.resolveEndGame();

        assertEquals(expectedPrestige, owner.getPrestigePoints());

    }

    @Test
    void testFarmerBonus(){

        FarmerPrestigeBonusDecorator decorator = new FarmerPrestigeBonusDecorator(baseHandler);

        int farmerNumber = 5;
        int expectedPoints = farmerNumber * FarmerPrestigeBonusDecorator.PRESTIGE_POINTS_BONUS;

        for(int i =  0; i < farmerNumber; i++){
            owner.addCard(createFarmer().build());
        }

        decorator.handleEndGame(owner);

        assertEquals(expectedPoints, owner.getPrestigePoints());
        assertEquals(startingFood, owner.getFood());

    }

    @Test
    void testFlatPrestigePointsBonus(){

        BuildingCard building = createBuilding()
                .prestigePointsGained(0)
                .effect(effectsCatalog.getEffect(EffectIdsConstants.FLAT_PRESTIGE_POINTS))
                .build();

        building.addToPlayer(owner);

        int expectedPrestige = startingPrestigePoints + FlatPrestigePointsDecorator.PRESTIGE_POINTS_BONUS;

        owner.resolveEndGame();
        assertEquals(expectedPrestige, owner.getPrestigePoints());


    }

    @Test
    void testHunterBonus(){

        HunterPrestigeBonusDecorator decorator = new HunterPrestigeBonusDecorator(baseHandler);

        int hunterNumber = 5;
        int expectedPoints = hunterNumber * HunterPrestigeBonusDecorator.PRESTIGE_POINTS_BONUS;

        for(int i =  0; i < hunterNumber; i++){
            owner.addCard(createHunter().mark(false).build());
        }

        decorator.handleEndGame(owner);

        assertEquals(expectedPoints, owner.getPrestigePoints());
        assertEquals(startingFood, owner.getFood());

    }

    @Test
    void testInventorBonus(){

        InventorPrestigeBonusDecorator decorator = new InventorPrestigeBonusDecorator(baseHandler);

        int inventorsNumber = 5;
        int expectedPoints = inventorsNumber * InventorPrestigeBonusDecorator.PRESTIGE_POINTS_BONUS;

        for(int i =  0; i < inventorsNumber; i++){
            owner.addCard(createInventor().build());
        }

        decorator.handleEndGame(owner);

        assertEquals(expectedPoints, owner.getPrestigePoints());
        assertEquals(startingFood, owner.getFood());

    }


    @Test
    void testShamanBonus(){

        ShamanPrestigeBonusDecorator decorator = new ShamanPrestigeBonusDecorator(baseHandler);

        int shamanNumber = 5;
        int expectedPoints = shamanNumber * ShamanPrestigeBonusDecorator.PRESTIGE_POINTS_BONUS;

        for(int i =  0; i < shamanNumber; i++){
            owner.addCard(createShaman().build());
        }

        decorator.handleEndGame(owner);

        assertEquals(expectedPoints, owner.getPrestigePoints());
        assertEquals(startingFood, owner.getFood());

    }

}
