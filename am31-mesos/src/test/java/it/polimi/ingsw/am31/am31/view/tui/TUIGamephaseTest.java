package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectIdsConstants;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectsCatalog;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.localState.LocalOfferCard;
import it.polimi.ingsw.am31.am31.view.localState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;


public class TUIGamephaseTest {
    private TUIGamePhase gamephase;
    private ViewEventBus eventBus = new ViewEventBus();
    private  LocalGameState state = new LocalGameState();
    private TextUserInterface TUI;
    @BeforeEach
    void setup() {
        List<Card> cards = new ArrayList<>();
        EffectsCatalog effects = new EffectsCatalog();

        Card h = createHunter().cardId("h1").era(1).minPlayers(2).mark(true).build();
        Card f = createFarmer().cardId("f1").era(2).minPlayers(2).discount(3).build();
        Card i = createShaman().cardId("i1").era(2).minPlayers(2).stars(3).build();
        Card e = createSustainEvent()
                .cardId("e1")
                .era(1)
                .prestigePointsMalus(2)
                .build();


        BuildingCard bd = createBuilding().cardId("bd1").era(1).cost(2).prestigePointsGained(3).description("40VOLTE S"+"S".repeat(40)).effect(effects.getEffect(EffectIdsConstants.DOUBLE_BUILDER_ENDGAME)).build();

        ArrayList<LocalOfferCard> track = new ArrayList<>();

        LocalPlayerState test1 = new LocalPlayerState("test", Color.BLACK);
        LocalPlayerState test = new LocalPlayerState("achillefrigeri2",Color.WHITE);
        LocalPlayerState rosso = new LocalPlayerState("rossini",Color.RED);

        LocalOfferCard a = new LocalOfferCard("A", "test", false, 1, 0, 0);
        LocalOfferCard b = new LocalOfferCard("B", null, true, 0, 2, 1);
        LocalOfferCard c = new LocalOfferCard("C", "achillefrigeri2", false, 0, 0, 2);
        LocalOfferCard d = new LocalOfferCard("D", "rossini", false, 0, 0, 2);

        track.add(a);
        track.add(b);
        track.add(c);
        track.add(d);

        cards.add(i);cards.add(h);cards.add(f);
        test1.setTribe(cards);test.setTribe(cards);


        cards.add(bd);
        state.setCardLine(cards, BoardRows.LOWER);
        cards.add(i);cards.add(e);cards.add(createHuntEvent().build() );
        state.setCardLine(cards, BoardRows.UPPER);
        state.setEra(1);
        state.setCurrentRoundPhase(RoundPhasesEnum.TOTEM_PLACING);
        state.setRoundNumber(1);state.setOfferTrack(track);
        state.addSolvedEvent(createHuntEvent().build()); state.addSolvedEvent(createSustainEvent().build());
        state.addPlayer(test);state.addPlayer(test1); state.addPlayer(rosso);
        state.setTurnOrder(state.getPlayers());state.setPlayerActing(test1);
        ClientController cont = new ClientController(null, eventBus);
        cont.setLocalNameTest();
        test1.addBuilding(bd);test.addBuilding(bd);
        state.setOfferTrack(track);
        gamephase = new TUIGamePhase(new TextUserInterface(cont,state,eventBus),cont, state );
    }
    @Test
    void TestShouldDraw (){

    }
    @Test
    void TestShouldDrawMain(){
        //should draw the main screen, 6 upper cards, 4 lower, 2 offers, 2 players
        //era 1, round 1, totem placing
        //players have some characters, buildings and events
        gamephase.drawMain();
        state.setCurrentRoundPhase(RoundPhasesEnum.ACTION_PHASE); //should show cards to draw
        state.getPlayers().get(0).addBuilding(createBuilding().cardId("bd20").build());
        //if bonus phase, should display a player has the bonus draw
        gamephase.drawMain();
        state.setCurrentRoundPhase(RoundPhasesEnum.BONUS_DRAWING_PHASE);
        gamephase.drawMain();

    }

    @Test
    void TestShouldDrawOfferTrack(){
        //should print the 4 offerCards
        gamephase.drawOfferTrack();
    }
    @Test
    void TestShouldDrawCards (){
        //should print 7 cards upper and 4 lower
        gamephase.drawCardLines();
    }
    @Test
    void TestShouldDrawPlayers (){

        //should draw players tribes and stats

        gamephase.drawPlayers();
    }
    @Test
    void TestShouldDrawEventsSolved (){
        //should print sus first, then hunt.
        gamephase.drawEventsSolved();
    }
}
