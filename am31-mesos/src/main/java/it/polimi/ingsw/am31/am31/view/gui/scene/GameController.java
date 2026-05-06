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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.InputStream;
import java.util.List;

public class GameController extends BaseController {
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
    @FXML private ImageView foodIcon;
    @FXML private ImageView PPIcon;

    @FXML private StackPane deckOverlay;
    @FXML private Label ownerName;
    @FXML private HBox tribePlayerContainer;
    @FXML private HBox buildingsPlayerContainer;

    private static final String CARDS_PATH = "/it/polimi/ingsw/am31/am31/view/gui/assets/cards/";
    private static final String ASSETS_PATH = "/it/polimi/ingsw/am31/am31/view/gui/assets/";
    private static final String TRACK_PATH = "/it/polimi/ingsw/am31/am31/view/gui/assets/Track/";
    private static final String TOTEM_PATH = "/it/polimi/ingsw/am31/am31/view/gui/assets/totem/";

    private static final double CARD_WIDTH = 150;
    private static final double CARD_HEIGHT = 211;

    @FXML
    public void initialize() {
        Image foodImg = loadImage(ASSETS_PATH + "food.png");
        Image ppImg = loadImage(ASSETS_PATH + "pp.png");
        if (foodImg != null) foodIcon.setImage(foodImg);
        if (ppImg != null) PPIcon.setImage(ppImg);
        deckImage.setFitWidth(CARD_WIDTH);
        deckImage.setFitHeight(CARD_HEIGHT);
    }

    private void refreshUI() {
        refreshDeck();
        refreshTopBar();
        refreshUpperRow();
        refreshOfferTrack();
        refreshLowerRow();
        refreshPlayerPanel();
        refreshMyInfo();
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

    private void refreshTopBar() {
        roundLabel.setText("Round: " + localGameState.getRoundNumber());
        eraLabel.setText("Era: " + localGameState.getEra());
        LocalPlayerState current = localGameState.getPlayerActing();
        boolean isMyTurn = current.getNickname().equals(controller.getLocalPlayerUsername());
        turnLabel.setText(isMyTurn ? "Your Turn" : "Turn: " + current.getNickname());
        phaseLabel.setText(phaseToString(localGameState.getCurrentRoundPhase()));
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

    private void refreshUpperRow() {
        upperRowContainer.getChildren().clear();
        for (Card card : localGameState.getBoard().getUpperLine()) {
            upperRowContainer.getChildren().add(buildCardNode(card, BoardRows.UPPER));
        }
    }

    private void refreshOfferTrack() {
        System.out.println("Offer track size: " + localGameState.getBoard().getOfferTrack().size());
        offerTrackContainer.getChildren().clear();
        offerTrackContainer.getChildren().add(buildTurnOrderTile());
        for (LocalOfferCard offer : localGameState.getBoard().getOfferTrack()) {
            offerTrackContainer.getChildren().add(buildOfferTileNode(offer));
        }
    }

    private void refreshLowerRow() {
        lowerRowContainer.getChildren().clear();
        for (Card card : localGameState.getBoard().getUnderLine()) {
            lowerRowContainer.getChildren().add(buildCardNode(card, BoardRows.LOWER));
        }
    }

    private void refreshPlayerPanel() {
        playersContainer.getChildren().clear();
        String myNick = controller.getLocalPlayerUsername();
        for (LocalPlayerState player : localGameState.getPlayers())
        {
            if (!player.getNickname().equals(myNick))
            {
                playersContainer.getChildren().add(buildPlayerCard(player));
            }
        }
    }

    private void refreshMyInfo() {
        String myNick = controller.getLocalPlayerUsername();
        System.out.println("refreshMyInfo for: " + myNick);
        LocalPlayerState me = localGameState.getPlayers().stream()
                .filter(p -> p.getNickname().equals(myNick))
                .findFirst()
                .orElse(null);
        if (me==null) {
            System.out.println("Player not found!");return;}
        System.out.println("Tribe size: " + me.getTribe().size());
        myFoodLabel.setText(String.valueOf(me.getFood()));
        myPPLabel.setText(String.valueOf(me.getPrestigePoints()));
        myTribeContainer.getChildren().clear();
        for (Card card : me.getTribe()) {
            myTribeContainer.getChildren().add(buildCardNode(card, null));
        }

        myBuildingsContainer.getChildren().clear();
        for (Card card : me.getBuildings()) {
            myBuildingsContainer.getChildren().add(buildCardNode(card, null));
        }
    }

    private StackPane buildCardNode (Card card, BoardRows row) {
        Image img = loadCardImage(card.getCardId());
        ImageView iv = new ImageView(img);
        iv.setFitWidth(CARD_WIDTH);
        iv.setFitHeight(CARD_HEIGHT);
        iv.setPreserveRatio(true);

        StackPane pane = new StackPane(iv);
        boolean isMyTurn = localGameState.getPlayerActing().getNickname()
                .equals(controller.getLocalPlayerUsername());
        RoundPhasesEnum phase = localGameState.getCurrentRoundPhase();
        boolean isPickPhase = (phase == RoundPhasesEnum.ACTION_PHASE || phase == RoundPhasesEnum.BONUS_DRAWING_PHASE);

        //if it's my turn and it's pick phase
        if (row != null && isMyTurn && isPickPhase) {
            pane.setStyle("-fx-cursor: hand;");
            pane.setOnMouseClicked(e -> onCardClicked(card, row));
        }

        return pane;
    }

    private Image loadCardImage(String cardId) {
        Image img = loadImage(CARDS_PATH + cardId + ".png");
        if (img == null) img = loadImage(CARDS_PATH + "card_back_1.png");
        return img;
    }

    private StackPane buildOfferTileNode (LocalOfferCard offer) {
        System.out.println("Loading offer: " + TRACK_PATH + offer.getOfferCardId() + ".png -> " + (loadImage(TRACK_PATH + offer.getOfferCardId() + ".png") == null ? "NULL" : "OK"));
        Image img = loadImage(TRACK_PATH + offer.getOfferCardId() + ".png");
        ImageView tileIv = new ImageView(img);
        tileIv.setFitWidth(CARD_WIDTH);
        tileIv.setFitHeight(CARD_HEIGHT);
        //tileIv.setPreserveRatio(true);

        StackPane pane = new StackPane(tileIv);

        boolean isFree = offer.getPlayer().equals("CARD_IS_EMPTY");
        if (!isFree)
        {
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

        boolean isMyTurn = localGameState.getPlayerActing().getNickname().equals(controller.getLocalPlayerUsername());
        if (isMyTurn && localGameState.getCurrentRoundPhase() == RoundPhasesEnum.TOTEM_PLACING && isFree)
        {
            pane.setStyle("-fx-cursor: hand;");
            pane.setOnMouseClicked(e -> onOfferTileClicked(offer));
        }

        return pane;
    }

    @Subscribe
    public void onBoardUpdate(BoardUpdateEvent event) {
        Platform.runLater(this::refreshUI);
    }

    @Subscribe
    public void onGameEnded(GameEndedEvent event) {
        sceneManager.showEndGame();
    }

    private void onCardClicked(Card card, BoardRows row) {
        try {
            controller.sendRequest(new DrawNetworkRequest(card.getCardId(), row));
        } catch (Exception e) {
            System.err.println("Draw request failed: " + e.getMessage());
        }
    }

    private void onOfferTileClicked(LocalOfferCard offer) {
        try {
            controller.sendRequest(new TotemNetworkRequest(offer.getOfferCardId()));
        } catch (Exception e) {
            System.err.println("Totem request failed: " + e.getMessage());
        }
    }

    private StackPane buildTurnOrderTile() {
        Image img = loadImage(TRACK_PATH + "turn.png");
        ImageView tileIv = new ImageView(img);
        tileIv.setFitWidth(CARD_WIDTH);
        tileIv.setFitHeight(CARD_HEIGHT);
        tileIv.setPreserveRatio(true);

        StackPane pane = new StackPane(tileIv);

        List<LocalPlayerState> turnOrder = localGameState.getTurnorder();
        double slotHeight = CARD_HEIGHT / 5;

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
        // Totem + nickname
        HBox nameRow = new HBox(6);
        nameRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Image totemImg = loadImage(TOTEM_PATH + player.getColor().name().toLowerCase() + ".png");
        if (totemImg != null) {
            ImageView totemIv = new ImageView(totemImg);
            totemIv.setFitWidth(24);
            totemIv.setFitHeight(24);
            totemIv.setPreserveRatio(true);
            nameRow.getChildren().add(totemIv);
        }
        Label nameLabel = new Label(player.getNickname());
        nameRow.getChildren().add(nameLabel);

        // Cibo + PP
        HBox statsRow = new HBox(10);
        statsRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Image foodImg = loadImage(ASSETS_PATH + "food.png");
        if (foodImg != null) {
            ImageView foodIv = new ImageView(foodImg);
            foodIv.setFitWidth(20);
            foodIv.setFitHeight(20);
            statsRow.getChildren().add(foodIv);
        }
        statsRow.getChildren().add(new Label(String.valueOf(player.getFood())));

        Image ppImg = loadImage(ASSETS_PATH + "pp.png");
        if (ppImg != null) {
            ImageView ppIv = new ImageView(ppImg);
            ppIv.setFitWidth(20);
            ppIv.setFitHeight(20);
            statsRow.getChildren().add(ppIv);
        }
        statsRow.getChildren().add(new Label(String.valueOf(player.getPrestigePoints())));

        VBox card = new VBox(6);
        card.getChildren().addAll(nameRow, statsRow);
        card.setStyle("-fx-cursor: hand;");
        card.setOnMouseClicked(e -> showTribeViewer(player));

        return card;
    }

    private void showTribeViewer(LocalPlayerState player) {
        ownerName.setText(player.getNickname());

        tribePlayerContainer.getChildren().clear();
        for (Card card : player.getTribe()) {
            tribePlayerContainer.getChildren().add(buildCardNode(card, null));
        }

        buildingsPlayerContainer.getChildren().clear();
        for (Card card : player.getBuildings()) {
            buildingsPlayerContainer.getChildren().add(buildCardNode(card, null));
        }

        deckOverlay.setVisible(true);
    }

    @FXML
    private void closeTribeViewer() {
        deckOverlay.setVisible(false);
    }

    @Override
    public void setLocalGameState(LocalGameState localGameState) {
        super.setLocalGameState(localGameState);
        Platform.runLater(this::refreshUI);
    }
}
