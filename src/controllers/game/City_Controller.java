package controllers.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import models.classes.playerdata.Player_Profile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.functions.WinModif.*;
import static controllers.menus.Profile_Controller.inventoryStage;
import static models.classes.playerdata.Player_Profile.Save;

public class City_Controller implements Initializable  {
    @FXML
    public HBox profileBar;
    public Label playerName;
    public ProgressBar healthBar;
    public ImageView cityImage;
    public Pane actionBar;
    public Button close;
    public Label pvAmount;
    public ImageView coin;
    public Label goldAmount;
    public Button to_profile;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        WinGameSetup(cityImage, "city.gif", playerName, pvAmount, healthBar, coin, goldAmount, to_profile);
    }

    public void close() throws IOException {
        Save();
        WinClose();
    }

    public void to_dungeon() throws IOException {
        WinChange("game/dungeon");
    }
    public void to_shop() throws IOException {
        WinChange("game/shop");
    }
}
