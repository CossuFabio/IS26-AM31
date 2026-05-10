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
import it.polimi.ingsw.am31.am31.view.gui.PathConstants;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

    @FXML private VBox rightPanel;
    @FXML private VBox leftSpacer;

    @FXML private StackPane cardTypeOverlay;
    @FXML private Label cardTypeLabel;
    @FXML private ScrollPane cardTypeScrollPane;
    @FXML private HBox cardTypeCardsContainer;


    private double cardWidth = 120;
    private double cardHeight = 170;

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

    @FXML
    public void initialize() {
        Image foodImg = loadImage(PathConstants.ASSETS_PATH + "food.png");
        Image ppImg = loadImage(PathConstants.ASSETS_PATH + "pp.png");
        if (foodImg != null) foodIcon.setImage(foodImg);
        if (ppImg != null) PPIcon.setImage(ppImg);

        //bind the width of the leftSpacer to the right one (the left will be empty)
        leftSpacer.prefWidthProperty().bind(rightPanel.widthProperty());

        //make all the ScrollPane transparent
        Platform.runLater(() -> {
            makeScrollPaneTransparent(tribeScrollPane);
            makeScrollPaneTransparent(buildingsScrollPane);
            makeScrollPaneTransparent(cardTypeScrollPane);
            makeScrollPaneTransparent(tribePlayerScrollPane);
            makeScrollPaneTransparent(buildingsPlayerScrollPane);
        });
    }

    //method to make a ScrollPane transparent (normally doesn't work  due to viewport)
    private void makeScrollPaneTransparent(ScrollPane sp) {
        sp.getStyleClass().add("transparent-scroll");
        var viewport = sp.lookup(".viewport");
        if (viewport != null) viewport.setStyle("-fx-background-color: transparent;");
    }

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
            refreshUI(phase, acting, localGameState);
        });
    }

    //method to refresh the UI calling every method to refresh each part of the screen
    private void refreshUI(RoundPhasesEnum phase, LocalPlayerState acting, LocalGameState gameState) {
        String myNick = controller.getLocalPlayerUsername();
        boolean isMyDrawTurn = acting != null && acting.getNickname().equals(myNick)
                && (phase == RoundPhasesEnum.ACTION_PHASE || phase == RoundPhasesEnum.BONUS_DRAWING_PHASE);
        if (!isMyDrawTurn) pendingDraws.clear();
        refreshDeck();
        refreshTopBar(phase, acting);
        refreshUpperRow(phase, acting);
        refreshOfferTrack(phase, acting, gameState);
        refreshLowerRow(phase, acting);
        refreshPlayerPanel(acting);
        refreshMyInfo();
        showPrompt(phase, acting);
    }

    //method to refresh the deck
    private void refreshDeck() {
        int era = localGameState.getEra();
        String backName = "card_back_" + era + ".png";
        Image back = loadImage(PathConstants.CARDS_PATH + backName);
        if (back != null) deckImage.setImage(back);
        if (localGameState.getRoundNumber() == 10) deckImage.setVisible(false);
    }

    //method to load an image from a path
    private Image loadImage(String path) {
        if (imageCache.containsKey(path)) return imageCache.get(path);
        InputStream stream = getClass().getResourceAsStream(path);
        Image img = stream != null ? new Image(stream) : null;
        imageCache.put(path, img);
        return img;
    }

    //method to refresh the top bar
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

    //method to refresh the upper lane
    private void refreshUpperRow(RoundPhasesEnum phase, LocalPlayerState acting) {
        upperRowContainer.getChildren().clear();
        for (Card card : localGameState.getBoard().getUpperLine()) {
            upperRowContainer.getChildren().add(buildCardNode(card, BoardRows.UPPER, phase, acting));
        }
    }

    //method to refresh the offer track
    private void refreshOfferTrack(RoundPhasesEnum phase, LocalPlayerState acting, LocalGameState gameState) {
        offerTrackContainer.getChildren().clear();
        offerTrackContainer.getChildren().add(buildTurnOrderTile(gameState));
        for (LocalOfferCard offer : localGameState.getBoard().getOfferTrack()) {
            offerTrackContainer.getChildren().add(buildOfferTileNode(offer, phase, acting));
        }
    }

    //method to refresh the lower lane
    private void refreshLowerRow(RoundPhasesEnum phase, LocalPlayerState acting) {
        lowerRowContainer.getChildren().clear();
        for (Card card : localGameState.getBoard().getUnderLine()) {
            lowerRowContainer.getChildren().add(buildCardNode(card, BoardRows.LOWER, phase, acting));
        }
    }

    //method to refresh the player panel on the right
    private void refreshPlayerPanel(LocalPlayerState acting) {
        playersContainer.getChildren().clear();
        String myNick = controller.getLocalPlayerUsername();
        for (LocalPlayerState player : localGameState.getPlayers()) {
            if (!player.getNickname().equals(myNick)) {
                playersContainer.getChildren().add(buildPlayerCard(player, acting));
            }
        }
    }

    //method to refresh all my info (food, pp and deck)
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
            totemIv.setFitWidth(35);
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

    //method for constructing the single node of a set of character cards of the same type
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

    //method to build the single node of card
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
                    //true if I haven't drawn from above yet and can afford to draw
                    isClickable = (row == BoardRows.UPPER)
                            && pendingDraws.getOrDefault(BoardRows.UPPER, 0) == 0
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

        boolean isFree = offer.isFree();
        if (!isFree) {
            localGameState.getPlayers().stream()
                    .filter(p -> p.getNickname().equals(offer.getPlayer()))
                    .findFirst()
                    .ifPresent(p -> {
                        Image totemImg = loadImage(PathConstants.TOTEM_PATH + p.getColor().name().toLowerCase() + ".png");
                        if (totemImg != null) {
                            ImageView totemIv = new ImageView(totemImg);
                            totemIv.setFitHeight(32);
                            totemIv.setPreserveRatio(true);
                            StackPane.setMargin(totemIv, new Insets(h-slotHeight-32, 0, 0, 0));
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
                    controller.sendRequest(new DrawNetworkRequest(card.getCardId(), row));
                } catch (Exception ex) {
                    System.err.println("Draw request failed: " + ex.getMessage());
                }
            }).start();
        });
        exit.play();
    }

    private int allowedDrawsFromRow(BoardRows row) {
        RoundPhasesEnum phase = localGameState.getCurrentRoundPhase();
        if (phase == RoundPhasesEnum.BONUS_DRAWING_PHASE) {
            return (row == BoardRows.UPPER) ? 1 : 0;
        }
        if (phase == RoundPhasesEnum.ACTION_PHASE) {
            String myNick = controller.getLocalPlayerUsername();
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
                controller.sendRequest(new TotemNetworkRequest(offer.getOfferCardId()));
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
        tileIv.setPreserveRatio(true);

        StackPane pane = new StackPane(tileIv);

        List<LocalPlayerState> turnOrder = localGameState.getTurnOrder();
        double slotHeight = cardHeight / 5;

        for (int i = 0; i < turnOrder.size(); i++) {
            LocalPlayerState player = turnOrder.get(i);
            if (player == null) continue;
            Image totemImg = loadImage(PathConstants.TOTEM_PATH + player.getColor().name().toLowerCase() + ".png");
            if (totemImg != null) {
                ImageView totemIv = new ImageView(totemImg);
                totemIv.setFitHeight(32);
                totemIv.setPreserveRatio(true);
                if (game.getPlayers().size() == 5)
                {
                    StackPane.setMargin(totemIv, new Insets(i * slotHeight, 0, 0, 0));
                }
                else if (game.getPlayers().size() == 4)
                {
                    StackPane.setMargin(totemIv, new Insets((i+1) * slotHeight-32, 0, 0, 0));
                }
                else if (game.getPlayers().size() == 3)
                {
                    double h = cardHeight / 4;
                    StackPane.setMargin(totemIv, new Insets(i * slotHeight + h -32, 0, 0, 0));
                }
                else if (game.getPlayers().size() == 2)
                {
                    double h = cardHeight / 2;
                    if (i == 0) StackPane.setMargin(totemIv, new Insets(h-slotHeight-32, 0, 0, 0));
                    else StackPane.setMargin(totemIv, new Insets(h-32, 0, 0, 0));
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
            totemIv.setFitWidth(35);
            totemIv.setPreserveRatio(true);
            row.getChildren().add(totemIv);
        }

        Label nameLabel = new Label(player.getNickname());
        nameLabel.setFont(Font.font("Inknut Antiqua Regular", 16));
        if (isActing) nameLabel.setStyle("-fx-font-weight: bold");
        row.getChildren().add(nameLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        row.getChildren().add(spacer);

        HBox foodGroup = new HBox();
        foodGroup.setAlignment(Pos.CENTER);
        Image foodImg = loadImage(PathConstants.ASSETS_PATH + "food.png");
        if (foodImg != null) {
            ImageView foodIv = new ImageView(foodImg);
            foodIv.setFitWidth(28);
            foodIv.setFitHeight(25);
            foodGroup.getChildren().add(foodIv);
        }
        Label foodLabel = new Label(String.valueOf(player.getFood()));
        foodLabel.setFont(Font.font("Inknut Antiqua Regular", 16));
        foodLabel.setMinWidth(35);
        foodLabel.setAlignment(Pos.CENTER);
        if (isActing) foodLabel.setStyle("-fx-font-weight: bold");
        foodGroup.getChildren().add(foodLabel);

        HBox ppGroup = new HBox();
        ppGroup.setAlignment(Pos.CENTER);
        Image ppImg = loadImage(PathConstants.ASSETS_PATH + "pp.png");
        if (ppImg != null) {
            ImageView ppIv = new ImageView(ppImg);
            ppIv.setFitWidth(28);
            ppIv.setFitHeight(25);
            ppGroup.getChildren().add(ppIv);
        }
        Label ppLabel = new Label(String.valueOf(player.getPrestigePoints()));
        ppLabel.setFont(Font.font("Inknut Antiqua Regular", 16));
        ppLabel.setMinWidth(35);
        ppLabel.setAlignment(Pos.CENTER);
        if (isActing) ppLabel.setStyle("-fx-font-weight: bold");
        ppGroup.getChildren().add(ppLabel);

        HBox statsGroup = new HBox(12);
        statsGroup.setAlignment(Pos.CENTER);
        statsGroup.getChildren().addAll(foodGroup, ppGroup);
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
    }

    @FXML
    private void closeTribeViewer() {
        deckOverlay.setVisible(false);
    }

    private void showPrompt (RoundPhasesEnum phase, LocalPlayerState acting) {
        if (acting == null) return;
        if (phase == lastShownPhase && acting.getNickname().equals(lastShownActing)) {
            if (phase == RoundPhasesEnum.ACTION_PHASE && promptLabel == null) {
                // non fare return, lascia che mostri il prompt
            } else {
                return;
            }
        }
        rootStackPane.getChildren().remove(promptLabel);
        //if (!(phase == lastShownPhase && acting.getNickname().equals(lastShownActing))) removePromptWithFade();

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
