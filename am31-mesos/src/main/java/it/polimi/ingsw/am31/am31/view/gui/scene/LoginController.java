package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.FailedRegistrationEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.SuccessRegistrationEvent;
import it.polimi.ingsw.am31.am31.view.gui.PathConstants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.util.List;


public class LoginController extends BaseController{
    @FXML private TextField nicknameField;
    @FXML private Button connectButton;
    @FXML private Label errorLabel;
    @FXML private ImageView backgroundImage;
    @FXML private ImageView logoTitle;
    @FXML private StackPane stackPane;
    double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
    double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();

    @FXML
    private void initialize () {
        Font.loadFont(getClass().getResourceAsStream(PathConstants.ASSETS_PATH + "InknutAntiqua-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream(PathConstants.ASSETS_PATH + "InknutAntiqua-Bold.ttf"), 16);
        logoTitle.setFitHeight(screenHeight*((double) 447 /1080));
        logoTitle.setFitWidth(screenWidth*((double) 993 /1920));
        nicknameField.setPrefWidth(screenWidth*((double) 500 /1920));
        nicknameField.setPrefHeight(screenHeight*((double) 66 /1080));
        connectButton.setPrefWidth(screenHeight*((double) 66 /1080));
        connectButton.setPrefHeight(screenHeight*((double) 66 /1080));
        VBox.setMargin(logoTitle, new Insets(screenHeight*((double) 250 /1080),0,screenHeight*((double) 50/1080),0));

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

        nicknameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleConnect();
            }
        });
    }

    @FXML private void handleConnect() {
        String nickname = nicknameField.getText().trim();

        if (nickname.isEmpty()) return;

        new Thread( () -> {
            try {
//                controller.setLocalPlayerUsername(nickname);
                controller.sendRequest(new NewServerConnectionRequest(nickname));
            } catch (Exception e) {
                Platform.runLater( () -> {
                    errorLabel.setText("Connection failed: " + e.getMessage());
                    errorLabel.setVisible(true);
                });
            }
        }).start();
    }

    @Subscribe
    public void onSuccessRegistration(SuccessRegistrationEvent e) {
        // Nickname accepted by server, move to waiting room
        sceneManager.showWaitingRoom();
    }

    @Subscribe
    public void onLobbyError(FailedRegistrationEvent e) {
        // Show error message on screen (e.g. username already taken)
        Platform.runLater(() -> {
            errorLabel.setText("Username already taken or connection error");
            errorLabel.setVisible(true);
        });
    }


}
