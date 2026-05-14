package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectIdsConstants;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectsCatalog;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalOfferCard;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;


public class TUIGamephaseTest {
    private TUIGamePhase gamephase;

    @BeforeEach
    void setup() {
        LocalGameState state = new LocalGameState();
        List<Card> cards = new ArrayList<>();
        EffectsCatalog effects = new EffectsCatalog();

        Card h = createHunter().cardId("h1").era(1).minPlayers(2).mark(true).build();
        Card f = createFarmer().cardId("f1").era(2).minPlayers(2).discount(3).build();
        Card i = createInventor().cardId("i1").era(2).minPlayers(2).icon(IconEnum.BREAD).build();
        Card e = createSustainEvent()
                .cardId("e1")
                .era(1)
                .prestigePointsMalus(2)
                .build();


        BuildingCard bd = createBuilding().cardId("bd1").era(1).cost(2).prestigePointsGained(3).effect(effects.getEffect(EffectIdsConstants.DOUBLE_BUILDER_ENDGAME)).build();

        ArrayList<LocalOfferCard> track = new ArrayList<>();



        LocalOfferCard a = new LocalOfferCard("A", null, true, 1, 0, 0);
        LocalOfferCard b = new LocalOfferCard("B", null, true, 0, 2, 1);
        track.add(a);
        track.add(b);

        cards.add(i);cards.add(h);cards.add(f);cards.add(bd);
        state.setCardLine(cards, BoardRows.LOWER);
        cards.add(i);cards.add(e);
        state.setCardLine(cards, BoardRows.UPPER);
        state.setEra(1);
        state.setCurrentRoundPhase(RoundPhasesEnum.TOTEM_PLACING);
        state.setRoundNumber(1);state.setOfferTrack(track);
        LocalPlayerState test1 = new LocalPlayerState("test", Color.BLACK);
        LocalPlayerState test = new LocalPlayerState("achillefrigeri2",Color.WHITE);
        state.addPlayer(test);state.addPlayer(test1);
        state.setTurnOrder(state.getPlayers());state.setPlayerActing(test1);
        ViewEventBus eventBus = new ViewEventBus();
        ClientController cont = new ClientController(null, eventBus);
        cont.setLocalNameTest();
        test1.setTribe(cards);test.setTribe(cards);
        test1.addBuilding(bd);test.addBuilding(bd);
        gamephase = new TUIGamePhase(null,cont, state );
    }
    @Test
    void TestShouldDraw (){

    }
    @Test
    void TestShouldDrawMain(){
        //should draw the main screen, 6 upper cards, 4 lower, 2 offers, 2 players
        //era 1, round 1, totem placing
        gamephase.drawMain();
    }

    @Test
    void TestShouldDrawOfferTrack(){
        //should print the 2 offerCards
        gamephase.drawOffer();
    }
    @Test
    void TestShouldDrawCards (){
        //should print 6 cards upper and 4 lower
        gamephase.drawCardLines();
    }
    @Test
    void TestShouldDrawPlayers (){
        //should draw players tribes and stats
        gamephase.drawPlayers();
    }
}
