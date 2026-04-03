package it.polimi.ingsw.am31.am31.modelPackage.deckFolder;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
//import it.polimi.ingsw.am31.am31.handlers.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame.*;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endRound.OneMoreCardDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn.FoodEndTurnDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent.BonusHunterHandleDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.GeneralAdditionalFoodDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.InventorAdditionalFoodDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent.BonusPaintHandlerDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose.NoMalusRitualLoseStrategy;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin.DoubleWinRitualStrategy;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.ArtistDiscountBonus;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.FarmerDiscountBonus;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.InventorDiscountBonus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingDeck extends Deck {



    public BuildingDeck(int nplayers, List<BuildingCard> catalog) {

        List<BuildingCard> list1 = catalog.stream().filter(c -> c.getEra() == 1).collect(Collectors.toList());
        List<BuildingCard> list2 = catalog.stream().filter(c -> c.getEra() == 2).collect(Collectors.toList());
        List<BuildingCard> list3 = catalog.stream().filter(c -> c.getEra() == 3).collect(Collectors.toList());

        //Setup for era 1 buildings
//        List<Card> list1 = new ArrayList<>();
//        list1.add(new BuildingCard(1, 4, 3, (Player player) -> player.addDrawEffect(GeneralAdditionalFoodDecorator::new)));
//        list1.add(new BuildingCard(1, 4, 4, (Player player) -> player.addSustainBonus(FarmerDiscountBonus::new)));
//        list1.add(new BuildingCard(1, 5, 3, (Player player) -> player.addSustainBonus(ArtistDiscountBonus::new)));
//        list1.add(new BuildingCard(1, 5, 2, (Player player) -> player.addRitualLoseEffect(NoMalusRitualLoseStrategy::new)));
//        list1.add(new BuildingCard(1, 3, 3, (Player player) -> player.addEndTurnEffect(FoodEndTurnDecorator::new)));
//        list1.add(new BuildingCard(1, 3, 4, (Player player) -> player.addDrawEffect(InventorAdditionalFoodDecorator::new)));
//
//        //Setup for era 2 buildings
//        List<Card> list2 = new ArrayList<>();
//        list2.add(new BuildingCard(2, 7, 0, (Player player) -> player.addRitualWinEffect(DoubleWinRitualStrategy::new)));
//        list2.add(new BuildingCard(2, 6, 4, (Player player) -> player.increaseStars(3)));
//        list2.add(new BuildingCard(2, 7, 4, (Player player) -> player.addSustainBonus(InventorDiscountBonus::new)));
//        list2.add(new BuildingCard(2, 7, 2, (Player player) -> player.addHuntEffect(BonusHunterHandleDecorator::new)));
//        list2.add(new BuildingCard(2, 6, 4, (Player player) -> player.addEndGameEffect(DoubleBuilderEndGameDecorator::new)));
//        list2.add(new BuildingCard(2, 5, 6, (Player player) -> player.addPaintEffect(BonusPaintHandlerDecorator::new)));
//        list2.add(new BuildingCard(2, 5, 6, (Player player) -> player.addEndGameEffect(CardSetDecorator::new)));
//
//
//        //Setup for era 3 buildings
//        List<Card> list3 = new ArrayList<>();
//        list3.add(new BuildingCard(3, 8, 8, (Player player) -> player.addEndGameEffect(HunterPrestigeBonusDecorator::new)));
//        list3.add(new BuildingCard(3, 7, 6, (Player player) -> player.addEndGameEffect(FarmerPrestigeBonusDecorator::new)));
//        list3.add(new BuildingCard(3, 7, 4, (Player player) -> player.addEndGameEffect(ShamanPrestigeBonusDecorator::new)));
//        list3.add(new BuildingCard(3, 6, 3, (Player player) -> player.addEndGameEffect(BuilderPrestigeBonusDecorator::new)));
//        list3.add(new BuildingCard(3, 7, 4, (Player player) -> player.addEndGameEffect(ArtistPrestigeBonusDecorator::new)));
//        list3.add(new BuildingCard(3, 6, 6, (Player player) -> player.addEndGameEffect(InventorPrestigeBonusDecorator::new)));
//        list3.add(new BuildingCard(3, 9, 3, (Player player) -> player.addEndRoundEffect(OneMoreCardDecorator::new)));
//        list3.add(new BuildingCard(3, 10, 0, (Player player) -> player.addEndGameEffect(FlatPrestigePointsDecorator::new)));


        //lists are shuffled, then we add cards based on nplayers
        Collections.shuffle(list1);
        Collections.shuffle(list2);
        Collections.shuffle(list3);

        for (int i = 0; i < GameConstants.getEraOneBuildings(nplayers); i++) {
            this.eraDeck.add(list1.getFirst());
            list1.removeFirst();
        }

        for (int i = 0; i < GameConstants.getEraTwoBuildings(nplayers); i++) {
            this.eraDeck.add(list2.getFirst());
            list2.removeFirst();
        }

        for (int i = 0; i < GameConstants.getEraThreeBuildings(nplayers); i++) {
            this.eraDeck.add(list3.getFirst());
            list3.removeFirst();
        }



    }
}

//        this.eraDeck.add(list1.getFirst());
//        list1.removeFirst();
//        if(nplayers>2)
//            this.eraDeck.add(list1.getFirst());
//        //ERA 2
//        for (int i = 0; i < 2; i++) {
//            this.eraDeck.add(list2.getFirst());
//            list2.removeFirst();
//            }
//        if(nplayers>3)
//            this.eraDeck.add(list2.getFirst());
        //ERA 3
//        for(int i=0; i<3;i++) {
//            this.eraDeck.add(list3.getFirst());
//            list3.removeFirst();
//        }
//        if(nplayers>2)
//        {
//            this.eraDeck.add(list3.getFirst());
//            list3.removeFirst();
//        }
//        if(nplayers==5)
//        {
//            this.eraDeck.add(list3.getFirst());
//            list3.removeFirst();
//        }

