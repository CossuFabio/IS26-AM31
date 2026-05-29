package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.view.localState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.*;
import it.polimi.ingsw.am31.am31.view.gui.PathConstants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fusesource.jansi.Ansi.ansi;


public class WaitingRoomController extends BaseController {
    // Images
    @FXML private ImageView backgroundImage;
    @FXML private ImageView logoTitle;

    @FXML private StackPane stackPane;
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
    @FXML private VBox lobbyWaitingList;
    @FXML private Button backButton1;
    @FXML private Button joinButton;
    @FXML private ComboBox<Color> joinColorBox;
    @FXML private StackPane rulesOverlay;
    @FXML private Button rulesButton;
    @FXML private HBox hbox3;
    @FXML private VBox vbox4;

    private int totalPlayers;
    private int currentSlide = 1;
    double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
    double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();


    private List<LobbyDescriptor> currentLobbies;
    //cache for the images (path + image)
    private final Map<String, Image> imageCache = new HashMap<>();

    @FXML
    public void initialize() {
        backgroundImage.fitWidthProperty().bind(stackPane.widthProperty());
        backgroundImage.fitHeightProperty().bind(stackPane.heightProperty());
        Font.loadFont(getClass().getResourceAsStream(PathConstants.ASSETS_PATH + "InknutAntiqua-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream(PathConstants.ASSETS_PATH + "InknutAntiqua-Bold.ttf"), 16);
        //add options to combobox
        playersBox.getItems().addAll(2,3,4,5);
        colorBox.getItems().addAll("Red","Blue","Yellow","White","Black");

        gameButton.setPrefHeight(screenHeight*((double) 66 /1080));
        gameButton.setPrefWidth(screenWidth*((double) 500 /1920));
        lobbyButton.setPrefHeight(screenHeight*((double) 66 /1080));
        lobbyButton.setPrefWidth(screenWidth*((double) 500 /1920));
        rulesButton.setPrefHeight(screenHeight*((double) 66 /1080));
        rulesButton.setPrefWidth(screenWidth*((double) 500 /1920));
        logoTitle.setFitHeight(screenHeight*((double) 447 /1080));
        logoTitle.setFitWidth(screenWidth*((double) 993 /1920));
        vbox1.setSpacing(20);
        playersBox.setPrefWidth(screenWidth*((double) 500 /1920));
        playersBox.setPrefHeight(screenHeight*((double) 66 /1080));
        colorBox.setPrefWidth(screenWidth*((double) 500 /1920));
        colorBox.setPrefHeight(screenHeight*((double) 66 /1080));
        backButton.setPrefWidth(screenWidth*((double) 250 /1920));
        backButton.setPrefHeight(screenHeight*((double) 66 /1080));
        createButton.setPrefWidth(screenWidth*((double) 250 /1920));
        createButton.setPrefHeight(screenHeight*((double) 66 /1080));
        lobbyList.setPrefWidth(screenWidth*((double) 800 /1920)); //old 500
        lobbyList.setPrefHeight(screenHeight*((double) 250 /1080)); //old 200
        joinColorBox.setPrefWidth(screenWidth*((double) 500 /1920));
        backButton1.setPrefWidth(screenWidth*((double) 400 /1920));
        backButton1.setPrefHeight(screenHeight*((double) 66 /1080));
        joinButton.setPrefWidth(screenWidth*((double) 400 /1920));
        joinButton.setPrefHeight(screenHeight*((double) 66 /1080));
        hbox3.setPrefWidth(screenWidth*((double) 200 /1920));
        hbox3.setPrefHeight(screenHeight*((double) 100 /1080));
        waitingLabel.setPrefWidth(screenWidth*((double) 500 /1920));
        waitingLabel.setPrefHeight(screenHeight*((double) 66 /1080));
        lobbyWaitingList.setPrefWidth(screenWidth*((double) 500 /1920));

        VBox.setMargin(logoTitle, new Insets(screenHeight*((double) 200 /1080),0,screenHeight*((double) 50/1080),0));
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
        clearErrorLabels();
        vbox1.setVisible(false);
        vbox2.setVisible(true);
    }

    @FXML
    private void handleShowLobbies() {
        clearErrorLabels();
        vbox1.setVisible(false);
        vbox3.setVisible(true);
        // Request lobbies from server
        new Thread(() -> {
            try {
                controller.requestShowLobbies();
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
        vbox4.setVisible(false);

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
                controller.requestNewGame(totalPlayers, color);
                Platform.runLater(() -> {
                    vbox2.setVisible(false);
                    vbox4.setVisible(true);
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

        totalPlayers = selected.getnPlayers();

        new Thread(() -> {
            try {
                controller.requestJoinGame(joinColorBox.getValue(), selected.getId());
                Platform.runLater(() -> {
                    vbox3.setVisible(false);
                    vbox4.setVisible(true);
                    int current = localGameState.getPlayers().size();
                    waitingLabel.setText("Waiting for other players: " + current + "/" + totalPlayers);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Subscribe
    public void playersChanged(PlayersInLobbyChangedEvent e) {

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
        vbox.setStyle("-fx-background-color: rgba(255,243,211,1); -fx-border-color: black;");
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxWidth(Region.USE_PREF_SIZE);
        vbox.setMaxHeight(Region.USE_PREF_SIZE);

        Image img = loadImage(PathConstants.RULES_PATH + "rules_1.jpg");
        ImageView rulesIv = new ImageView(img);
        rulesIv.setFitWidth(screenWidth*((double) 900 / 1920));
        rulesIv.setFitHeight(screenHeight*((double) 900 / 1080));

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);

        Button back = new Button();
        back.setPrefWidth(screenWidth*((double) 900 / 1920) / 2);
        back.setPrefHeight(screenHeight*((double) 66 / 1080));
        back.setText("Back");
        back.setFont(Font.font("Inknut Antiqua Regular", 20));
        back.setStyle("-fx-background-color: rgba(255,243,211,1); -fx-border-color: black; -fx-border-width: 1 1 0 0; -fx-cursor: hand;");
        back.setDisable(true);

        Button next = new Button();
        next.setPrefWidth(screenWidth*((double) 900 / 1920) / 2);
        next.setPrefHeight(screenHeight*((double) 66 / 1080));
        next.setText("Next");
        next.setFont(Font.font("Inknut Antiqua Regular", 20));
        next.setStyle("-fx-background-color: rgba(255,243,211,1); -fx-border-color: black; -fx-border-width: 1 0 0 0;  -fx-cursor: hand;");

        back.setOnAction(e -> backRule(rulesIv, back, next));
        next.setOnAction(e -> nextRule(rulesIv, back, next));

        buttons.getChildren().addAll(back, next);
        vbox.getChildren().addAll(rulesIv,buttons);
        rulesOverlay.getChildren().addAll(darkBg, vbox);

        rulesOverlay.setVisible(true);

    }

    private void backRule(ImageView rulesIv, Button back, Button next) {
        currentSlide--;
        Image img = loadImage(PathConstants.RULES_PATH + "rules_" + currentSlide + ".jpg");
        rulesIv.setImage(img);
        next.setDisable(false);
        if (currentSlide == 1)
        {
            back.setDisable(true);
        }
    }

    private void nextRule(ImageView rulesIv, Button back, Button next) {
        currentSlide++;

        Image img = loadImage(PathConstants.RULES_PATH + "rules_" + currentSlide + ".jpg");
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
            lobbyWaitingList.getChildren().clear();

            for (LocalPlayerState player : e.getPlayers()) {
                Label nicknameLabel = new Label(player.getNickname());
                nicknameLabel.setStyle("-fx-font-family: 'Inknut Antiqua'; -fx-font-size: 20; -fx-text-fill: black;");
                nicknameLabel.setTextOverrun(OverrunStyle.ELLIPSIS);

                Label colorLabel = new Label(" | Color: " + player.getColor());
                colorLabel.setStyle("-fx-font-family: 'Inknut Antiqua'; -fx-font-size: 20; -fx-text-fill: black;");
                colorLabel.setMinWidth(Region.USE_PREF_SIZE);

                HBox row = new HBox(nicknameLabel, colorLabel);
                row.setStyle("-fx-padding: 0 10 0 10;");
                row.setAlignment(Pos.CENTER);
                row.setPrefHeight(screenHeight*((double) 66 /1080));
                Separator separator = new Separator();
                lobbyWaitingList.getChildren().addAll(row, separator);
            }

            // aggiorna anche il contatore
            waitingLabel.setText("Waiting for other players: " + e.getPlayers().size() + "/" + totalPlayers);
        });
    }

    @Subscribe
    public void unableToJoin(FailedJoinLobby e) {
        Platform.runLater(() -> {
            handleBack();
            clearErrorLabels();
            Label errorPrompt = new Label("Unable to enter Lobby. " + e.getMessage());
            errorPrompt.setStyle("-fx-text-fill: white");
            errorPrompt.setFont(Font.font("Inknut Antiqua Regular", 20));
            errorPrompt.setId("errorLabel");
            VBox.setMargin(errorPrompt, new Insets(20, 0, 0, 0));
            vbox1.getChildren().add(errorPrompt);
        });
    }

    @Subscribe
    public void invalidColorSelected(InvalidColorPickEvent e) {
        Platform.runLater(() -> {
            handleBack();
            clearErrorLabels();
            Label errorPrompt = new Label("Invalid color pick!");
            errorPrompt.setStyle("-fx-text-fill: white");
            errorPrompt.setFont(Font.font("Inknut Antiqua Regular", 20));
            errorPrompt.setId("errorLabel");
            VBox.setMargin(errorPrompt, new Insets(20, 0, 0, 0));
            vbox1.getChildren().add(errorPrompt);
        });
    }

    private void clearErrorLabels() {
        vbox1.getChildren().removeIf(n -> "errorLabel".equals(n.getId()));
    }

}
