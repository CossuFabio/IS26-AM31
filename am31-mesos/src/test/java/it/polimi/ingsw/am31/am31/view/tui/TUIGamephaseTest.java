package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectIdsConstants;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectsCatalog;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferCardMessage;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalOfferCard;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

import java.util.ArrayList;
import java.util.List;


public class TUIGamephaseTest {
    private TUIGamePhase gamephase;

    @BeforeEach
    void setup() {
        LocalGameState state = new LocalGameState();
        List<Card> cards = new ArrayList<>();
        EffectsCatalog effects = new EffectsCatalog();
        Card h = new Hunter("h1",1,2,true);
        Card f = new Farmer("f1",2,2,3);
        Card i = new Inventor("i1",2,2,IconEnum.BREAD);
        BuildingCard bd = new BuildingCard("bd1",1, 2, 3,effects.getEffect(EffectIdsConstants.DOUBLE_BUILDER_ENDGAME));
        ArrayList<LocalOfferCard> track = new ArrayList<>();
        LocalOfferCard a = new LocalOfferCard(new OfferCard("A",1,0,0,2), new OfferCardMessage("A", null,  true));
        LocalOfferCard b = new LocalOfferCard(new OfferCard("B",0,1,2,2), new OfferCardMessage("B", null,  true));
        track.add(a);
        track.add(b);

        cards.add(i);cards.add(h);cards.add(f);
        state.setCardLine(cards, BoardRows.LOWER);
        cards.add(i);cards.add(bd);
        state.setCardLine(cards, BoardRows.UPPER);
        state.setEra(1);
        state.setCurrentRoundPhase(RoundPhasesEnum.TOTEM_PLACING);
        state.setRoundNumber(1);state.setOfferTrack(track);
        LocalPlayerState test1 = new LocalPlayerState("test", Color.BLACK);LocalPlayerState test = new LocalPlayerState("achillefrigeri2",Color.WHITE);
        state.addPlayer(test);state.addPlayer(test1);
        state.setTurnOrder(state.getPlayers());state.setPlayerActing(test1);
        ClientController cont = new ClientController(null, new ViewEventBus());
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
        gamephase.drawMain();
    }

    @Test
    void TestShouldDrawOfferTrack(){
        gamephase.drawOffer();
    }
    @Test
    void TestShouldDrawCards (){
        gamephase.drawCardLines();
    }
    @Test
    void TestShouldDrawPlayers (){
        gamephase.drawPlayers();
    }
}
