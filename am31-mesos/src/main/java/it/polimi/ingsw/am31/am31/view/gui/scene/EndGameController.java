package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalLeaderBoard;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.gui.PathConstants;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.List;

public class EndGameController extends BaseController {
    @FXML VBox rankingBox;



    private void populateRanking (List<LocalLeaderBoard> ranking) {
        Font.loadFont(getClass().getResourceAsStream(
                PathConstants.ASSETS_PATH + "InknutAntiqua-Regular.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream(
                PathConstants.ASSETS_PATH + "InknutAntiqua-Bold.ttf"), 16);
        int i = 1;
        for (LocalLeaderBoard player : ranking) {

            rankingBox.getChildren().add(new Separator());
            HBox row = new HBox();

            Label position = new Label("#" + i);
            position.setFont(Font.font("Inknut Antiqua Regular", 16));
            position.setPrefWidth(150);
            if (player.isWinner()) position.setStyle("-fx-font-weight: bold;");

            HBox name = new HBox(10);
            name.setPrefWidth(150);
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
            if (player.isWinner()) nickname.setStyle("-fx-font-weight: bold;");
            name.getChildren().add(nickname);

            HBox points = new HBox(10);
            HBox pp = new HBox(10);
            HBox food = new HBox(10);
            pp.setAlignment(Pos.CENTER_LEFT);
            food.setAlignment(Pos.CENTER_LEFT);
            points.setPrefWidth(150);
            Image ppImg = loadImage(PathConstants.ASSETS_PATH + "pp.png");
            if (ppImg != null) {
                ImageView ppIv = new ImageView(ppImg);
                ppIv.setFitWidth(28);
                ppIv.setFitHeight(25);
                pp.getChildren().add(ppIv);
            }
            Label ppPoints = new Label();
            ppPoints.setText(String.valueOf(player.playerState().getPrestigePoints()));
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
            Label foodPoints = new Label();
            foodPoints.setText(String.valueOf(player.playerState().getFood()));
            foodPoints.setFont(Font.font("Inknut Antiqua Regular", 16));
            if (player.isWinner()) foodPoints.setStyle("-fx-font-weight: bold;");
            food.getChildren().add(foodPoints);
            Region space3 = new Region();
            HBox.setHgrow(space3, javafx.scene.layout.Priority.ALWAYS);
            points.getChildren().addAll(pp, space3, food);

            Region space1 = new Region();
            Region space2 = new Region();
            HBox.setHgrow(space1, javafx.scene.layout.Priority.ALWAYS);
            HBox.setHgrow(space2, javafx.scene.layout.Priority.ALWAYS);

            row.getChildren().addAll(position,space1, name, space2, points);
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
            i++;
        }
    }

    //method to load an image from a path
    private Image loadImage(String path) {
        InputStream stream = getClass().getResourceAsStream(path);
        return stream != null ? new Image(stream) : null;
    }

    @Override
    public void setLocalGameState(LocalGameState localGameState) {
        super.setLocalGameState(localGameState);

        populateRanking(localGameState.getLeaderboard());
    }

    //for test
    @FXML
    public void initialize() {
        LocalPlayerState simone = new LocalPlayerState("Simone", Color.RED);
        simone.setPrestigePoints(120);
        simone.setFood(3);

        LocalPlayerState fabio = new LocalPlayerState("Fabio", Color.BLUE);
        fabio.setPrestigePoints(95);
        fabio.setFood(5);

        LocalPlayerState mario = new LocalPlayerState("Mario", Color.YELLOW);
        mario.setPrestigePoints(80);
        mario.setFood(2);

        populateRanking(List.of(
                new LocalLeaderBoard(simone, true),
                new LocalLeaderBoard(fabio,  false),
                new LocalLeaderBoard(mario,  false)
        ));
    }
}
