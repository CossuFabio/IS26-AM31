package it.polimi.ingsw.am31.am31.handlers.endGameHandlerTest;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.testUtils.emptyHandlers.EmptyEndGameHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;

public class ArtistPrestigeBonusDecoratorTest {

    private Player owner;
    private EmptyEndGameHandler baseHandler;

    @BeforeEach
    void setUp(){
        owner = createPlayer().build();
        baseHandler = new EmptyEndGameHandler();
    }

    @Test
    void testHandleEndGame(){

    }



}
