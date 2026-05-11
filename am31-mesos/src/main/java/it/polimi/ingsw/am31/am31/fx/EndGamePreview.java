package it.polimi.ingsw.am31.am31.fx;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalLeaderBoard;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.gui.scene.EndGameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class EndGamePreview extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/it/polimi/ingsw/am31/am31/view/gui/scene/endGame.fxml"));
        Parent root = loader.load();

        EndGameController controller = loader.getController();

        // stato finto
        LocalGameState fakeState = new LocalGameState();
        fakeState.setPlayers(List.of(
                new LocalPlayerState("Simone", Color.RED),
                new LocalPlayerState("Fabio",  Color.BLUE),
                new LocalPlayerState("Mario",  Color.YELLOW)
        ));
        fakeState.getPlayers().get(0).setPrestigePoints(120);
        fakeState.getPlayers().get(1).setPrestigePoints(95);
        fakeState.getPlayers().get(2).setPrestigePoints(80);

        fakeState.getPlayers().get(0).setFood(500);
        fakeState.getPlayers().get(1).setFood(1000);
        fakeState.getPlayers().get(2).setFood(20);

        fakeState.setLeaderboard(List.of(
                new LocalLeaderBoard(fakeState.getPlayers().get(0), true),
                new LocalLeaderBoard(fakeState.getPlayers().get(1), false),
                new LocalLeaderBoard(fakeState.getPlayers().get(2), false)
        ));

        controller.setLocalGameState(fakeState);

        stage.setScene(new Scene(root, 1920, 1080));
        stage.setTitle("EndGame Preview");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}