package controllers.menus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.classes.entities.Entity;
import models.classes.playerdata.Player_Profile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controllers.functions.WinModif.WinChange;
import static controllers.functions.WinModif.WinClose;

public class Char_Creation_Controller extends Player_Profile implements Initializable {

    //Attributs fx:id
    @FXML
    public ComboBox<Classes_Menu> choixClasse;
    public TextField username;
    public Label lab_class;
    public ImageView logoClasse;
    public Text descripClasse;
    public Button confirm;
    public Button close;


    //Attributs
    ObservableList<Classes_Menu> classesMenuListe = FXCollections.observableArrayList(
            new Classes_Menu(1), //Guerrier
            new Classes_Menu(2), //Mage
            new Classes_Menu(3) //Voleur
    );

    //Méthodes
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choixClasse.setItems(classesMenuListe);
    }

    public void changeLab() {
            lab_class.setText(choixClasse.getValue().getName());
            logoClasse.setImage(choixClasse.getValue().getLogoClasse());
            descripClasse.setText(choixClasse.getValue().getDescription());
    }

    public void creation() throws IOException {
        Entity player = new Entity(username.getText(), true, choixClasse.getValue().getId(), true);
        setPlayer(player);
        WinChange("game/city");
    }

    public void close() throws IOException {
        WinClose();
    }

    public void back() throws IOException {
        WinChange("menus/main_menu");
    }

    public static class Classes_Menu {

        private int id;
        private String name;
        private Image logoClasse;
        private String description;

        public Classes_Menu(int id_choice){

            if (id_choice == 1) {
                this.name = "Guerrier";
                this.id = id_choice;
                this.logoClasse = new Image(new File("src/views/resources/pictures/classes/warrior.png").toURI().toString());
                this.description = "Fier et fort personnage qui possède des bonus en défense.";

            } else if (id_choice == 2) {
                this.name = "Mage";
                this.id = id_choice;
                this.logoClasse = new Image(new File("src/views/resources/pictures/classes/wizard.png").toURI().toString());
                this.description = "Personnage frêle, mais possédant des bonus en attaque.";

            } else if (id_choice == 3) {
                this.name = "Voleur";
                this.id = id_choice;
                this.logoClasse = new Image(new File("src/views/resources/pictures/classes/thief.png").toURI().toString());
                this.description = "Personnage roublard, ayant moins d'attaque que le mage mais débutant avec quelques pièces d'or.";
            }

        }

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public Image getLogoClasse() {
            return logoClasse;
        }
        public String getDescription() {
            return description;
        }
        @Override
        public String toString() {
            return this.getName();
        }
    }
}
