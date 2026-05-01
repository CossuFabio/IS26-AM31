package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.List;


public class LoginController extends BaseController{
    @FXML private TextField nicknameField;
    @FXML private Button connectButton;
    @FXML private Label errorLabel;
    @FXML private ImageView backgroundImage;
    @FXML private ImageView logoTitle;


    @FXML private StackPane stackPane;


    @FXML
    private void initialize () {
        //disable connect button if nickname field is empty
        connectButton.disableProperty().bind(
                javafx.beans.binding.Bindings.createBooleanBinding(
                        () -> nicknameField.getText().trim().isEmpty(),
                        nicknameField.textProperty()
                )
        );
        //bind image to window size
        backgroundImage.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                backgroundImage.fitWidthProperty().bind(newScene.widthProperty());
                backgroundImage.fitHeightProperty().bind(newScene.heightProperty());
                logoTitle.fitWidthProperty().bind(newScene.widthProperty().multiply(0.5));
            }
        });

        connectButton.setOnMousePressed(e -> connectButton.setOpacity(0.7));
        connectButton.setOnMouseReleased(e -> connectButton.setOpacity(1.0));
        Platform.runLater(() -> nicknameField.getParent().requestFocus());

        stackPane.setOnMouseClicked(e -> stackPane.requestFocus());

    }

    @FXML private void handleConnect() {
        String nickname = nicknameField.getText().trim();

        if (nickname.isEmpty()) return;

        new Thread( () -> {
            try {
                controller.setLocalPlayerUsername(nickname);
                controller.sendRequest(new NewServerConnectionRequest(nickname));
            } catch (Exception e) {
                Platform.runLater( () -> {
                    errorLabel.setText("Connection failed: " + e.getMessage());
                    errorLabel.setVisible(true);
                });
            }
        }).start();
    }

    @Override
    public void onSuccessRegistration(SuccessRegistrationUpdate msg) {
        // Nickname accepted by server, move to waiting room
        sceneManager.showWaitingRoom();
    }

    @Override
    public void onLobbyError(String errorMsg) {
        // Show error message on screen (e.g. username already taken)
        Platform.runLater(() -> {
            errorLabel.setText(errorMsg);
            errorLabel.setVisible(true);
        });
    }

    @Override public void onGameStartUpdate() {}
    @Override public void onRoundPhaseUpdate() {}
    @Override public void onRoundNumberUpdate() {}
    @Override public void onCardLineUpdate() {}
    @Override public void onPlayerListUpdate() {}
    @Override public void onEraUpdate() {}
    @Override public void onOfferTrackUpdate() {}
    @Override public void onPlayerScoreUpdate() {}
    @Override public void onPlayerTribeUpdate() {}
    @Override public void onTurnOrderUpdate() {}
    @Override public void onShowLobbyUpdate(List<LobbyDescriptor> lobbies) {}
}
