package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.TotemNetworkRequest;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalOfferCard;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.BoardUpdateEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameEndedEvent;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GameController extends BaseController {
    @FXML private StackPane rootStackPane;
    @FXML private Label roundLabel;
    @FXML private Label eraLabel;
    @FXML private Label turnLabel;
    @FXML private Label phaseLabel;

    @FXML private ImageView deckImage;

    @FXML private HBox upperRowContainer;
    @FXML private HBox offerTrackContainer;
    @FXML private HBox lowerRowContainer;

    @FXML private VBox playersContainer;

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
    @FXML private HBox tribePlayerContainer;
    @FXML private HBox buildingsPlayerContainer;

    @FXML private VBox rightPanel;
    @FXML private VBox leftSpacer;

    private static final String CARDS_PATH = "/it/polimi/ingsw/am31/am31/view/gui/assets/cards/";
    private static final String ASSETS_PATH = "/it/polimi/ingsw/am31/am31/view/gui/assets/";
    private static final String TRACK_PATH = "/it/polimi/ingsw/am31/am31/view/gui/assets/Track/";
    private static final String TOTEM_PATH = "/it/polimi/ingsw/am31/am31/view/gui/assets/totem/";

    private double cardWidth = 120;
    private double cardHeight = 170;

    private RoundPhasesEnum lastShownPhase = null;
    private String lastShownActing = null;

    private Label promptLabel = null;

    private Set<String> previousTribeIds = new HashSet<>();
    private Set<String> previousBuildingIds = new HashSet<>();

    private final AtomicBoolean uiRefreshPending = new AtomicBoolean(false);

    @FXML
    public void initialize() {
        Image foodImg = loadImage(ASSETS_PATH + "food.png");
        Image ppImg = loadImage(ASSETS_PATH + "pp.png");
        if (foodImg != null) foodIcon.setImage(foodImg);
        if (ppImg != null) PPIcon.setImage(ppImg);

        leftSpacer.prefWidthProperty().bind(rightPanel.widthProperty());

        Platform.runLater(() -> {
            makeScrollPaneTransparent(tribeScrollPane);
            makeScrollPaneTransparent(buildingsScrollPane);
        });
    }

    private void makeScrollPaneTransparent(ScrollPane sp) {
        sp.getStyleClass().add("transparent-scroll");
        var viewport = sp.lookup(".viewport");
        if (viewport != null) viewport.setStyle("-fx-background-color: transparent;");
    }

    @Subscribe
    public void onBoardUpdate(BoardUpdateEvent event) {
        if (uiRefreshPending.compareAndSet(false, true)) {
            Platform.runLater(() -> {
                uiRefreshPending.set(false);
                RoundPhasesEnum phase = localGameState.getCurrentRoundPhase();
                LocalPlayerState acting = localGameState.getPlayerActing();
                refreshUI(phase, acting);
            });
        }
    }

    @Subscribe
    public void onGameEnded(GameEndedEvent event) {
        sceneManager.showEndGame();
    }

    @Override
    public void setLocalGameState(LocalGameState localGameState) {
        super.setLocalGameState(localGameState);
        Platform.runLater(() -> {
            RoundPhasesEnum phase = localGameState.getCurrentRoundPhase();
            LocalPlayerState acting = localGameState.getPlayerActing();
            refreshUI(phase, acting);
        });
    }

    private void refreshUI(RoundPhasesEnum phase, LocalPlayerState acting) {
        refreshDeck();
        refreshTopBar(phase, acting);
        refreshUpperRow(phase, acting);
        refreshOfferTrack(phase, acting);
        refreshLowerRow(phase, acting);
        refreshPlayerPanel();
        refreshMyInfo();
        showPrompt(phase, acting);
    }

    private void refreshDeck() {
        int era = localGameState.getEra();
        String backName = "card_back_" + era + ".png";
        Image back = loadImage(CARDS_PATH + backName);
        if (back != null) deckImage.setImage(back);
    }

    private Image loadImage(String path) {
        InputStream stream = getClass().getResourceAsStream(path);
        return stream != null ? new Image(stream) : null;
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
            default                  -> "";
        };
    }

    private void refreshUpperRow(RoundPhasesEnum phase, LocalPlayerState acting) {
        upperRowContainer.getChildren().clear();
        for (Card card : localGameState.getBoard().getUpperLine()) {
            upperRowContainer.getChildren().add(buildCardNode(card, BoardRows.UPPER, phase, acting));
        }
    }

    private void refreshOfferTrack(RoundPhasesEnum phase, LocalPlayerState acting) {
        offerTrackContainer.getChildren().clear();
        offerTrackContainer.getChildren().add(buildTurnOrderTile());
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

    private void refreshPlayerPanel() {
        playersContainer.getChildren().clear();
        String myNick = controller.getLocalPlayerUsername();
        for (LocalPlayerState player : localGameState.getPlayers()) {
            if (!player.getNickname().equals(myNick)) {
                playersContainer.getChildren().add(buildPlayerCard(player));
            }
        }
    }

    private void refreshMyInfo() {
        String myNick = controller.getLocalPlayerUsername();
        LocalPlayerState me = localGameState.getPlayers().stream()
                .filter(p -> p.getNickname().equals(myNick))
                .findFirst()
                .orElse(null);
        if (me == null) return;
        myFoodLabel.setText(String.valueOf(me.getFood()));
        myPPLabel.setText(String.valueOf(me.getPrestigePoints()));
        Set<String> newTribeIds = me.getTribe().stream()
                .map(Card::getCardId)
                .collect(Collectors.toSet());
        Set<String> addedTribeIds = new HashSet<>(newTribeIds);
        addedTribeIds.removeAll(previousTribeIds);
        previousTribeIds = newTribeIds;

        Set<String> newBuildingIds = me.getBuildings().stream()
                .map(Card::getCardId)
                .collect(Collectors.toSet());
        Set<String> addedBuildingIds = new HashSet<>(newBuildingIds);
        addedBuildingIds.removeAll(previousBuildingIds);
        previousBuildingIds = newBuildingIds;
        myTribeContainer.getChildren().clear();
        for (Card card : me.getTribe()) {
            StackPane node = buildCardNode(card, null, null, null);
            if (addedTribeIds.contains(card.getCardId())) {
                animateCardEntry(node);
            }
            myTribeContainer.getChildren().add(node);
        }

        myBuildingsContainer.getChildren().clear();
        for (Card card : me.getBuildings()) {
            StackPane node = buildCardNode(card, null, null, null);
            if (addedBuildingIds.contains(card.getCardId())) {
                animateCardEntry(node);
            }
            myBuildingsContainer.getChildren().add(node);
        }
    }

    private void animateCardEntry(StackPane node) {
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
        boolean isPickPhase = (phase == RoundPhasesEnum.ACTION_PHASE || phase == RoundPhasesEnum.BONUS_DRAWING_PHASE);

        if (row != null && isMyTurn && isPickPhase) {

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
        Image img = loadImage(CARDS_PATH + cardId + ".png");
        if (img == null) img = loadImage(CARDS_PATH + "card_back_1.png");
        return img;
    }

    private StackPane buildOfferTileNode(LocalOfferCard offer, RoundPhasesEnum phase, LocalPlayerState acting) {
        Image img = loadImage(TRACK_PATH + offer.getOfferCardId() + ".png");
        ImageView tileIv = new ImageView(img);
        tileIv.setFitWidth(cardWidth);
        tileIv.setFitHeight(cardHeight);

        StackPane pane = new StackPane(tileIv);

        boolean isFree = offer.getPlayer().equals("CARD_IS_EMPTY");
        if (!isFree) {
            localGameState.getPlayers().stream()
                    .filter(p -> p.getNickname().equals(offer.getPlayer()))
                    .findFirst()
                    .ifPresent(p -> {
                        Image totemImg = loadImage(TOTEM_PATH + p.getColor().name().toLowerCase() + ".png");
                        if (totemImg != null) {
                            ImageView totemIv = new ImageView(totemImg);
                            totemIv.setFitWidth(32);
                            totemIv.setFitHeight(32);
                            pane.getChildren().add(totemIv);
                        }
                    });
        }

        boolean isMyTurn = acting != null && acting.getNickname().equals(controller.getLocalPlayerUsername());
        if (isMyTurn && phase == RoundPhasesEnum.TOTEM_PLACING && isFree) {
            pane.setStyle("-fx-cursor: hand;");
            pane.setOnMouseClicked(e -> onOfferTileClicked(offer));
        }

        return pane;
    }

    private void onCardClicked(Card card, BoardRows row, StackPane cardNode) {
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
                    controller.sendRequest(new DrawNetworkRequest(card.getCardId(), row));
                } catch (Exception ex) {
                    System.err.println("Draw request failed: " + ex.getMessage());
                }
            }).start();
        });
        exit.play();
    }

    private void onOfferTileClicked(LocalOfferCard offer) {
        new Thread(() -> {
            try {
                controller.sendRequest(new TotemNetworkRequest(offer.getOfferCardId()));
            } catch (Exception e) {
                System.err.println("Totem request failed: " + e.getMessage());
            }
        }).start();
    }

    private StackPane buildTurnOrderTile() {
        Image img = loadImage(TRACK_PATH + "turn.png");
        ImageView tileIv = new ImageView(img);
        tileIv.setFitWidth(cardWidth);
        tileIv.setFitHeight(cardHeight);
        tileIv.setPreserveRatio(true);

        StackPane pane = new StackPane(tileIv);

        List<LocalPlayerState> turnOrder = localGameState.getTurnorder();
        double slotHeight = cardHeight / 5;

        for (int i = 0; i < turnOrder.size(); i++) {
            LocalPlayerState player = turnOrder.get(i);
            if (player == null) continue;
            Image totemImg = loadImage(TOTEM_PATH + player.getColor().name().toLowerCase() + ".png");
            if (totemImg != null) {
                ImageView totemIv = new ImageView(totemImg);
                totemIv.setFitWidth(20);
                totemIv.setFitHeight(20);
                StackPane.setMargin(totemIv, new Insets(i * slotHeight, 0, 0, 0));
                StackPane.setAlignment(totemIv, javafx.geometry.Pos.TOP_CENTER);
                pane.getChildren().add(totemIv);
            }
        }

        return pane;
    }

    private VBox buildPlayerCard(LocalPlayerState player) {
        boolean isActing = player.getNickname().equals(localGameState.getPlayerActing().getNickname());
        HBox nameRow = new HBox(10);
        nameRow.setAlignment(Pos.CENTER);

        Image totemImg = loadImage(TOTEM_PATH + player.getColor().name().toLowerCase() + ".png");
        if (totemImg != null) {
            ImageView totemIv = new ImageView(totemImg);
            totemIv.setFitWidth(35);
            totemIv.setPreserveRatio(true);
            nameRow.getChildren().add(totemIv);
        }
        Label nameLabel = new Label(player.getNickname());
        nameLabel.setFont(Font.font("Inknut Antiqua Regular", 16));
        if (isActing) nameLabel.setStyle("-fx-font-weight: bold");
        nameRow.getChildren().add(nameLabel);

        nameRow.setAlignment(Pos.CENTER);

        Image foodImg = loadImage(ASSETS_PATH + "food.png");
        if (foodImg != null) {
            ImageView foodIv = new ImageView(foodImg);
            foodIv.setFitWidth(36);
            foodIv.setFitHeight(32);
            nameRow.getChildren().add(foodIv);
        }
        Label foodLabel = new Label(String.valueOf(player.getFood()));
        foodLabel.setFont(Font.font("Inknut Antiqua Regular", 16));
        if (isActing) foodLabel.setStyle("-fx-font-weight: bold");
        nameRow.getChildren().add(foodLabel);

        Image ppImg = loadImage(ASSETS_PATH + "pp.png");
        if (ppImg != null) {
            ImageView ppIv = new ImageView(ppImg);
            ppIv.setFitWidth(36);
            ppIv.setFitHeight(32);
            nameRow.getChildren().add(ppIv);
        }
        Label ppLabel = new Label(String.valueOf(player.getPrestigePoints()));
        ppLabel.setFont(Font.font("Inknut Antiqua Regular", 16));
        if (isActing) ppLabel.setStyle("-fx-font-weight: bold");
        nameRow.getChildren().add(ppLabel);

        if (isActing)
        {
            nameRow.setStyle("-fx-cursor: hand; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 18; -fx-background-color: rgba(235,220,185,1); -fx-font-weight: bold");
            DropShadow glow = new DropShadow();
            glow.setColor(Color.BLACK);
            glow.setRadius(15);
            nameRow.setEffect(glow);
        }

        VBox card = new VBox(6);
        VBox.setMargin(card, new Insets(0, 20, 20, 20));
        card.getChildren().add(nameRow);
        card.setStyle("-fx-cursor: hand;");
        card.setOnMouseClicked(e -> showTribeViewer(player));

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
    }

    @FXML
    private void closeTribeViewer() {
        deckOverlay.setVisible(false);
    }

    private void showPrompt (RoundPhasesEnum phase, LocalPlayerState acting) {
        if (acting == null) return;
        if (phase == lastShownPhase && acting.getNickname().equals(lastShownActing)) return;
        //if (phase == lastShownPhase && !acting.getNickname().equals(lastShownActing)) rootStackPane.getChildren().remove((promptLabel));
        //if (phase != lastShownPhase && acting.getNickname().equals(lastShownActing)) rootStackPane.getChildren().remove(promptLabel);
        //if (phase != lastShownPhase && !acting.getNickname().equals(lastShownActing)) rootStackPane.getChildren().remove(promptLabel);
        if (!(phase == lastShownPhase && acting.getNickname().equals(lastShownActing))) removePromptWithFade();

        lastShownPhase = phase;
        lastShownActing = acting.getNickname();
        //Label label = new Label();
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
                        .filter(offer -> offer.getPlayer().equals(acting.getNickname()))
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
            promptLabel.setText(text);
            promptLabel.setFont(Font.font("Inknut Antiqua Regular", 20));
            promptLabel.setStyle("-fx-background-color: rgba(255,243,211,1); -fx-border-color: black; -fx-padding: 15;");
            StackPane.setAlignment(promptLabel, javafx.geometry.Pos.BOTTOM_CENTER);
            StackPane.setMargin(promptLabel, new Insets(0, 0, 250, 0));
            rootStackPane.getChildren().add(promptLabel);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), promptLabel);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }
        else {
            if (promptLabel != null) rootStackPane.getChildren().remove(promptLabel);
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
}
