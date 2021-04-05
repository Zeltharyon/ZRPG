package controllers.menus;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.functions.WinModif.WinChange;


public class Start_Window_Controller implements Initializable {

    public ImageView logo;
    public Button run;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logo.setImage(new Image(new File("src/views/resources/pictures/logoRPG.png").toURI().toString()));
        run.setOnMouseClicked(Event -> {
            try {
                WinChange("menus/main_menu");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
