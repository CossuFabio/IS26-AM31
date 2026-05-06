package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameStartingEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.PlayersInLobbyChangedEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.ShowLobbyEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.SuccessRegistrationEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.List;


public class WaitingRoomController extends BaseController {
    // Images
    @FXML private ImageView backgroundImage;
    @FXML private ImageView logoTitle;

    // Panel 1 - main menu
    @FXML private VBox vbox1;
    @FXML private Button gameButton;
    @FXML private Button lobbyButton;

    // Panel 2 - create game
    @FXML private VBox vbox2;
    @FXML private ComboBox<Integer> playersBox;
    @FXML private ComboBox<String> colorBox;
    @FXML private Button backButton;
    @FXML private Button createButton;
    @FXML private Label waitingLabel;

    // Panel 3 - show lobbies
    @FXML private VBox vbox3;
    @FXML private ListView<LobbyDescriptor> lobbyList;
    @FXML private Button backButton1;
    @FXML private Button joinButton;
    @FXML private ComboBox<Color> joinColorBox;
    private int totalPlayers;

    private List<LobbyDescriptor> currentLobbies;

    @FXML
    public void initialize() {
        //add options to combobox
        playersBox.getItems().addAll(2,3,4,5);
        colorBox.getItems().addAll("Red","Blue","Yellow","White","Black");

        //disable join button until a lobby and a color are selected
        joinButton.disableProperty().bind(
                lobbyList.getSelectionModel().selectedItemProperty().isNull()
                        .or(joinColorBox.valueProperty().isNull())
        );

        //disable create button until both comboboxes are selected
        createButton.disableProperty().bind(
                playersBox.valueProperty().isNull()
                        .or(colorBox.valueProperty().isNull())
        );

        //bind images to window size
        backgroundImage.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                backgroundImage.fitWidthProperty().bind(newScene.widthProperty());
                backgroundImage.fitHeightProperty().bind(newScene.heightProperty());
                logoTitle.fitWidthProperty().bind(newScene.widthProperty().multiply(0.5));
            }
        });

        // Cell factory for lobby list
        lobbyList.setCellFactory(lv -> new javafx.scene.control.ListCell<LobbyDescriptor>() {
            @Override
            protected void updateItem(LobbyDescriptor lobby, boolean empty) {
                super.updateItem(lobby, empty);
                if (empty || lobby == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    javafx.scene.layout.HBox cell = new javafx.scene.layout.HBox(20);
                    cell.setStyle("-fx-background-color: rgba(255,243,211,1); -fx-padding: 10;");
                    javafx.scene.control.Label id = new javafx.scene.control.Label("Lobby #" + lobby.getId());
                    javafx.scene.control.Label players = new javafx.scene.control.Label(
                            "Players: " + (lobby.getnPlayers() - lobby.getFreeSlots()) + "/" + lobby.getnPlayers()
                    );
                    id.setStyle("-fx-font-family: 'Inknut Antiqua'; -fx-font-size: 14; -fx-text-fill: #3d1f00;");
                    players.setStyle("-fx-font-family: 'Inknut Antiqua'; -fx-font-size: 14; -fx-text-fill: #3d1f00;");
                    cell.getChildren().addAll(id, players);
                    setGraphic(cell);
                    setStyle("-fx-background-color: transparent;");
                }
            }
        });

        lobbyList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                joinColorBox.getItems().clear();
                joinColorBox.getItems().addAll(newVal.getAvailableColors());
            }
        });
    }

    @FXML
    private void handleCreateGame() {
        vbox1.setVisible(false);
        vbox2.setVisible(true);
    }

    @FXML
    private void handleShowLobbies() {
        vbox1.setVisible(false);
        vbox3.setVisible(true);
        // Request lobbies from server
        new Thread(() -> {
            try {
                controller.sendRequest(new ShowLobbyNetworkRequest());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void handleBack() {
        vbox2.setVisible(false);
        vbox3.setVisible(false);
        vbox1.setVisible(true);
    }

    @FXML
    private void handleCreate() {
        totalPlayers = playersBox.getValue();
        Color color = Color.valueOf(colorBox.getValue().toUpperCase());

        new Thread(() -> {
            try {
                controller.sendRequest(new NewGameNetworkRequest(totalPlayers, color));
                Platform.runLater(() -> {
                    vbox2.setVisible(false);
                    waitingLabel.setVisible(true);
                    waitingLabel.setText("Waiting for players: 1/" + totalPlayers);
                } );
            } catch (Exception e) {
                Platform.runLater(() -> System.err.println("Error creating game: " + e.getMessage()));
            }
        }).start();
    }

    @FXML
    private void handleJoin() {
        LobbyDescriptor selected = lobbyList.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        new Thread(() -> {
            try {
                controller.sendRequest(new JoinGameNetworkRequest(joinColorBox.getValue(), selected.getId()));
                 } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Subscribe
    public void onShowLobbyUpdate(ShowLobbyEvent e) {
        List<LobbyDescriptor> lobbies = e.getLobbies();
        Platform.runLater(() -> {
            currentLobbies = lobbies;
            lobbyList.getItems().clear();
            lobbyList.getItems().addAll(lobbies);
        });
    }

    @Subscribe
    public void onGameStartUpdate(GameStartingEvent e) {
        sceneManager.showGame();
    }

    @Subscribe
    public void onSuccessRegistration(SuccessRegistrationEvent e) {
        
    }

    @Subscribe
    public void onPlayerListUpdate(PlayersInLobbyChangedEvent e) {
        Platform.runLater(() -> {
            int current = localGameState.getPlayers().size();
            waitingLabel.setText("Waiting for other players: " + current + "/" + totalPlayers);
        });
    }

}
