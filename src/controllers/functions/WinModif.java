package controllers.functions;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Run;
import models.classes.playerdata.Player_Profile;

import java.io.File;
import java.io.IOException;

import static controllers.menus.Profile_Controller.inventoryStage;


public class WinModif {

    public static Stage currentStage = Run.getPrimaryStage();
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void WinChange(String fxml) throws IOException {
        Parent root = FXMLLoader.load(WinModif.class.getResource("../../views/windows/" + fxml + ".fxml"));
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            currentStage.setX(event.getScreenX() - xOffset);
            currentStage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        currentStage.setScene(scene);
        currentStage.show();
    }

    public static void NewWin(Stage otherStage, String fxml) throws IOException {
        Parent root = FXMLLoader.load(WinModif.class.getResource("../../views/windows/" + fxml + ".fxml"));
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            otherStage.setX(event.getScreenX() - xOffset);
            otherStage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        otherStage.setScene(scene);
        otherStage.show();
    }

    public static void WinGameSetup(ImageView Picture, String ImgName, Label PlayerLbl, Label PlayerPv, ProgressBar healthBar, ImageView CoinImg, Label goldAmount, Button to_profile){
        ProfileUpdate(PlayerPv, healthBar, goldAmount);
        Picture.setImage(new Image(new File("src/views/resources/pictures/locations/" + ImgName).toURI().toString()));
        PlayerLbl.setText(Player_Profile.getPlayer().getName());
        CoinImg.setImage(new Image(new File("src/views/resources/pictures/icons/coin.png").toURI().toString()));
        healthBar.setStyle("-fx-accent: darkred;");
        Image img = new Image(new File("src/views/resources/pictures/actions/bag.png").toURI().toString());
        ImageView view = new ImageView(img);
        to_profile.setGraphic(view);

        to_profile.setOnMouseEntered(Event -> {
            Image bagpic = new Image(new File("src/views/resources/pictures/actions/bag_opened.gif").toURI().toString());
            ImageView bgpcview = new ImageView(bagpic);
            to_profile.setGraphic(bgpcview);
        });
        to_profile.setOnMouseExited(Event -> {
            Image bagpic = new Image(new File("src/views/resources/pictures/actions/bag.png").toURI().toString());
            ImageView bgpcview = new ImageView(bagpic);
            to_profile.setGraphic(bgpcview);
        });
        to_profile.setOnMouseClicked(Event -> {
            try {
                NewWin(inventoryStage, "menus/inventory");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void ProfileUpdate(Label PlayerPv, ProgressBar healthBar, Label goldAmount){
        float pv = Player_Profile.getPlayer().getPv();
        float maxpv = Player_Profile.getPlayer().getMaxPv();
        PlayerPv.setText(Player_Profile.getPlayer().getPv() + "/" + Player_Profile.getPlayer().getMaxPv());
        goldAmount.setText(""+Player_Profile.getPlayer().getGold());
        healthBar.setProgress(pv/maxpv);
    }

    public static void WinClose() throws IOException {
        currentStage.close();
    }

}

