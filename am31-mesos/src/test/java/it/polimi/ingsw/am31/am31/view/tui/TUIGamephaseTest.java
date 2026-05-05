package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalOfferCard;
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
        Card h = new Hunter("h1",1,2,true);
        Card f = new Farmer("f1",2,2,3);
        Card i = new Inventor("i1",2,2,IconEnum.BREAD);
        ArrayList<LocalOfferCard> track = new ArrayList<>();
        LocalOfferCard a = new LocalOfferCard(new OfferCard("A",1,0,0,2));
        LocalOfferCard b = new LocalOfferCard(new OfferCard("B",0,1,2,2));
        track.add(a);
        track.add(b);

        cards.add(i);
        cards.add(h);
        cards.add(f);
        state.setCardLine(cards, BoardRows.LOWER);
        cards.add(i);
        cards.add(f);
        state.setCardLine(cards, BoardRows.UPPER);
        state.setEra(1);
        state.setCurrentRoundPhase(RoundPhasesEnum.TOTEM_PLACING);
        state.setRoundNumber(1);
        state.setOfferTrack(track);

        gamephase = new TUIGamePhase(null, null, state );
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
