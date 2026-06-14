package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.*;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.localState.LocalOfferCard;
import it.polimi.ingsw.am31.am31.view.localState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.gui.PathConstants;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Controller of the game FXML file.
 */
public class GameController extends BaseController {
    @FXML private VBox centerHBox;
    @FXML private StackPane rootStackPane;
    @FXML private Label roundLabel;
    @FXML private Label eraLabel;
    @FXML private Label turnLabel;
    @FXML private Label phaseLabel;
    @FXML private ImageView deckImage;
    @FXML private HBox upperRowContainer;
    @FXML private HBox middleRowContainer;
    @FXML private HBox offerTrackContainer;
    @FXML private HBox lowerRowContainer;
    @FXML private VBox playersContainer;
    @FXML private HBox myNickname;
    @FXML private Label myFoodLabel;
    @FXML private Label myPPLabel;
    @FXML private HBox myTribeContainer;
    @FXML private HBox myBuildingsContainer;
    @FXML private ScrollPane tribeScrollPane;
    @FXML private ScrollPane buildingsScrollPane;
    @FXML private ImageView foodIcon;
    @FXML private ImageView PPIcon;
    @FXML private StackPane deckOverlay;
    @FXML private Label ownerName;
    @FXML private ScrollPane tribePlayerScrollPane;
    @FXML private ScrollPane buildingsPlayerScrollPane;
    @FXML private HBox tribePlayerContainer;
    @FXML private HBox buildingsPlayerContainer;
    @FXML private StackPane cardTypeOverlay;
    @FXML private Label cardTypeLabel;
    @FXML private ScrollPane cardTypeScrollPane;
    @FXML private HBox cardTypeCardsContainer;
    @FXML private StackPane rulesOverlay;
    @FXML private ImageView backgroundImage;
    @FXML private HBox topBar;
    @FXML private Button skipButton1;
    @FXML private Button skipButton2;
    @FXML private Button playersButton;
    @FXML private Button rulesButton;
    @FXML private Button summaryButton;
    @FXML private StackPane playerOverlay;
    @FXML private VBox playersPanel;

    private int currentSlide = 1;

    double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
    double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();

    private final double cardHeight = screenHeight / 6;
    private final double cardWidth = cardHeight*0.7;

    private RoundPhasesEnum lastShownPhase = null;
    private String lastShownActing = null;

    private Label promptLabel = null;

    //cache for the images (path + image)
    private final Map<String, Image> imageCache = new HashMap<>();
    //set of player's tribe card IDs
    private final Set<String> tribeKnownTypes = new HashSet<>();
    //set of player's building card IDs
    private final Set<String> knownBuildingIds = new HashSet<>();
    //map to keep track of how many cards you have already drawn from each row in the current turn
    private final Map<BoardRows, Integer> pendingDraws = new HashMap<>();

    //boolean thread-safe used as flag to avoid multiple refresh to UI
    private final AtomicBoolean uiRefreshPending = new AtomicBoolean(false);

    private final List<Card> pendingEvents = new ArrayList<>();
    private boolean showingEvent = false;
    private VBox currentEventVbox = null;

    @FXML
    private void initialize() {
        Font.loadFont(getClass().getResourceAsStream(PathConstants.ASSETS_PATH + "InknutAntiqua-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream(PathConstants.ASSETS_PATH + "InknutAntiqua-Bold.ttf"), 16);
        Image foodImg = loadImage(PathConstants.ASSETS_PATH + "food.png");
        Image ppImg = loadImage(PathConstants.ASSETS_PATH + "pp.png");
        if (foodImg != null) foodIcon.setImage(foodImg);
        if (ppImg != null) PPIcon.setImage(ppImg);

        rulesButton.prefWidthProperty().bind(summaryButton.widthProperty());
        backgroundImage.fitWidthProperty().bind(rootStackPane.widthProperty());
        backgroundImage.fitHeightProperty().bind(rootStackPane.heightProperty());
        VBox.setMargin(centerHBox, new Insets(((double) 1 /42)*screenHeight, 0, ((double) 1 /42)*screenHeight, 0));
        centerHBox.setSpacing(((double) 1 /84)*screenHeight);
        buildingsScrollPane.setPrefHeight(cardHeight);
        tribeScrollPane.setPrefHeight(cardHeight);
        VBox.setMargin(tribeScrollPane, new Insets(0,0,screenHeight*((double) 1 /84),0));
        VBox.setMargin(buildingsScrollPane, new Insets(0,0,screenHeight*((double) 1 /42),0));
        middleRowContainer.setPrefHeight(cardHeight);
        roundLabel.setPrefWidth(screenWidth*((double) 200 /1920));
        eraLabel.setPrefWidth(screenWidth*((double) 200 /1920));
        turnLabel.setPrefWidth(screenWidth*((double) 200 /1920));
        phaseLabel.setPrefWidth(screenWidth*((double) 200 /1920));
        topBar.setPrefHeight(screenHeight*((double) 2 /42));
        skipButton1.setPrefHeight(screenHeight*((double) 2 /42));
        skipButton1.setPrefWidth(screenWidth*0.10);
        skipButton2.setPrefHeight(screenHeight*((double) 2 /42));
        skipButton2.setPrefWidth(screenWidth*0.10);
        playersButton.setPrefHeight(screenHeight*((double) 2 /42));
        playersButton.setPrefWidth(screenWidth*0.10);
        rulesButton.setPrefHeight(screenHeight*((double) 2 /42));
        summaryButton.setPrefHeight(screenHeight*((double) 2 /42));
        myNickname.setMaxWidth(screenWidth*((double)250/1920));
        playersPanel.setPrefWidth(screenWidth*0.4);

        makeScrollPaneTransparent(tribeScrollPane);
        makeScrollPaneTransparent(buildingsScrollPane);
        makeScrollPaneTransparent(cardTypeScrollPane);
        makeScrollPaneTransparent(tribePlayerScrollPane);
        makeScrollPaneTransparent(buildingsPlayerScrollPane);
        buildingsScrollPane.setVisible(true);
        tribeScrollPane.setVisible(true);

        new Thread(() -> {
            for (int i = 1; i <= 7; i++) {
                loadImage(PathConstants.RULES_PATH + "rules_" + i + ".png");
            }
        }).start();
    }

    private void makeScrollPaneTransparent(ScrollPane sp) {
        sp.getStyleClass().add("transparent-scroll");
    }

    /**
     * Handles a {@link BoardUpdateEvent} by refreshing the UI.
     *
     * @param event the board update event
     */
    @Subscribe
    public void onBoardUpdate(BoardUpdateEvent event) {
        //compareAndSet check if uiRefreshPending is false, if so sets it to true and return true, if not return false
        if (uiRefreshPending.compareAndSet(false, true)) {
            Platform.runLater(() -> {
                uiRefreshPending.set(false);
                RoundPhasesEnum phase = localGameState.getCurrentRoundPhase();
                LocalPlayerState acting = localGameState.getPlayerActing();
                refreshUI(phase, acting,localGameState);
            });
        }
    }

    /**
     * Handles a {@link GameEndedEvent} by showing a prompt and then switching to end game scene.
     *
     * @param event the game ended event
     */
    @Subscribe
    public void onGameEnded(GameEndedEvent event) {
        Platform.runLater(() -> {
            rulesOverlay.getChildren().clear();

            Region darkBg = new Region();
            darkBg.setStyle("-fx-background-color: rgba(0,0,0,0.6);");
            darkBg.setMaxWidth(Double.MAX_VALUE);
            darkBg.setMaxHeight(Double.MAX_VALUE);

            Label endLabel = new Label("The game is over! Going to the standings...");
            endLabel.setFont(Font.font("Inknut Antiqua Regular", 20));
            endLabel.setStyle("-fx-background-color: rgba(255,243,211,1); -fx-border-color: black; -fx-padding: 15;");
            StackPane.setAlignment(endLabel, Pos.CENTER);

            endLabel.setOpacity(0.0);
            rulesOverlay.getChildren().addAll(darkBg, endLabel);
            rulesOverlay.toFront();
            rulesOverlay.setVisible(true);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), endLabel);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.setOnFinished(e -> sceneManager.showEndGame());

            fadeIn.setOnFinished(e -> pause.play());
            fadeIn.play();
        });
    }

    /**
     * Handles a {@link GameCrashedEvent} by showing an alert and switching to waiting room scene.
     *
     * @param event the game crashed event
     */
    @Subscribe
    public void onGameCrashed(GameCrashedEvent event) {
        localGameState.reset();
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Interrupted");
            alert.setHeaderText(null);
            alert.setContentText("A player disconnected. Returning to main menu.");
            alert.showAndWait();
            sceneManager.showWaitingRoom();
        });
    }

    /**
     * Sets the local game state shared across all scene controllers.
     *
     * @param localGameState the LocalGameState instance
     */
    @Override
    public void setLocalGameState(LocalGameState localGameState) {
        super.setLocalGameState(localGameState);
        Platform.runLater(() -> {
            RoundPhasesEnum phase = localGameState.getCurrentRoundPhase();
            LocalPlayerState acting = localGameState.getPlayerActing();
            refreshUI(phase, acting, localGameState);
        });
    }

    /**
     * Refreshes the UI calling every method to refresh each part of the screen.
     *
     * @param phase current phase of the game
     * @param acting current player acting
     * @param gameState local game state
     */
    private void refreshUI(RoundPhasesEnum phase, LocalPlayerState acting, LocalGameState gameState) {
        String myNick = controller.getLocalPlayerUsername();
        boolean isMyDrawTurn = acting != null && acting.getNickname().equals(myNick)
                && (phase == RoundPhasesEnum.ACTION_PHASE || phase == RoundPhasesEnum.BONUS_DRAWING_PHASE);
        if (!isMyDrawTurn || phase != lastShownPhase) pendingDraws.clear();
        refreshDeck();
        refreshTopBar(phase, acting);
        refreshUpperRow(phase, acting);
        refreshOfferTrack(phase, acting, gameState);
        refreshLowerRow(phase, acting);
        refreshPlayerPanel(acting);
        refreshMyInfo();
        showPrompt(phase, acting);
        refreshSkip(phase, acting);
    }

    /**
     * Refreshes the skip buttons using the phase and the player acting.
     *
     * @param phase current phase of the game
     * @param acting current player acting
     */
    private void refreshSkip(RoundPhasesEnum phase, LocalPlayerState acting) {
        String myNick = controller.getLocalPlayerUsername();
        boolean isMyTurn = acting != null && acting.getNickname().equals(myNick);
        boolean isDrawPhase = phase == RoundPhasesEnum.ACTION_PHASE || phase == RoundPhasesEnum.BONUS_DRAWING_PHASE;

        if (!isMyTurn || !isDrawPhase) {
            skipButton1.setDisable(true);
            skipButton2.setDisable(true);
            return;
        }

        int remainingFromUpper = allowedDrawsFromRow(BoardRows.UPPER) - pendingDraws.getOrDefault(BoardRows.UPPER, 0);
        int remainingFromLower = allowedDrawsFromRow(BoardRows.LOWER) - pendingDraws.getOrDefault(BoardRows.LOWER, 0);

        CountVisitor countVisitor = new CountVisitor();

        localGameState.getBoard().getUpperLine()
                .forEach(card -> card.acceptVisit(countVisitor));
        int charactersUpperLine = countVisitor.getTotalCharacters();

        countVisitor.reset();
        localGameState.getBoard().getUnderLine()
                .forEach(card -> card.acceptVisit(countVisitor));

        int charactersLowerLine = countVisitor.getTotalCharacters();

        skipButton1.setDisable(!(remainingFromUpper > 0 &&
                ( (charactersUpperLine == 0 && phase == RoundPhasesEnum.ACTION_PHASE) ||
                        (phase == RoundPhasesEnum.BONUS_DRAWING_PHASE) )));
        skipButton2.setDisable(!(remainingFromLower > 0 && charactersLowerLine == 0));
    }

    @FXML
    private void skipDrawFromUpper() {
        skipButton1.setDisable(true);
        new Thread(() -> {
                    try {
                        controller.requestSkip(BoardRows.UPPER);
                    } catch (Exception e) {
                        System.err.println("Skip failed: " + e.getMessage());
                    }
                }).start();
    }

    @FXML
    private void skipDrawFromLower() {
        skipButton2.setDisable(true);
        new Thread(() -> {
            try {
                controller.requestSkip(BoardRows.LOWER);
            } catch (Exception e) {
                System.err.println("Skip failed: " + e.getMessage());
            }
        }).start();
    }

    private void refreshDeck() {
        int era = localGameState.getEra();
        String backName = "card_back_" + era + ".png";
        Image back = loadImage(PathConstants.CARDS_PATH + backName);
        if (back != null) deckImage.setImage(back);
        deckImage.setFitWidth(cardWidth);
        deckImage.setFitHeight(cardHeight);
        if (localGameState.getRoundNumber() == 10) deckImage.setVisible(false);
    }

    private Image loadImage(String path) {
        if (imageCache.containsKey(path)) return imageCache.get(path);
        InputStream stream = getClass().getResourceAsStream(path);
        Image img = stream != null ? new Image(stream) : null;
        imageCache.put(path, img);
        return img;
    }

    private void refreshTopBar(RoundPhasesEnum phase, LocalPlayerState acting) {
        roundLabel.setText("Round: " + localGameState.getRoundNumber());
        eraLabel.setText("Era: " + localGameState.getEra());
        if (acting != null) {
            boolean isMyTurn = acting.getNickname().equals(controller.getLocalPlayerUsername());
            turnLabel.setText(isMyTurn ? "Your Turn" : "Turn: " + acting.getNickname());
        }
        phaseLabel.setText(phaseToString(phase));
    }

    private String phaseToString(RoundPhasesEnum phase) {
        return switch (phase) {
            case TOTEM_PLACING       -> "Place the totem";
            case ACTION_PHASE        -> "Draw a card";
            case BONUS_DRAWING_PHASE -> "Draw bonus";
            case END_TURN            -> "End turn";
            case GAME_STARTING       -> "Game starting...";
            case GAME_ENDED          -> "Game ended";
        };
    }

    private void refreshUpperRow(RoundPhasesEnum phase, LocalPlayerState acting) {
        upperRowContainer.getChildren().clear();
        for (Card card : localGameState.getBoard().getUpperLine()) {
            upperRowContainer.getChildren().add(buildCardNode(card, BoardRows.UPPER, phase, acting));
        }
    }

    private void refreshOfferTrack(RoundPhasesEnum phase, LocalPlayerState acting, LocalGameState gameState) {
        offerTrackContainer.getChildren().clear();
        offerTrackContainer.getChildren().add(buildTurnOrderTile(gameState));
        for (LocalOfferCard offer : localGameState.getBoard().getOfferTrack()) {
            offerTrackContainer.getChildren().add(buildOfferTileNode(offer, phase, acting));
        }
    }

    private void refreshLowerRow(RoundPhasesEnum phase, LocalPlayerState acting) {
        lowerRowContainer.getChildren().clear();
        for (Card card : localGameState.getBoard().getUnderLine()) {
            lowerRowContainer.getChildren().add(buildCardNode(card, BoardRows.LOWER, phase, acting));
        }
    }

    private void refreshPlayerPanel(LocalPlayerState acting) {
        playersContainer.getChildren().clear();
        String myNick = controller.getLocalPlayerUsername();
        for (LocalPlayerState player : localGameState.getPlayers()) {
                playersContainer.getChildren().add(buildPlayerCard(player, acting));
        }
    }

    private void refreshMyInfo() {
        String myNick = controller.getLocalPlayerUsername();
        LocalPlayerState me = localGameState.getPlayers().stream()
                .filter(p -> p.getNickname().equals(myNick))
                .findFirst()
                .orElse(null);
        if (me == null) return;
        myNickname.getChildren().clear();
        Image totemImg = loadImage(PathConstants.TOTEM_PATH  + me.getColor().name().toLowerCase() + ".png");
        if (totemImg != null) {
            ImageView totemIv = new ImageView(totemImg);
            totemIv.setFitWidth(screenWidth*((double) 35 /1920));
            totemIv.setPreserveRatio(true);
            myNickname.getChildren().add(totemIv);
        }
        Label nickname = new Label();
        nickname.setText(myNick);
        nickname.setStyle("-fx-text-fill: white;");
        nickname.setFont(Font.font("Inknut Antiqua Regular", 30));
        myNickname.getChildren().add(nickname);
        myFoodLabel.setText(String.valueOf(me.getFood()));
        myPPLabel.setText(String.valueOf(me.getPrestigePoints()));
        updateStackedContainer(myTribeContainer, me.getTribe(), tribeKnownTypes);
        updateCardContainer(myBuildingsContainer, me.getBuildings(), knownBuildingIds);
    }

    private void updateCardContainer(HBox container, List<Card> cards, Set<String> knownIds) {
        for (Card card : cards) {
            //.add return true if the element wasn't present
            if (knownIds.add(card.getCardId())) {
                StackPane node = buildCardNode(card, null, null, null);
                container.getChildren().add(node);
                animateCardEntry(node);
            }
        }
    }

    private void updateStackedContainer(HBox container, List<Card> cards, Set<String> knownTypes) {
        Map<String, List<Card>> grouped = new LinkedHashMap<>();
        for (Card card : cards) {
            grouped.computeIfAbsent(card.getClass().getSimpleName(), k -> new ArrayList<>()).add(card);
        }
        container.getChildren().clear();
        container.setPadding(new Insets(0, 14, 0, 0));
        for (Map.Entry<String, List<Card>> entry : grouped.entrySet()) {
            Node stackNode = buildCardStackNode(entry.getKey(), entry.getValue());
            container.getChildren().add(stackNode);
            if (knownTypes.add(entry.getKey())) {
                animateCardEntry(stackNode);
            }
        }
    }

    private Node buildCardStackNode(String typeName, List<Card> cards) {
        int n = cards.size();
        int visualLayers = Math.min(n, 3); //max 3 visible cards
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);

        //take the most recent cards
        List<Card> visible = cards.subList(cards.size() - visualLayers, cards.size());
        for (int i = 0; i < visualLayers; i++) {
            int level = visualLayers - 1 - i; // top = 0, middle = 1, back = 2
            ImageView iv = new ImageView(loadCardImage(visible.get(i).getCardId()));
            iv.setFitWidth(cardWidth);
            iv.setFitHeight(cardHeight);
            iv.setTranslateX(level * 7.0);
            iv.setTranslateY(level * -3.0);

            DropShadow shadow = new DropShadow();
            shadow.setRadius(6);
            shadow.setOffsetX(2);
            shadow.setOffsetY(2);
            shadow.setColor(Color.rgb(0, 0, 0, 0.4));
            iv.setEffect(shadow);

            stackPane.getChildren().add(iv);
        }

        if (n > 1) {
            Label badge = new Label(String.valueOf(n));
            badge.setStyle("-fx-background-color: rgba(40,40,40,0.85); -fx-text-fill: white; -fx-padding: 2 5 2 5; -fx-background-radius: 8;");
            badge.setFont(Font.font("Inknut Antiqua Regular", 11));
            StackPane.setAlignment(badge, Pos.TOP_LEFT);
            StackPane.setMargin(badge, new Insets(4, 4, 0, 0));
            stackPane.getChildren().add(badge);
        }

        VBox wrapper = new VBox(4);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.getChildren().add(stackPane);
        wrapper.setStyle("-fx-cursor: hand;");

        List<Card> cardsCopy = new ArrayList<>(cards);
        wrapper.setOnMouseClicked(e -> showCardTypeViewer(typeName, cardsCopy));

        return wrapper;
    }

    private void animateCardEntry(Node node) {
        node.setOpacity(0);
        node.setScaleX(0.5);
        node.setScaleY(0.5);

        FadeTransition fade = new FadeTransition(Duration.millis(400), node);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(400), node);
        scale.setFromX(0.5);
        scale.setFromY(0.5);
        scale.setToX(1.0);
        scale.setToY(1.0);

        new ParallelTransition(fade, scale).play();
    }

    private void showCardTypeViewer(String typeName, List<Card> cards) {
        cardTypeLabel.setText(typeName + " (" + cards.size() + ")");
        cardTypeCardsContainer.getChildren().clear();
        for (Card card : cards) {
            cardTypeCardsContainer.getChildren().add(buildCardNode(card, null, null, null));
        }
        cardTypeOverlay.setVisible(true);
    }

    @FXML
    private void closeCardTypeViewer() {
        cardTypeOverlay.setVisible(false);
    }

    private StackPane buildCardNode(Card card, BoardRows row, RoundPhasesEnum phase, LocalPlayerState acting) {
        Image img = loadCardImage(card.getCardId());
        ImageView iv = new ImageView(img);
        iv.setFitWidth(cardWidth);
        iv.setFitHeight(cardHeight);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setColor(Color.rgb(0, 0, 0, 0.5));

        DropShadow shadowHover = new DropShadow();
        shadowHover.setRadius(20);
        shadowHover.setOffsetX(6);
        shadowHover.setOffsetY(6);
        shadowHover.setColor(Color.rgb(0, 0, 0, 0.7));

        iv.setEffect(shadow);

        StackPane pane = new StackPane(iv);

        boolean isMyTurn = acting != null && acting.getNickname().equals(controller.getLocalPlayerUsername());
        boolean isClickable = false;
        //if the lane exists and it's my turn
        if (row != null && isMyTurn) {
            //if the card can be picked
            if (card.canBePicked()) {
                if (phase == RoundPhasesEnum.BONUS_DRAWING_PHASE) {
                    isClickable = row == BoardRows.UPPER &&
                            pendingDraws.getOrDefault(row, 0) == 0
                            && canAffordCard(card);
                } else if (phase == RoundPhasesEnum.ACTION_PHASE) {
                    LocalOfferCard myOffer = localGameState.getBoard().getOfferTrack().stream()
                            .filter(o -> !o.isFree() && o.getPlayer().equals(acting.getNickname()))
                            .findFirst().orElse(null);
                    if (myOffer != null) {
                        int usedUpper = pendingDraws.getOrDefault(BoardRows.UPPER, 0); //cards already drawn from up
                        int usedLower = pendingDraws.getOrDefault(BoardRows.LOWER, 0); //cards already drawn from down
                        //true if I still have to draw from above/below and I can afford it
                        isClickable = ((row == BoardRows.UPPER && myOffer.getDrawFromUpper() - usedUpper > 0)
                                   || (row == BoardRows.LOWER && myOffer.getDrawFromUnder() - usedLower > 0))
                                   && canAffordCard(card);
                    }
                }
            }
        }

        if (isClickable) {

            pane.setOnMouseEntered(e -> {
                ScaleTransition scale = new ScaleTransition(Duration.millis(150), pane);
                scale.setToX(1.08);
                scale.setToY(1.08);

                TranslateTransition move = new TranslateTransition(Duration.millis(150), pane);
                move.setToY(-4);

                iv.setEffect(shadowHover);

                new ParallelTransition(scale, move).play();
            });

            pane.setOnMouseExited(e -> {
                ScaleTransition scale = new ScaleTransition(Duration.millis(150), pane);
                scale.setToX(1.0);
                scale.setToY(1.0);

                TranslateTransition move = new TranslateTransition(Duration.millis(150), pane);
                move.setToY(0);

                iv.setEffect(shadow);

                new ParallelTransition(scale, move).play();
            });
            pane.setStyle("-fx-cursor: hand;");
            pane.setOnMouseClicked(e -> onCardClicked(card, row, pane));
        }
        return pane;
    }

    private Image loadCardImage(String cardId) {
        Image img = loadImage(PathConstants.CARDS_PATH + cardId + ".png");
        if (img == null) img = loadImage(PathConstants.CARDS_PATH + "card_back_1.png");
        return img;
    }

    //method to calculate if the player can afford to buy a building
    private boolean canAffordCard(Card card) {
        LocalPlayerState me = localGameState.getPlayers().stream()
                .filter(p -> p.getNickname().equals(controller.getLocalPlayerUsername()))
                .findFirst().orElse(null);
        if (me == null) return false;
        int discount = me.getTribe().stream()
                .mapToInt(Card::getBuildingDiscount)
                .sum();
        return card.canAffordWithFood(me.getFood(), discount);
    }

    //method to build the single node of the offer card
    private StackPane buildOfferTileNode(LocalOfferCard offer, RoundPhasesEnum phase, LocalPlayerState acting) {
        Image img = loadImage(PathConstants.TRACK_PATH + offer.getOfferCardId() + ".png");
        ImageView tileIv = new ImageView(img);
        tileIv.setFitWidth(cardWidth);
        tileIv.setFitHeight(cardHeight);

        StackPane pane = new StackPane(tileIv);
        double slotHeight = cardHeight / 5;
        double h = cardHeight / 2;
        double hTotem = screenHeight * 28 / 1080;

        boolean isFree = offer.isFree();
        if (!isFree) {
            localGameState.getPlayers().stream()
                    .filter(p -> p.getNickname().equals(offer.getPlayer()))
                    .findFirst()
                    .ifPresent(p -> {
                        Image totemImg = loadImage(PathConstants.TOTEM_PATH + p.getColor().name().toLowerCase() + ".png");
                        if (totemImg != null) {
                            ImageView totemIv = new ImageView(totemImg);
                            totemIv.setFitHeight(hTotem);
                            totemIv.setPreserveRatio(true);
                            StackPane.setMargin(totemIv, new Insets(h-slotHeight-hTotem, 0, 0, 0));
                            StackPane.setAlignment(totemIv, javafx.geometry.Pos.TOP_CENTER);
                            pane.getChildren().add(totemIv);
                        }
                    });
        }

        boolean isMyTurn = acting != null && acting.getNickname().equals(controller.getLocalPlayerUsername());
        if (isMyTurn && phase == RoundPhasesEnum.TOTEM_PLACING && isFree) {
            pane.setStyle("-fx-cursor: hand;");
            pane.setOnMouseClicked(e -> onOfferTileClicked(offer, pane));
        }

        return pane;
    }

    private void onCardClicked(Card card, BoardRows row, StackPane cardNode) {
        cardNode.setOnMouseClicked(null);
        cardNode.setOnMouseEntered(null);
        cardNode.setOnMouseExited(null);

        pendingDraws.merge(row, 1, Integer::sum);

        // Se questo row è ora esaurito, disabilita immediatamente le carte sorelle
        if (pendingDraws.getOrDefault(row, 0) >= allowedDrawsFromRow(row)) {
            HBox sameRow = (row == BoardRows.UPPER) ? upperRowContainer : lowerRowContainer;
            for (Node child : sameRow.getChildren()) {
                if (child != cardNode) {
                    child.setOnMouseClicked(null);
                    child.setOnMouseEntered(null);
                    child.setOnMouseExited(null);
                    child.setStyle("");
                }
            }
        }

        RoundPhasesEnum phase = localGameState.getCurrentRoundPhase();
        LocalPlayerState acting = localGameState.getPlayerActing();
        if (row == BoardRows.UPPER) refreshLowerRow(phase, acting);
        else refreshUpperRow(phase, acting);

        FadeTransition fade = new FadeTransition(Duration.millis(300), cardNode);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(300), cardNode);
        scale.setToX(0.5);
        scale.setToY(0.5);

        ParallelTransition exit = new ParallelTransition(fade, scale);
        exit.setOnFinished(e -> {
            new Thread(() -> {
                try {
                    controller.requestDraw(card.getCardId(), row);
                } catch (Exception ex) {
                    System.err.println("Draw request failed: " + ex.getMessage());
                    Platform.runLater(() -> {
                        pendingDraws.merge(row, -1, Integer::sum);
                        RoundPhasesEnum p = localGameState.getCurrentRoundPhase();
                        LocalPlayerState a = localGameState.getPlayerActing();
                        refreshUI(p, a, localGameState);
                    });
                }
            }).start();
        });
        exit.play();
    }

    private int allowedDrawsFromRow(BoardRows row) {
        RoundPhasesEnum phase = localGameState.getCurrentRoundPhase();
        String myNick = controller.getLocalPlayerUsername();
        LocalPlayerState acting = localGameState.getPlayerActing();
        if (acting == null) return 0;
        boolean hasBonusDraw = acting.hasBonusDraw();
        if (phase == RoundPhasesEnum.BONUS_DRAWING_PHASE && row == BoardRows.UPPER && hasBonusDraw) {
            return 1;
        }
        if (phase == RoundPhasesEnum.ACTION_PHASE) {
            return localGameState.getBoard().getOfferTrack().stream()
                    .filter(o -> !o.isFree() && o.getPlayer().equals(myNick))
                    .findFirst()
                    .map(o -> (row == BoardRows.UPPER) ? o.getDrawFromUpper() : o.getDrawFromUnder())
                    .orElse(0);
        }
        return 0;
    }

    private void onOfferTileClicked(LocalOfferCard offer, StackPane pane) {
        pane.setOnMouseClicked(null);
        new Thread(() -> {
            try {
                controller.requestTotemPlacement(offer.getOfferCardId());
            } catch (Exception e) {
                System.err.println("Totem request failed: " + e.getMessage());
            }
        }).start();
    }

    private StackPane buildTurnOrderTile(LocalGameState game) {
        Image img = loadImage(PathConstants.TRACK_PATH + "turn_" + game.getPlayers().size() + ".png");
        ImageView tileIv = new ImageView(img);
        tileIv.setFitWidth(cardWidth);
        tileIv.setFitHeight(cardHeight);
        tileIv.setPreserveRatio(false);

        StackPane pane = new StackPane(tileIv);

        List<LocalPlayerState> turnOrder = localGameState.getTurnOrder();
        double slotHeight = cardHeight / 5;
        double hTotem = screenHeight * 28 / 1080;

        for (int i = 0; i < turnOrder.size(); i++) {
            LocalPlayerState player = turnOrder.get(i);
            if (player == null) continue;
            Image totemImg = loadImage(PathConstants.TOTEM_PATH + player.getColor().name().toLowerCase() + ".png");
            if (totemImg != null) {
                ImageView totemIv = new ImageView(totemImg);
                totemIv.setFitHeight(hTotem);
                totemIv.setPreserveRatio(true);
                if (game.getPlayers().size() == 5)
                {
                    StackPane.setMargin(totemIv, new Insets(i * slotHeight, 0, 0, 0));
                }
                else if (game.getPlayers().size() == 4)
                {
                    StackPane.setMargin(totemIv, new Insets((i+1) * slotHeight-hTotem, 0, 0, 0));
                }
                else if (game.getPlayers().size() == 3)
                {
                    double h = cardHeight / 4;
                    StackPane.setMargin(totemIv, new Insets(i * slotHeight + h -hTotem, 0, 0, 0));
                }
                else if (game.getPlayers().size() == 2)
                {
                    double h = cardHeight / 2;
                    if (i == 0) StackPane.setMargin(totemIv, new Insets(h-slotHeight-hTotem, 0, 0, 0));
                    else StackPane.setMargin(totemIv, new Insets(h-hTotem, 0, 0, 0));
                }
                StackPane.setAlignment(totemIv, javafx.geometry.Pos.TOP_CENTER);
                pane.getChildren().add(totemIv);
            }
        }

        return pane;
    }

    //method to build the single node of a player shown in the panel on the right
    private VBox buildPlayerCard(LocalPlayerState player, LocalPlayerState acting) {
        boolean isActing = acting != null && player.getNickname().equals(acting.getNickname());

        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setMaxWidth(Double.MAX_VALUE);

        Image totemImg = loadImage(PathConstants.TOTEM_PATH + player.getColor().name().toLowerCase() + ".png");
        if (totemImg != null) {
            ImageView totemIv = new ImageView(totemImg);
            totemIv.setFitWidth(screenWidth * ((double) 35 / 1920));
            totemIv.setPreserveRatio(true);
            row.getChildren().add(totemIv);
        }

        Label nameLabel = new Label(player.getNickname());
        nameLabel.setFont(Font.font("Inknut Antiqua Regular", 16));
        nameLabel.setMaxWidth(screenWidth * 0.12); //old 0.08
        nameLabel.setTextOverrun(javafx.scene.control.OverrunStyle.ELLIPSIS);
        if (isActing) nameLabel.setStyle("-fx-font-weight: bold");
        row.getChildren().add(nameLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        row.getChildren().add(spacer);

        HBox foodGroup = new HBox(10);
        foodGroup.setAlignment(Pos.CENTER_LEFT);
        foodGroup.setPrefWidth(100);
        Image foodImg = loadImage(PathConstants.ASSETS_PATH + "food.png");
        if (foodImg != null) {
            ImageView foodIv = new ImageView(foodImg);
            foodIv.setFitWidth(screenWidth * ((double) 28 / 1920));
            foodIv.setFitHeight(screenHeight * ((double) 25 / 1080));
            foodGroup.getChildren().add(foodIv);
        }
        Label foodLabel = new Label(String.valueOf(player.getFood()));
        foodLabel.setFont(Font.font("Inknut Antiqua Regular", 16));
        foodLabel.setMinWidth(screenWidth * ((double) 35 / 1920));
        //foodLabel.setAlignment(Pos.CENTER);
        if (isActing) foodLabel.setStyle("-fx-font-weight: bold");
        foodGroup.getChildren().add(foodLabel);

        HBox ppGroup = new HBox(10);
        ppGroup.setAlignment(Pos.CENTER_LEFT);
        Image ppImg = loadImage(PathConstants.ASSETS_PATH + "pp.png");
        if (ppImg != null) {
            ImageView ppIv = new ImageView(ppImg);
            ppIv.setFitWidth(screenWidth * ((double) 28 / 1920));
            ppIv.setFitHeight(screenHeight * ((double) 25 / 1080));
            ppGroup.getChildren().add(ppIv);
        }
        Label ppLabel = new Label(String.valueOf(player.getPrestigePoints()));
        ppLabel.setFont(Font.font("Inknut Antiqua Regular", 16));
        ppLabel.setMinWidth(screenWidth * ((double) 35 / 1920));
        //ppLabel.setAlignment(Pos.CENTER);
        if (isActing) ppLabel.setStyle("-fx-font-weight: bold");
        ppGroup.getChildren().add(ppLabel);

        HBox statsGroup = new HBox();
        //HBox.setMargin(ppGroup, new Insets(-10, 0, 0, 0));
        Region space3 = new Region();
        HBox.setHgrow(space3, Priority.ALWAYS);
        statsGroup.getChildren().addAll(foodGroup, space3, ppGroup);
        row.getChildren().add(statsGroup);

        VBox card = new VBox(6);
        card.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(card, new Insets(0, 20, 20, 20));
        card.getChildren().add(row);
        card.setOnMouseClicked(e -> showTribeViewer(player));

        if (isActing) {
            card.setStyle("-fx-cursor: hand; -fx-padding: 12; -fx-border-color: black; -fx-border-width: 2; -fx-background-color: rgba(235,220,185,1); ");
            DropShadow glow = new DropShadow();
            glow.setColor(Color.BLACK);
            glow.setRadius(15);
            card.setEffect(glow);
        } else {
            card.setStyle("-fx-cursor: hand; -fx-padding: 12; -fx-border-color: transparent; -fx-border-width: 2;");
        }

        return card;
    }

    private void showTribeViewer(LocalPlayerState player) {
        ownerName.setText(player.getNickname());

        tribePlayerContainer.getChildren().clear();
        for (Card card : player.getTribe()) {
            tribePlayerContainer.getChildren().add(buildCardNode(card, null, null, null));
        }

        buildingsPlayerContainer.getChildren().clear();
        for (Card card : player.getBuildings()) {
            buildingsPlayerContainer.getChildren().add(buildCardNode(card, null, null, null));
        }

        deckOverlay.setVisible(true);
        deckOverlay.toFront();
    }

    @FXML
    private void closeTribeViewer() {
        deckOverlay.setVisible(false);
    }

    private void showPrompt (RoundPhasesEnum phase, LocalPlayerState acting) {
        if (acting == null) return;
        if (phase == lastShownPhase && acting.getNickname().equals(lastShownActing)) {
            boolean promptWasRemoved = phase == RoundPhasesEnum.ACTION_PHASE && promptLabel == null;
            if (!promptWasRemoved) return;
        }
        rootStackPane.getChildren().remove(promptLabel);

        lastShownPhase = phase;
        lastShownActing = acting.getNickname();

        String text = null;
        boolean isMyTurn = acting.getNickname().equals(controller.getLocalPlayerUsername());
        if (isMyTurn)
        {
            promptLabel = new Label();
            if (phase == RoundPhasesEnum.TOTEM_PLACING)
            {
                text = "Place your totem on the Offer Track";
            }
            else if (phase == RoundPhasesEnum.ACTION_PHASE)
            {
                LocalOfferCard card = localGameState.getBoard().getOfferTrack().stream()
                        .filter(offer -> !offer.isFree() && offer.getPlayer().equals(acting.getNickname()))
                        .findFirst()
                        .orElse(null);
                if (card != null)
                {
                    int cardsFromUp = card.getDrawFromUpper();
                    int cardsFromDown = card.getDrawFromUnder();
                    if (cardsFromDown > 0 && cardsFromUp > 0)
                    {
                        text = "Draw " + cardsFromUp + " cards from the Upper Line and " + cardsFromDown + " cards from the Under Line";
                    }
                    else if (cardsFromDown > 0 && cardsFromUp == 0)
                    {
                        text = "Draw " + cardsFromDown + " cards from the Under Line";
                    }
                    else if (cardsFromDown == 0 && cardsFromUp > 0)
                    {
                        text = "Draw " + cardsFromUp + " cards from the Upper Line";
                    }
                }
            }
            else if (phase == RoundPhasesEnum.BONUS_DRAWING_PHASE)
            {
                text = "Draw a card from the Upper Line";
            }

            if (text == null) {
                promptLabel = null;
                return;
            }
            promptLabel.setText(text);
            promptLabel.setFont(Font.font("Inknut Antiqua Regular", 20));
            promptLabel.setStyle("-fx-background-color: rgba(255,243,211,1); -fx-border-color: black; -fx-padding: 15;");
            StackPane.setAlignment(promptLabel, javafx.geometry.Pos.BOTTOM_CENTER);
            StackPane.setMargin(promptLabel, new Insets(0, 0, screenHeight*((double) 25 /108), 0));
            promptLabel.setOpacity(0.0);
            rootStackPane.getChildren().add(promptLabel);
            if (currentEventVbox != null) currentEventVbox.toFront();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), promptLabel);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }
        else {
            if (promptLabel != null) removePromptWithFade(); //rootStackPane.getChildren().remove(promptLabel);
            promptLabel = null;
        }
    }

    private void removePromptWithFade() {
        if (promptLabel == null) return;
        Label toRemove = promptLabel;
        promptLabel = null;
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toRemove);
        fadeOut.setFromValue(toRemove.getOpacity());
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> rootStackPane.getChildren().remove(toRemove));
        fadeOut.play();
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
        rulesOverlay.toFront();
    }

    @FXML private void showSummaryCards() {
        rulesOverlay.getChildren().clear();
        Region darkBg = new Region();
        darkBg.setStyle("-fx-background-color: rgba(0,0,0,0.6);");
        darkBg.setMaxWidth(Double.MAX_VALUE);
        darkBg.setMaxHeight(Double.MAX_VALUE);
        darkBg.setOnMouseClicked(e -> closeRules());

        HBox summaryCards = new HBox(20);
        summaryCards.setAlignment(Pos.CENTER);
        summaryCards.setMaxWidth(Region.USE_PREF_SIZE);
        summaryCards.setMaxHeight(Region.USE_PREF_SIZE);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setColor(Color.rgb(0, 0, 0, 0.5));

        Image img1 = loadImage(PathConstants.RULES_PATH + "summary_1.png");
        ImageView summaryIv1 = new ImageView(img1);
        summaryIv1.setFitWidth(cardWidth*3);
        summaryIv1.setFitHeight(cardHeight*3);
        summaryIv1.setEffect(shadow);
        summaryCards.getChildren().add(summaryIv1);

        Image img2 = loadImage(PathConstants.RULES_PATH + "summary_2.png");
        ImageView summaryIv2 = new ImageView(img2);
        summaryIv2.setFitWidth(cardWidth*3);
        summaryIv2.setFitHeight(cardHeight*3);
        summaryIv2.setEffect(shadow);
        summaryCards.getChildren().add(summaryIv2);

        rulesOverlay.getChildren().addAll(darkBg, summaryCards);

        rulesOverlay.setVisible(true);
        rulesOverlay.toFront();
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

    /**
     * Handles a {@link GameEventResolveEvent} by adding the event to the pending events and showing the next event.
     *
     * @param event the game event resolve event
     */
    @Subscribe
    public void onGameEventResolved(GameEventResolveEvent event) {
        Platform.runLater(() -> {
            pendingEvents.add(event.getCard());
            if (!showingEvent && localGameState.getRoundNumber() != 10) showNextEvent();
        });
    }

    private void showNextEvent() {
        if (pendingEvents.isEmpty())
        {
            showingEvent = false;
            return;
        }
        showingEvent = true;
        Card card = pendingEvents.removeFirst();

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        String id = card.getCardId();

        Label title = new Label("Event resolved:");
        title.setFont(Font.font("Inknut Antiqua Regular", 32));

        Image img = loadCardImage(id);
        ImageView iv = new ImageView(img);
        iv.setFitWidth(cardWidth);
        iv.setFitHeight(cardHeight);
        DropShadow shadowCard = new DropShadow();
        shadowCard.setRadius(10);
        shadowCard.setOffsetX(3);
        shadowCard.setOffsetY(3);
        shadowCard.setColor(Color.rgb(0, 0, 0, 0.5));
        iv.setEffect(shadowCard);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(3);
        shadow.setOffsetY(3);
        shadow.setColor(Color.rgb(0, 0, 0, 0.5));
        vbox.setEffect(shadow);
        vbox.setStyle("-fx-background-color: rgba(255,243,211,1); -fx-border-color: black; -fx-padding: 30");
        vbox.getChildren().addAll(title, iv);

        vbox.setMaxWidth(Region.USE_PREF_SIZE);
        vbox.setMaxHeight(Region.USE_PREF_SIZE);
        StackPane.setAlignment(vbox, javafx.geometry.Pos.BOTTOM_CENTER);
        StackPane.setMargin(vbox, new Insets(0, 0, screenHeight*((double) 25 /108), 0));
        vbox.setOpacity(0.0);
        rootStackPane.getChildren().add(vbox);
        currentEventVbox = vbox;
        vbox.toFront();

        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), vbox);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(400), vbox);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(f -> {
            rootStackPane.getChildren().remove(vbox);
            currentEventVbox = null;
            showNextEvent();
        });

        fadeIn.setOnFinished(e -> pause.play());
        pause.setOnFinished(e -> fadeOut.play());
        fadeIn.play();
    }

    @FXML
    private void showPlayers () {
        playerOverlay.setVisible(true);
        playerOverlay.toFront();
    }

    @FXML
    private void closeShowPlayers() {
        playerOverlay.setVisible(false);
    }
}
