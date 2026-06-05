package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.GlobalRankingEntry;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.localState.LocalLeaderBoard;
import it.polimi.ingsw.am31.am31.view.gui.PathConstants;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.List;

public class EndGameController extends BaseController {
    @FXML VBox rankingBox;
    @FXML ImageView backgroundImage;
    @FXML StackPane  stackPane;
    @FXML Button backButton;
    @FXML Label globalPosition;
    @FXML Button globalRanking;
    @FXML StackPane globalRankingOverlay;
    @FXML TableView <GlobalRankingEntry> rankingTable;
    @FXML TableColumn <GlobalRankingEntry, Integer> rankColumn;
    @FXML TableColumn <GlobalRankingEntry, String> nicknameColumn;
    @FXML TableColumn <GlobalRankingEntry, Integer> gamesColumn;
    @FXML TableColumn <GlobalRankingEntry, Integer> foodColumn;
    @FXML TableColumn <GlobalRankingEntry, Integer> PPColumn;
    @FXML Label tableTitle;

    double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
    double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();

    @FXML public void initialize() {
        Font.loadFont(getClass().getResourceAsStream(PathConstants.ASSETS_PATH + "InknutAntiqua-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream(PathConstants.ASSETS_PATH + "InknutAntiqua-Bold.ttf"), 16);
        backgroundImage.fitWidthProperty().bind(stackPane.widthProperty());
        backgroundImage.fitHeightProperty().bind(stackPane.heightProperty());
        rankingBox.setPrefWidth(screenWidth * (double) 600 /1920);
        backButton.setPrefWidth(screenWidth * (double) 300 /1920);
        backButton.setStyle("-fx-border-color: black; -fx-border-width: 0 1 0 0");
        globalPosition.setPrefWidth(screenWidth * (double) 600 /1920);
        globalRanking.setPrefWidth(screenWidth * (double) 300 /1920);
        tableTitle.setPrefWidth(screenWidth * (double) 1000 / 1920);
        tableTitle.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0; -fx-background-color: rgba(255,243,211,1);");
        rankingTable.setPrefHeight(screenHeight* (double) 600 / 1080);
        rankingTable.setPrefWidth(screenWidth * (double) 1000 / 1920);

        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("playerNickname"));
        gamesColumn.setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));
        foodColumn.setCellValueFactory(new PropertyValueFactory<>("totalFood"));
        PPColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrestigePoints"));


        PseudoClass currentPlayer = PseudoClass.getPseudoClass("current-player");

        rankingTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(GlobalRankingEntry item, boolean empty) {
                super.updateItem(item, empty);
                boolean isCurrentPlayer = item != null && item.getPlayerNickname().equals(controller.getLocalPlayerUsername());
                pseudoClassStateChanged(currentPlayer, isCurrentPlayer);
            }
        });
    }

    private void populateRanking (List<LocalLeaderBoard> ranking) {
        int i = 1;
        for (LocalLeaderBoard player : ranking) {

            rankingBox.getChildren().add(new Separator());
            HBox row = new HBox();

            Label position = new Label("#" + i);
            position.setFont(Font.font("Inknut Antiqua Regular", 16));

            position.setPadding(new Insets(0,10,0,0));
            //position.setPrefWidth(150);
            if (player.isWinner()) position.setStyle("-fx-font-weight: bold;");

            HBox name = new HBox(10);
            //name.setPrefWidth(150);
            name.setAlignment(Pos.CENTER_LEFT);
            Image totemImg = loadImage(PathConstants.TOTEM_PATH + player.playerState().getColor().name().toLowerCase() + ".png");
            if (totemImg != null) {
                ImageView totemIv = new ImageView(totemImg);
                totemIv.setFitHeight(32);
                totemIv.setPreserveRatio(true);
                name.getChildren().add(totemIv);
            }
            Label nickname = new Label();
            nickname.setText(player.playerState().getNickname());
            nickname.setFont(Font.font("Inknut Antiqua Regular", 16));
            nickname.setMaxWidth(screenWidth * (double) 150 / 1920);
            nickname.setTextOverrun(OverrunStyle.ELLIPSIS);
            if (player.isWinner()) nickname.setStyle("-fx-font-weight: bold;");
            name.getChildren().add(nickname);

            HBox points = new HBox(10);
            HBox pp = new HBox(10);
            HBox food = new HBox(10);
            pp.setAlignment(Pos.CENTER_LEFT);
            food.setAlignment(Pos.CENTER_LEFT);
            pp.setPrefWidth(100);
            food.setPrefWidth(100);
            Image ppImg = loadImage(PathConstants.ASSETS_PATH + "pp.png");
            if (ppImg != null) {
                ImageView ppIv = new ImageView(ppImg);
                ppIv.setFitWidth(28);
                ppIv.setFitHeight(25);
                pp.getChildren().add(ppIv);
            }
            Label ppPoints = new Label("0");
            ppPoints.setFont(Font.font("Inknut Antiqua Regular", 16));
            if (player.isWinner()) ppPoints.setStyle("-fx-font-weight: bold;");
            pp.getChildren().add(ppPoints);
            Image foodImg = loadImage(PathConstants.ASSETS_PATH + "food.png");
            if (foodImg != null) {
                ImageView foodIv = new ImageView(foodImg);
                foodIv.setFitWidth(28);
                foodIv.setFitHeight(25);
                food.getChildren().add(foodIv);
            }
            Label foodPoints = new Label("0");
            foodPoints.setFont(Font.font("Inknut Antiqua Regular", 16));
            if (player.isWinner()) foodPoints.setStyle("-fx-font-weight: bold;");
            food.getChildren().add(foodPoints);
            Region space3 = new Region();
            HBox.setHgrow(space3, Priority.ALWAYS);
            points.getChildren().addAll(pp, space3, food);

            Region space1 = new Region();
            Region space2 = new Region();
            HBox.setHgrow(space1, Priority.ALWAYS);
            HBox.setHgrow(space2, Priority.ALWAYS);

            row.getChildren().addAll(position, name, space2, points);
            row.setStyle("-fx-padding: 20;");
            if (player.isWinner()) {
                row.setStyle("-fx-padding: 20; -fx-background-color: rgba(235,220,185,1);");
                DropShadow shadow = new DropShadow();
                shadow.setRadius(10);
                shadow.setOffsetX(3);
                shadow.setOffsetY(3);
                shadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.5));
                row.setEffect(shadow);
            }
            rankingBox.getChildren().add(row);
            animateRowEntry(row, i, ppPoints, player.playerState().getPrestigePoints(), foodPoints, player.playerState().getFood());
            i++;
        }
    }

    //method to load an image from a path
    private Image loadImage(String path) {
        InputStream stream = getClass().getResourceAsStream(path);
        return stream != null ? new Image(stream) : null;
    }

    private void animateRowEntry(HBox row, int index, Label ppLabel, int targetPP, Label foodLabel, int targetFood) {
        //parte invisibile e sotto di 30px
        row.setOpacity(0);
        row.setTranslateY(30);

        Duration delay = Duration.millis(index * 1000);

        //gestisce la trasparenza: parte invisibile e diventa completamente visibile
        FadeTransition fade = new FadeTransition(Duration.millis(500), row);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(Duration.millis(700), row);
        slide.setFromY(30);
        slide.setToY(0);

        ParallelTransition anim = new ParallelTransition(fade, slide);
        anim.setDelay(delay);
        anim.setOnFinished(e -> {
            animateCounter(ppLabel, targetPP);
            animateCounter(foodLabel, targetFood);
        });
        anim.play();
    }

    private void animateCounter(Label ppLabel, int targetValue) {
        ppLabel.setText("0");

        Timeline timeline = new Timeline();
        int steps = 20; //number of photograms
        Duration totalDuration = Duration.millis(3000); //total duration of the animation

        for (int s = 1; s <= steps; s++) {
            int value = (int) (targetValue * ((double) s / steps)); //value = target * s/steps = 120 * 1/20 = 6
            Duration time = totalDuration.multiply((double) s / steps);
            timeline.getKeyFrames().add(
                    new KeyFrame(time, ev -> ppLabel.setText(String.valueOf(value)))
            ); //at the time "time" set the text to value
        }

        timeline.play();
    }

    @Override
    public void setLocalGameState(LocalGameState localGameState) {
        super.setLocalGameState(localGameState);

        populateRanking(localGameState.getLeaderboard());
        String myNick = controller.getLocalPlayerUsername();
        int rank = localGameState.getGlobalRanking().stream()
                .filter(g -> g.getPlayerNickname().equals(myNick))
                .findFirst()
                .map(GlobalRankingEntry::getRank)
                .orElse(-1);
        if (rank != -1)
        {
            globalPosition.setText("Global position: #" + rank + "/" + localGameState.getGlobalRanking().size());
        }
        else
        {
            globalPosition.setText("Player not found in global ranking");
        }
    }

    @FXML
    private void returnToLobby() {
        localGameState.reset();
        sceneManager.showWaitingRoom();
    }

    @FXML
    private void showGlobalRanking() {
//        globalRankingOverlay.getChildren().clear();
//        Region darkBg = new Region();
//        darkBg.setStyle("-fx-background-color: rgba(0,0,0,0.6);");
//        darkBg.setMaxWidth(Double.MAX_VALUE);
//        darkBg.setMaxHeight(Double.MAX_VALUE);
//        darkBg.setOnMouseClicked(e -> closeGlobalRanking());

        ObservableList<GlobalRankingEntry> rankingData = FXCollections.observableArrayList(localGameState.getGlobalRanking());
        rankingTable.setItems(rankingData);
//        globalRankingOverlay.getChildren().addAll(darkBg);

        globalRankingOverlay.setVisible(true);
    }

    @FXML
    private void closeGlobalRanking() {
        globalRankingOverlay.setVisible(false);
    }
}
