package controllers.game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.classes.entities.Entity;
import models.classes.playerdata.Player_Profile;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static controllers.functions.WinModif.*;
import static models.classes.playerdata.Player_Profile.Save;

public class Shop_Controller implements Initializable {

    @FXML
    public Label playerName;
    public Pane actionBar;
    public ProgressBar healthBar;
    public Button close;
    public ImageView shopImage;
    public Label pvAmount;
    public ImageView coin;
    public Label goldAmount;
    public ListView<Item> listItems;
    public Button buy;
    public Label tooPoor;
    public Button to_profile;

    public ObservableList<Item> list_of_items = FXCollections.observableArrayList(
            new Item("Saucisse", 5, 1),
            new Item("Potion de vie", 15, 2),
            new Item("Potion de force", 30, 3),
            new Item("Cristal de vie", 70, 4)
    );

    public void initialize(URL url, ResourceBundle resourceBundle) {
        WinGameSetup(shopImage, "shop.gif", playerName, pvAmount, healthBar, coin, goldAmount, to_profile);
        listItems.setItems(list_of_items);
        tooPoor.setText("Trop pauvre, le clodo !");
        tooPoor.setVisible(false);
    }
    public void close() throws IOException {
        Save();
        WinClose();
    }
    public void to_city() throws IOException {
        WinChange("game/city");
    }
    public void buy() throws InterruptedException {
        Item choice = listItems.getSelectionModel().getSelectedItem();
        Entity player = Player_Profile.getPlayer();

        if (choice.getId_item() == 1 && player.getGold() >= choice.price) {
            if(player.getPv() < player.getMaxPv()) {
                player.setGold(player.getGold() - choice.price);
                player.heal(player, 5);
                ProfileUpdate(pvAmount, healthBar, goldAmount);
                tooPoor.setVisible(false);
            }
        }
        else if (choice.getId_item() == 2 && player.getGold() >= choice.price) {
            if(player.getPv() < player.getMaxPv()) {
                player.setGold(player.getGold() - choice.price);
                player.heal(player, 20);
                ProfileUpdate(pvAmount, healthBar, goldAmount);
                tooPoor.setVisible(false);
            }
        }
        else if (choice.getId_item() == 3 && player.getGold() >= choice.price) {
            player.setGold(player.getGold() - choice.price);
            player.setDmg(player.getDmg()+2);
            ProfileUpdate(pvAmount, healthBar, goldAmount);
            tooPoor.setVisible(false);
        }
        else if (choice.getId_item() == 4 && player.getGold() >= choice.price) {
            player.setGold(player.getGold() - choice.price);
            player.setMaxPv(player.getMaxPv()+10);
            player.setPv(player.getPv()+10);
            ProfileUpdate(pvAmount, healthBar, goldAmount);
            tooPoor.setVisible(false);
        }
        else{
            tooPoor.setVisible(true);
        }
    }

    public static class Item {

        private String name;
        private int price;
        private int id_item;

        public Item(String name, int price, int id_item){
            this.name = name;
            this.price = price;
            this.id_item = id_item;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getId_item() {
            return id_item;
        }

        @Override
        public String toString() {
            return this.getName() + " - " + this.getPrice() + " Po";
        }
    }
}
