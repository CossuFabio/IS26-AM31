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
import it.polimi.ingsw.am31.am31.view.gui.PathConstants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @FXML private StackPane rulesOverlay;
    private int totalPlayers;
    private int currentSlide = 1;

    private List<LobbyDescriptor> currentLobbies;
    //cache for the images (path + image)
    private final Map<String, Image> imageCache = new HashMap<>();

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

        lobbyList.setCellFactory(lv -> new javafx.scene.control.ListCell<LobbyDescriptor>() {
            @Override
            protected void updateItem(LobbyDescriptor lobby, boolean empty) {
                super.updateItem(lobby, empty);
                if (empty || lobby == null) {
                    setGraphic(null);
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    boolean isSelected = lobbyList.getSelectionModel().getSelectedItem() != null &&
                            lobbyList.getSelectionModel().getSelectedItem().getId() == lobby.getId();

                    String border = isSelected
                            ? "black;"
                            : "rgba(255,243,211,1);";

                    HBox cell = new HBox(20);
                    cell.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                    cell.setStyle("-fx-border-color: " + border + " -fx-background-color: rgba(255,243,211,1); -fx-padding: 10;");
                    cell.setCursor(Cursor.HAND);

                    Label id = new Label("Lobby #" + lobby.getId());
                    Label players = new Label("Players: " + (lobby.getnPlayers() - lobby.getFreeSlots()) + "/" + lobby.getnPlayers());
                    id.setStyle("-fx-font-family: 'Inknut Antiqua'; -fx-font-size: 14; -fx-text-fill: #3d1f00;");
                    players.setStyle("-fx-font-family: 'Inknut Antiqua'; -fx-font-size: 14; -fx-text-fill: #3d1f00;");

                    ComboBox<Color> colorBox = new ComboBox<>();
                    colorBox.setId("lobbyColorBox");
                    colorBox.getItems().addAll(lobby.getAvailableColors());
                    colorBox.setPromptText("Color");
                    colorBox.setStyle("-fx-font-family: 'Inknut Antiqua'; -fx-font-size: 14;");

                    if (isSelected) {
                        cell.setEffect(new DropShadow(8, 0, 2, javafx.scene.paint.Color.rgb(61, 31, 0, 0.4)));
                    } else {
                        cell.setEffect(null);
                    }

                    // aggiorna joinColorBox solo quando l'utente sceglie il colore
                    colorBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                        if (newVal != null) {
                            joinColorBox.setValue(newVal);
                        }
                    });

                    // click sulla cella seleziona la lobby e resetta il colore
                    cell.setOnMouseClicked(e -> {
                        lobbyList.getSelectionModel().select(lobby);
                        joinColorBox.setValue(null);
                        colorBox.setValue(null);
                        lobbyList.refresh();
                    });

                    Region space1 = new Region();
                    Region space2 = new Region();
                    HBox.setHgrow(space1, javafx.scene.layout.Priority.ALWAYS);
                    HBox.setHgrow(space2, javafx.scene.layout.Priority.ALWAYS);

                    cell.getChildren().addAll(id, space1, players, space2, colorBox);
                    setGraphic(cell);
                    setStyle("-fx-background-color: transparent;");
                }
            }
        });

// ridisegna le celle quando cambia la selezione
        lobbyList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            lobbyList.refresh();
        });

        new Thread(() -> {
            for (int i = 1; i <= 7; i++) {
                loadImage(PathConstants.RULES_PATH + "rules_" + i + ".png");
            }
        }).start();
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

        joinColorBox.setValue(null);
        lobbyList.getSelectionModel().clearSelection();
        lobbyList.refresh();
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

    @FXML
    private void showRules() {
        rulesOverlay.getChildren().clear();
        Region darkBg = new Region();
        darkBg.setStyle("-fx-background-color: rgba(0,0,0,0.6);");
        darkBg.setMaxWidth(Double.MAX_VALUE);
        darkBg.setMaxHeight(Double.MAX_VALUE);
        darkBg.setOnMouseClicked(e -> closeRules());


        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxWidth(Region.USE_PREF_SIZE);
        vbox.setMaxHeight(Region.USE_PREF_SIZE);

        Image img = loadImage(PathConstants.RULES_PATH + "rules_1.png");
        ImageView rulesIv = new ImageView(img);
        rulesIv.setFitWidth(778);
        rulesIv.setFitHeight(900);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);

        Button back = new Button();
        back.setPrefWidth(389);
        back.setPrefHeight(66);
        back.setText("Back");
        back.setFont(Font.font("Inknut Antiqua Regular", 20));
        back.setStyle("-fx-background-color: rgba(255,243,211,1); -fx-border-color: black; -fx-cursor: hand;");
        back.setDisable(true);

        Button next = new Button();
        next.setPrefWidth(389);
        next.setPrefHeight(66);
        next.setText("Next");
        next.setFont(Font.font("Inknut Antiqua Regular", 20));
        next.setStyle("-fx-background-color: rgba(255,243,211,1); -fx-border-color: black; -fx-cursor: hand;");

        back.setOnAction(e -> backRule(rulesIv, back, next));
        next.setOnAction(e -> nextRule(rulesIv, back, next));

        buttons.getChildren().addAll(back, next);
        vbox.getChildren().addAll(rulesIv,buttons);
        rulesOverlay.getChildren().addAll(darkBg, vbox);

        rulesOverlay.setVisible(true);

    }

    private void backRule(ImageView rulesIv, Button back, Button next) {
        currentSlide--;
        Image img = loadImage(PathConstants.RULES_PATH + "rules_" + currentSlide + ".png");
        rulesIv.setImage(img);
        next.setDisable(false);
        if (currentSlide == 1)
        {
            back.setDisable(true);
        }
    }

    private void nextRule(ImageView rulesIv, Button back, Button next) {
        currentSlide++;

        Image img = loadImage(PathConstants.RULES_PATH + "rules_" + currentSlide + ".png");
        rulesIv.setImage(img);
        back.setDisable(false);
        if (currentSlide == 7)
        {
            next.setDisable(true);
        }
    }

    private void closeRules() {
        rulesOverlay.setVisible(false);
        rulesOverlay.getChildren().clear();
        currentSlide = 1;
    }

    //method to load an image from a path
    private Image loadImage(String path) {
        if (imageCache.containsKey(path)) return imageCache.get(path);
        InputStream stream = getClass().getResourceAsStream(path);
        Image img = stream != null ? new Image(stream) : null;
        imageCache.put(path, img);
        return img;
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
