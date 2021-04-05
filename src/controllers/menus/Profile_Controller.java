package controllers.menus;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.classes.playerdata.Player_Profile;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Profile_Controller implements Initializable {


    public Label playerName;
    public ImageView logoClasse;
    public Label class_lab;
    public ImageView pv;
    public ImageView atk;
    public ImageView def;
    public Label pvAmount;
    public Label atkAmount;
    public Label defAmount;
    public static Stage inventoryStage = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerName.setText(Player_Profile.getPlayer().getName());
        logoClasse.setImage(new Image(new File("src/views/resources/pictures/classes/" + Player_Profile.getPlayer().getFilepicName() + ".png").toURI().toString()));
        class_lab.setText(Player_Profile.getPlayer().getClasse());
        pv.setImage(new Image(new File("src/views/resources/pictures/icons/hel.png").toURI().toString()));
        pvAmount.setText(Player_Profile.getPlayer().getPv() + "/" + Player_Profile.getPlayer().getMaxPv());
        atk.setImage(new Image(new File("src/views/resources/pictures/icons/atk.png").toURI().toString()));
        atkAmount.setText(Player_Profile.getPlayer().getDmg() + "");
        def.setImage(new Image(new File("src/views/resources/pictures/icons/def.png").toURI().toString()));
        defAmount.setText(Player_Profile.getPlayer().getDef() + "");
    }
}
