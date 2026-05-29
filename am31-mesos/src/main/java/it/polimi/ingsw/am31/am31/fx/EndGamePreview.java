package it.polimi.ingsw.am31.am31.fx;

import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.localState.LocalLeaderBoard;
import it.polimi.ingsw.am31.am31.view.localState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameEndedEvent;
import it.polimi.ingsw.am31.am31.view.gui.SceneManager;
import it.polimi.ingsw.am31.am31.view.gui.scene.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class EndGamePreview extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ViewEventBus eventBus = new ViewEventBus();

        VirtualServer fakeServer = new VirtualServer() {
            @Override public void sendRequest(NetworkRequest r) {}
            @Override public void disconnect() {}
            @Override public void setIdentifier(String id) {}
        };

        ClientController fakeController = new ClientController(fakeServer, eventBus);
        fakeController.setLocalNameTest(); // username = "test"

        LocalPlayerState me    = new LocalPlayerState("test",  Color.RED);
        LocalPlayerState fabio = new LocalPlayerState("Fabio", Color.BLUE);
        LocalPlayerState mario = new LocalPlayerState("Mario", Color.YELLOW);
        me.setPrestigePoints(120);
        fabio.setPrestigePoints(95);
        mario.setPrestigePoints(80);
        me.setFood(500);
        fabio.setFood(1000);
        mario.setFood(20);

        LocalGameState fakeState = new LocalGameState();
        fakeState.setPlayers(List.of(me, fabio, mario));
        fakeState.setTurnOrder(List.of(me, fabio, mario));
        fakeState.setRoundNumber(9);
        fakeState.setEra(3);
        fakeState.setCurrentRoundPhase(RoundPhasesEnum.TOTEM_PLACING);
        fakeState.setLeaderboard(List.of(
                new LocalLeaderBoard(me,    true),
                new LocalLeaderBoard(fabio, false),
                new LocalLeaderBoard(mario, false)
        ));

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/it/polimi/ingsw/am31/am31/view/gui/scene/game.fxml"));
        Parent root = loader.load();

        GameController gameController = loader.getController();
        SceneManager sceneManager = new SceneManager(stage, fakeController, fakeState, eventBus);

        gameController.setController(fakeController);
        gameController.setLocalGameState(fakeState);
        gameController.setSceneManager(sceneManager);
        gameController.setEventBus(eventBus);
        eventBus.register(gameController);

        stage.setScene(new Scene(root, 1920, 1080));
        stage.setTitle("EndGame Preview – Round 9");
        stage.show();

        // fire GameEndedEvent after 3 seconds to test the transition
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                eventBus.post(new GameEndedEvent());
            } catch (InterruptedException ignored) {}
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
