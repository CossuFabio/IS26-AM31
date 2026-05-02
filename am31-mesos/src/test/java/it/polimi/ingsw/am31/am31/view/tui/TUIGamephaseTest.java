package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TUIGamephaseTest {
    private TUIGamePhase gamephase;

    @BeforeEach
    void setup() {
        LocalGameState state = new LocalGameState();
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
}
